package com.kaylerrenslow.mysqlDatabaseTool.fx.controllers;

import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.DBConnectionUpdate;

/**
 * @author Kayler
 *         Created on 01/23/2016.
 */
public class DBConnectionFXController{
	private DBConnectionUpdate connUpdate;

	public void initialize(DatabaseFXController dc) {
		connUpdate = new DBConnectionUpdate(dc);
	}

	public DBConnectionUpdate getConnectionUpdate() {
		return connUpdate;
	}
}
