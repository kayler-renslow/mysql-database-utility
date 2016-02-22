package com.kaylerrenslow.mysqlDatabaseTool.database;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.*;
import com.kaylerrenslow.mysqlDatabaseTool.dbGui.IConnectionUpdate;
import com.kaylerrenslow.mysqlDatabaseTool.dbGui.IQueryExecuteEvent;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTableEdit;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.IDBTableData;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.MySQLDatabaseUtility;
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
	private boolean propFileUpdated = false;
	private MysqlConnection mysqlConn = new MysqlConnection(System.out);
	private IConnectionUpdate conUpdate;
	private IQueryExecuteEvent qee;
	private String sql;
	private QueryType queryType;

	private ConnectionStatus status = ConnectionStatus.DISCONNECTED;
	private IDBTableData dBTable;

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
		propFileUpdated = true;
	}

	/**
	 * Sets the current SQL query to parameter sql
	 */
	public void prepareQuery(String sql, QueryType qt) {
		this.sql = sql;
		this.queryType = qt;
	}

	public void prepareQueryToLoadTable(String tablename){
		prepareQuery("SELECT * FROM "+ tablename, QueryType.SELECTION);
	}

	private String getCommentSQL(String table, String columnName){
		return "SELECT COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = '"+this.mysqlConn.getDatabaseName()+"' AND TABLE_NAME = '"+table+"' AND COLUMN_NAME = '"+columnName+"';";
	}

	/**
	 * Attempts a connection to the database synchronously.
	 */
	public void connect() {
		if (propFile == null){
			connectionUpdate(Lang.CONN_STATUS_NO_PROPERTIES_LONG, null, ConnectionStatus.NO_PROPERTIES);
			return;
		}
		if (this.propFileUpdated){
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
		this.propFileUpdated = false;
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
			System.out.println("DatabaseConnection.runQuery");
			return;
		}
		try{
			connectionUpdate(Lang.CONN_STATUS_BEGIN_QUERY_LONG + this.sql, null, ConnectionStatus.QUERY_BEGIN);
			MysqlQueryResult rs = mysqlConn.query(this.sql, this.queryType);
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

	public void synchronize() {
		if (this.dBTable == null){
			throw new IllegalStateException("In order to synchronize the data, setDBTable(), must be called");
		}
		if (!this.dBTable.hasColumns()){
			throw new IllegalStateException("Can't synchronize a table when there isn't any columns");
		}
		String tableName = this.dBTable.getTableName();

		String primaryKey = null;
		try{
			DatabaseMetaData md = this.mysqlConn.getDBMetadata();
			ResultSet rs = md.getPrimaryKeys(null, null, tableName);
			if(rs.next()){
				primaryKey = rs.getString(4);
			}
		}catch (Exception e){
			System.err.println("Synchronize failed. " + e.getMessage());
			MySQLDatabaseUtility.showErrorWindow("Synchronization failed.", e.getMessage());
			return;
		}

		DBTableEdit edit;
		Iterator<DBTableEdit> iter = this.dBTable.iterator(false);

		System.out.println("----Sync Queries Started-----");

		while (iter.hasNext()){
			edit = iter.next();
			String whereCond;
			String primaryKeyValue = "";
			if(primaryKey != null){
				for (int i = 0; i < this.dBTable.getColumnNames().length; i++){
					if (this.dBTable.getColumnNames()[i].equals(primaryKey)){
						primaryKeyValue = (String) edit.oldRowData().get(i);
						break;
					}
				}
				whereCond =  " WHERE " + primaryKey + "='" + primaryKeyValue + "'";
			}else{
				whereCond = getWhereCondition(this.dBTable.getColumnNames(), edit.oldRowData());
			}
			try{
				String query;
				if (edit.type() == DBTableEdit.EditType.DELETION){

					query = "DELETE FROM " + tableName + whereCond;

				}else if (edit.type() == DBTableEdit.EditType.ADDITION){

					query = "INSERT INTO " + tableName + " ( " + getInsertString(this.dBTable.getColumnNames(), edit.newRowData()) + ")";

				}else if (edit.type() == DBTableEdit.EditType.UPDATE){

					query = "UPDATE " + tableName + " SET ";
					query += getUpdateString(this.dBTable.getColumnNames(), edit.newRowData(), edit.oldRowData());
					query += whereCond;

				}else {
					throw new IllegalStateException("Edit type '" + edit.type() + "' not supported");
				}

				this.mysqlConn.query(query, true);
				System.out.println(query);

			}catch (Exception e){
				connectionUpdate(Lang.CONN_STATUS_QUERY_ERROR_LONG, e.getMessage(), ConnectionStatus.QUERY_FAIL);
				return;
			}
		}
		System.out.println("----Sync Queries Complete-----");
		this.dBTable.clearEdited();
	}

	/**
	 * Set the table that will be used for synchronizing data. Can't be null.
	 */
	public void setDBTable(IDBTableData dBTable) {
		this.dBTable = dBTable;
	}

	private String getWhereCondition(String[] fields, ObservableList<?> values){
		String s = " WHERE ";
		Iterator iter = values.iterator();
		int i = 0;
		while (iter.hasNext()){
			s += fields[i] + "='" + escapeString(iter.next().toString()) + "'";
			if (iter.hasNext()){
				s += " AND ";
			}
			i++;
		}
		return s;
	}


	private String getUpdateString(String[] fields, ObservableList<?> newData, ObservableList<?> oldData) {
		String s = "";
		Iterator iterNew = newData.iterator();
		Iterator iterOld = oldData.iterator();
		if(newData.size() != oldData.size()){
			throw new IllegalStateException("sizes aren't equal");
		}
		int i = 0;
		String iterNextValue;
		boolean placeComma = false;
		while (iterNew.hasNext()){
			iterNextValue = iterNew.next().toString();
			if(iterNextValue.equals(iterOld.next())){ //no need to put this data in the update since it's the same
				i++;
				continue;
			}
			if(placeComma){
				placeComma = false;
				s += ", ";
			}
			s += fields[i] + "='" + escapeString(iterNextValue) + "'";
			if (iterNew.hasNext()){
				placeComma = true;
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
			s += "'"+escapeString(iter.next().toString())+"'";
			if (iter.hasNext()){
				s += ", ";
			}
			i++;
		}
		return s;
	}

	private String escapeString(String s){
		return s.replaceAll("\'","\\\\'");
	}
}
