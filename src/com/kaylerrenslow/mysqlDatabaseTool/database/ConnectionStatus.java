package com.kaylerrenslow.mysqlDatabaseTool.database;

import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;

/**
 * @author Kayler
 *         All types of connection states/statuses. This is used in tandom with DBConnectionUpdate and DatabaseConnection
 *         Created on 11/6/15.
 */
public enum ConnectionStatus{
	/**Not connected to database. No extra data is passed with this status.*/
	DISCONNECTED(Lang.CONN_STATUS_DISCONNECTED),

	/**Attempting a connection to database. No extra data is passed with this status.*/
	CONNECTING(Lang.CONN_STATUS_CONNECTING),

	/**Connected to database. No extra data is passed with this status.*/
	CONNECTED(Lang.CONN_STATUS_CONNECTED),

	/**Error while attempting to connect to database. No extra data is passed with this status.*/
	CONNECTION_ERROR(Lang.CONN_STATUS_CONN_ERROR),

	/**The properties file for making a database connection is bad. No extra data is passed with this status.*/
	BAD_PROPERTIES(Lang.CONN_STATUS_BAD_PROP),

	/**This is used whenever a connection is attempted but no properties file is set. No extra data is passed with this status.*/
	NO_PROPERTIES(Lang.CONN_STATUS_NO_PROPERTIES),

	/**Not connected to database. This status is only reached after attempting a database implementation (i.e. query) and the connection is not set. No extra data is passed with this status.*/
	NOT_CONNECTED(Lang.CONN_STATUS_NOT_CONNECTED),

	/**Query is about to begin. No extra data is passed with this status.*/
	QUERY_BEGIN(Lang.CONN_STATUS_BEGIN_QUERY),

	/**Query ended successfully. The extra data is an instance of java.sql.ResultSet*/
	QUERY_END(Lang.CONN_STATUS_END_QUERY),

	/**Query failed. The extra data is a string with the query fail message*/
	QUERY_FAIL(Lang.CONN_STATUS_QUERY_ERROR);

	public String msg;

	ConnectionStatus(String msg) {
		this.msg = msg;
	}

	public boolean isQueryStatus(){
		return this == QUERY_BEGIN || this == QUERY_END || this == QUERY_FAIL;
	}

}
