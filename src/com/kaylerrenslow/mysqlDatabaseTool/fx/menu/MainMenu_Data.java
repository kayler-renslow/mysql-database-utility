package com.kaylerrenslow.mysqlDatabaseTool.fx.menu;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.window.AllTablesWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.window.DataSynchronizeWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.window.ViewEditsWindow;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;

/**
 * @author Kayler
 * Created on 12/7/15.
 */
public class MainMenu_Data extends javafx.scene.control.Menu implements IFXMenuEventHandle, EventHandler<Event> {

    private QueryFXController qc;
    private FXMenuItem menuNewEntry = new FXMenuItem(Lang.MENUB_DATA_NEW_ENTRY);
    private FXMenuItem menuSyncData = new FXMenuItem(Lang.MENUB_DATA_SYNC_DATA);
    private FXMenuItem menuListTables = new FXMenuItem(Lang.MENUB_DATA_LIST_TABLES);
	private FXMenuItem menuViewEdits = new FXMenuItem(Lang.MENUB_DATA_VIEW_EDITS);

    public MainMenu_Data(QueryFXController qc) {
        super(Lang.MENUB_DATA_TITLE);
        FXMenuUtil.addItems(this, this, menuNewEntry, menuSyncData, menuListTables, menuViewEdits);
        this.qc = qc;
		this.setOnShowing(this);
    }

    @Override
    public void handle(int index, ActionEvent event) {
        if(menuNewEntry.matchesIndex(index)){
            qc.addEmptyRow();
        }else if(menuSyncData.matchesIndex(index)){
			WebsiteDatabaseTool.createNewWindow(new DataSynchronizeWindow(this.qc));
        }else if(menuListTables.matchesIndex(index)){
            WebsiteDatabaseTool.createNewWindow(new AllTablesWindow());
        }else if(menuViewEdits.matchesIndex(index)){
			WebsiteDatabaseTool.createNewWindow(new ViewEditsWindow(qc));
		}
    }

	@Override
	public void handle(Event event) {
		for(MenuItem mi : this.getItems()){
			mi.disableProperty().set(!Program.DATABASE_CONNECTION.isConnected());
		}
		this.menuSyncData.disableProperty().set(!qc.canAddEmptyRow());
		this.menuNewEntry.disableProperty().set(!qc.canAddEmptyRow());
	}
}
