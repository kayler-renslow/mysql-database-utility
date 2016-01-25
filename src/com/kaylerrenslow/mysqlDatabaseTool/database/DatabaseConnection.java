package com.kaylerrenslow.mysqlDatabaseTool.database;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.ConnectionException;
import com.kaylerrenslow.mysqlDatabaseTool.database.lib.MysqlConnection;
import com.kaylerrenslow.mysqlDatabaseTool.database.lib.QueryFailedException;
import com.kaylerrenslow.mysqlDatabaseTool.dbGui.IConnectionUpdate;
import com.kaylerrenslow.mysqlDatabaseTool.dbGui.IQueryExecuteEvent;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTableEdit;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.IDBTableData;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Kayler
 *         Runs a database connection and query utility synchronously.
 *         Created on 11/6/15.
 */
public class DatabaseConnection{
	private File propFile;
	private MysqlConnection mysqlConn = new MysqlConnection(System.out);
	private IConnectionUpdate conUpdate;
	private IQueryExecuteEvent qee;
	private String sql;

	private ConnectionStatus status = ConnectionStatus.DISCONNECTED;
	private IDBTableData dBTable;
	private String synchronizeTableName;

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
		if (!mysqlConn.connectionPropertiesSet()){
			try{
				mysqlConn.loadConnectionProperties(this.propFile);
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
			mysqlConn.connect();
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
		mysqlConn.disconnect();
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
	 * Runs the prepared query synchronously. This method should be used only if the prepared query is just a selection query
	 */
	public void runQuery() {
		if (!this.isConnected()){
			connectionUpdate(Lang.CONN_STATUS_NOT_CONNECTED_LONG, Lang.NOTIF_BODY_NOT_CONNECTED, ConnectionStatus.NOT_CONNECTED);
			return;
		}
		try{
			connectionUpdate(Lang.CONN_STATUS_BEGIN_QUERY_LONG + this.sql, null, ConnectionStatus.QUERY_BEGIN);
			ResultSet rs = mysqlConn.query(this.sql);
			connectionUpdate(null, rs, ConnectionStatus.QUERY_END);
		}catch (QueryFailedException e){
			connectionUpdate(Lang.CONN_STATUS_QUERY_ERROR_LONG, e.getMessage(), ConnectionStatus.QUERY_FAIL);
		}
	}

	private void connectionUpdate(String msg, Object data, ConnectionStatus newStatus) {
		this.status = newStatus;
		if (this.conUpdate == null){
			System.out.println("DatabaseConnection.connectionUpdate was triggered, but no connection update was set.");
			return;
		}
		this.conUpdate.connectionUpdate(msg, data);
	}

	public ArrayList<String> getAllTableNames() {
		ArrayList<String> s = new ArrayList<>();
		try{
			DatabaseMetaData dmd = this.mysqlConn.getDBMetadata();
			ResultSet rs = dmd.getTables(null, null, "%", null);
			while (rs.next()){
				s.add(rs.getString(3));
			}
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return s;
	}

	public boolean isConnected() {
		return this.mysqlConn.isConnected();
	}

	public void prepareSynchronize(String tableName) {
		this.synchronizeTableName = tableName;
	}

	public void synchronize() {
		if (this.dBTable == null){
			throw new IllegalStateException("In order to synchronize the data, setDBTable(), must be called");
		}
		if (!this.dBTable.hasColumns()){
			throw new IllegalStateException("Can't synchronize a table when there isn't any columns");
		}
		String tableName = this.synchronizeTableName;

		String primaryKey = null;
		try{
			DatabaseMetaData md = this.mysqlConn.getDBMetadata();
			ResultSet rs = md.getPrimaryKeys(null, null, tableName);
			rs.next();
			primaryKey = rs.getString(4);
		}catch (Exception e){
			System.err.println("Synchronize failed. " + e.getMessage());
			return;
		}


		DBTableEdit edit;
		Iterator<DBTableEdit> iter = this.dBTable.getEditedData().iterator();

		while (iter.hasNext()){
			edit = iter.next();
			String primaryKeyValue = "";
			for (int i = 0; i < this.dBTable.getColumnNames().length; i++){
				if (this.dBTable.getColumnNames()[i].equals(primaryKey)){
					primaryKeyValue = (String) edit.oldRowData().get(i);
					break;
				}
			}
			try{
				String query;
				if (edit.type() == DBTableEdit.EditType.DELETION){

					query = "DELETE FROM " + tableName + " WHERE " + primaryKey + "='" + primaryKeyValue + "'";

				}else if (edit.type() == DBTableEdit.EditType.ADDITION){

					query = "INSERT INTO " + tableName + " ( " + getInsertString(this.dBTable.getColumnNames(), edit.newRowData()) + ")";

				}else if (edit.type() == DBTableEdit.EditType.UPDATE){

					query = "UPDATE " + tableName + " SET ";
					query += getUpdateString(this.dBTable.getColumnNames(), edit.newRowData());
					query += " WHERE " + primaryKey + "='" + primaryKeyValue + "'";

				}else {
					throw new IllegalStateException("Edit type '" + edit.type() + "' not supported");
				}
				System.out.println(query);

				this.mysqlConn.query(query, true);

			}catch (Exception e){
				connectionUpdate(Lang.CONN_STATUS_QUERY_ERROR_LONG, e.getMessage(), ConnectionStatus.QUERY_FAIL);
				return;
			}
		}
		System.out.println("----Queries complete-----");
		this.dBTable.clearEdited();
	}

	/**
	 * Set the table that will be used for synchronizing data. Can't be null.
	 */
	public void setDBTable(IDBTableData dBTable) {
		this.dBTable = dBTable;
	}


	private String getUpdateString(String[] fields, ObservableList<?> values) {
		String s = "";
		Iterator iter = values.iterator();
		int i = 0;
		while (iter.hasNext()){
			s += fields[i] + "='" + iter.next() + "'";
			if (iter.hasNext()){
				s += ", ";
			}
			i++;
		}
		return s;
	}


	private String getInsertString(String[] fields, ObservableList<?> values) {
		String s = "";
		Iterator iter = values.iterator();
		int i = 0;
		for(i = 0; i < fields.length; i++){
			s+= fields[i];
			if(i != fields.length - 1){
				s += ", ";
			}
		}
		s += ") VALUES (";
		while (iter.hasNext()){
			s += "'"+iter.next()+"'";
			if (iter.hasNext()){
				s += ", ";
			}
			i++;
		}
		return s;
	}
}
