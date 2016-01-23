package com.kaylerrenslow.mysqlDatabaseTool.main;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.DBConnectionFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.DatabaseFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.MenuBarFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.QueryFXController;

/**
 * @author Kayler
 * Contains all the controllers for the program. There is only one instance of this class and it is in Program.java
 *
 * Created on 10/30/15
 */
public class Controllers {

    private QueryFXController qc;
    private DatabaseFXController dc;
    private MenuBarFXController mbc;
	private DBConnectionFXController dbCon = new DBConnectionFXController();

    Controllers(InitController cont){
        dc = new DatabaseFXController(dbCon, cont.textFieldFileURL, cont.btnLocateProperties, cont.btnConnect, cont.btnDisconnect, cont.labelConnectionStatus, cont.progressConnection, cont.taConsole);
        qc = new QueryFXController(dbCon, cont.textAreaQuery, cont.btnExecuteQuery, cont.tableQueryResults);

		dbCon.initialize(dc);
		dc.initialize();
		qc.initialize();

        mbc = new MenuBarFXController(cont.mainMenuBar, qc);
    }
}
