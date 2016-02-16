package com.kaylerrenslow.mysqlDatabaseTool.fx.actionEvent;

import com.kaylerrenslow.mysqlDatabaseTool.dbGui.Task;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.DatabaseFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * @author Kayler
 * This class is the facade layer from the GUI to the Database connection.
 * This class is used whenever the GUI connect or disconnect button is pressed.
 * Created on 11/9/15.
 */
public class ConnectionGUIAction implements EventHandler<ActionEvent> {
    private DatabaseFXController dc;
    private QueryFXController qc;

    /**True if this action represents the connect button being pressed, otherwise if the disconnect button was pressed*/
    private boolean connecting;

    public ConnectionGUIAction(DatabaseFXController dc, QueryFXController qc, boolean connecting) {
        this.dc = dc;
        this.qc = qc;
        this.connecting = connecting;
    }

    @Override
    public void handle(ActionEvent event) {
        if(connecting){
            connectButtonPress();
        }else{
            disconnectButtonPress();
        }
    }


    private void connectButtonPress() {
        Task.runTask(dc.getConnectTask());
    }

    private void disconnectButtonPress() {
        Task.runTask(this.dc.getDisconnectTask());
		this.qc.clearTabs();
    }
}
