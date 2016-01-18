package main;

import fx.fxControllers.DatabaseFXController;
import fx.fxControllers.MenuBarFXController;

/**
 * Created by kayler on 10/30/15.
 */
public class Controllers {
    private DatabaseFXController dc;
    private MenuBarFXController mbc;

    public Controllers(InitController cont){
        dc = new DatabaseFXController(cont.textAreaQuery, cont.btnExecuteQuery, cont.tableQueryResults, cont.textFieldFileURL, cont.btnLocateProperties, cont.btnConnect, cont.btnDisconnect, cont.labelConnectionStatus, cont.progressConnection);
        mbc = new MenuBarFXController(cont.mainMenuBar, dc);
    }
}
