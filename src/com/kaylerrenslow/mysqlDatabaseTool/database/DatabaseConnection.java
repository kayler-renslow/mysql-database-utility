package com.kaylerrenslow.mysqlDatabaseTool.database;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.ConnectionException;
import com.kaylerrenslow.mysqlDatabaseTool.database.lib.MysqlConnection;
import com.kaylerrenslow.mysqlDatabaseTool.database.lib.QueryFailedException;
import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.IConnectionUpdate;
import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.IQueryExecuteEvent;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @author Kayler
 *         Runs a database connection and query utility synchronously.
 *         Created on 11/6/15.
 */
public class DatabaseConnection{
	private File propFile;
	private MysqlConnection conn = new MysqlConnection(System.out);
	private IConnectionUpdate conUpdate;
	private IQueryExecuteEvent qee;
	private String sql;

	private ConnectionStatus status = ConnectionStatus.DISCONNECTED;

	/**
	 * The conUpdate is what is used to describe the state of the connection through each step of the connection (disconnect, connect, query, etc).
	 */
	public void setConnectionUpdate(IConnectionUpdate conEvent) {
		this.conUpdate = conEvent;
	}

	public void setQueryExecutedEvent(IQueryExecuteEvent qee) {
		this.qee = qee;
	}

	public IQueryExecuteEvent getQueryExecuteEvent() {
		return this.qee;
	}

	/**
	 * Sets the database connection properties file
	 */
	public void setConnectionPropertiesFile(File propCon) {
		this.propFile = propCon;
	}

	/**
	 * Sets the current SQL query to parameter sql
	 */
	public void prepareQuery(String sql) {
		this.sql = sql;
	}

	/**
	 * Attempts a connection to the database synchronously.
	 */
	public void connect() {
		if (propFile == null){
			connectionUpdate(Lang.CONN_STATUS_NO_PROPERTIES_LONG, null, ConnectionStatus.NO_PROPERTIES);
			return;
		}
		if (!conn.connectionPropertiesSet()){
			try{
				conn.loadConnectionProperties(this.propFile);
			}catch (IllegalArgumentException e){
				//e.printStackTrace();
				connectionUpdate(e.getMessage(), null, ConnectionStatus.BAD_PROPERTIES);
				return;
			}catch (IOException ioe){
				ioe.printStackTrace();
				connectionUpdate("Error occurred with the properties file.\n" + ioe.getMessage(), null, ConnectionStatus.BAD_PROPERTIES);
				return;
			}
		}
		connectionUpdate(null, null, ConnectionStatus.CONNECTING);
		try{
			conn.connect();
		}catch (ConnectionException e){
			connectionUpdate("An error occurred while trying to connect to the database.\n" + e.getMessage(), null, ConnectionStatus.CONNECTION_ERROR);
			return;
		}

		connectionUpdate(null, null, ConnectionStatus.CONNECTED);
	}

	/**
	 * Disconnects from the database synchronously. This has no effect if the connection was already disconnected.
	 */
	public void disconnect() {
		conn.disconnect();
		connectionUpdate(null, null, ConnectionStatus.DISCONNECTED);
	}

	/**
	 * Get a String of the current status' message.
	 */
	public String getConnectionStatusMessage() {
		return Lang.DB_STATUS_PREFIX + status.msg;
	}

	public ConnectionStatus status() {
		return this.status;
	}

	/**
	 * Runs the prepared query synchronously
	 */
	public void runQuery() {
		if (!this.isConnected()){
			connectionUpdate(Lang.CONN_STATUS_NOT_CONNECTED_LONG, Lang.NOTIF_BODY_NOT_CONNECTED, ConnectionStatus.NOT_CONNECTED);
			return;
		}
		try{
			connectionUpdate(Lang.CONN_STATUS_BEGIN_QUERY_LONG + this.sql, null, ConnectionStatus.QUERY_BEGIN);
			ResultSet rs = conn.query(this.sql);
			connectionUpdate(null, rs, ConnectionStatus.QUERY_END);
		}catch (QueryFailedException e){
			connectionUpdate(Lang.CONN_STATUS_QUERY_ERROR_LONG, e.getMessage(), ConnectionStatus.QUERY_FAIL);
		}
	}

	private void connectionUpdate(String msg, Object data, ConnectionStatus newStatus) {
		this.status = newStatus;
		if (this.conUpdate == null){
			System.err.println("WARNING: connectionUpdate is not set for the DatabaseConnection");
			return;
		}
		this.conUpdate.connectionUpdate(msg, data);
	}

	public boolean isConnected() {
		return this.conn.isConnected();
	}

}
