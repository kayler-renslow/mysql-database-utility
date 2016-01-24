package com.kaylerrenslow.mysqlDatabaseTool.fx.controllers;

import com.kaylerrenslow.mysqlDatabaseTool.dbGui.DBConnectionUpdate;

/**
 * @author Kayler
 *         Created on 01/23/2016.
 */
public class DBConnectionFXController{
	private DBConnectionUpdate connUpdate;
	private QueryFXController qc;
	private DatabaseFXController dc;

	public void initialize(DatabaseFXController dc, QueryFXController qc) {
		connUpdate = new DBConnectionUpdate(dc);
		this.qc = qc;
		this.dc = dc;
	}

	public DBConnectionUpdate getConnectionUpdate() {
		return connUpdate;
	}

	public DatabaseFXController getDatabaseFXController() {
		return this.dc;
	}

	public QueryFXController getQueryFXController(){
		return this.qc;
	}
}
