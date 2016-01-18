package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.DatabaseFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.fxMenu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.fxMenu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.fxMenu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import javafx.event.ActionEvent;

/**
 * @author Kayler
 * Created on 12/7/15.
 */
public class MainMenu_Data extends javafx.scene.control.Menu implements IFXMenuEventHandle {

    private DatabaseFXController dc;
    private FXMenuItem menuNewEntry = new FXMenuItem(Lang.MENUB_DATA_NEW_ENTRY);
    private FXMenuItem menuSyncData = new FXMenuItem(Lang.MENUB_DATA_SYNC_DATA);

    public MainMenu_Data(DatabaseFXController dc) {
        super(Lang.MENUB_DATA_TITLE);
        FXMenuUtil.addItems(this, this, menuNewEntry, menuSyncData);
        this.dc = dc;
    }

    @Override
    public void handle(int index, ActionEvent event) {
        if(index == menuNewEntry.getInsertionIndex()){
            dc.createNewEntry();
        }else if(index == menuSyncData.getInsertionIndex()){
            System.out.println("MainMenu_Data>> sync data pressed");
        }else{
            return;
        }
    }
}
