package com.kaylerrenslow.mysqlDatabaseTool.main;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.DatabaseFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.MenuBarFXController;

/**
 * @author Kayler
 * Contains all the controllers for the program. There is only one instance of this class and it is in Program.java
 *
 * Created on 10/30/15
 */
public class Controllers {
    private DatabaseFXController dc;
    private MenuBarFXController mbc;

    Controllers(InitController cont){
        dc = new DatabaseFXController(cont.textAreaQuery, cont.btnExecuteQuery, cont.tableQueryResults, cont.textFieldFileURL, cont.btnLocateProperties, cont.btnConnect, cont.btnDisconnect, cont.labelConnectionStatus, cont.progressConnection);
        mbc = new MenuBarFXController(cont.mainMenuBar, dc);
    }
}
