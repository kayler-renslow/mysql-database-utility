package com.kaylerrenslow.mysqlDatabaseTool.fx.menu;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

/**
 * @author Kayler
 *         Created on 12/7/15.
 */
public class MainMenu_Data extends javafx.scene.control.Menu implements IFXMenuEventHandle, EventHandler<Event>{

	final MenuItem noItems = new MenuItem(Lang.MENUB_ITEM_NO_TABLES);
	QueryFXController qc;
	Menu menuNewTable = new Menu(Lang.MENUB_ITEM_ADD_NEW_TABLE);

	public MainMenu_Data(QueryFXController qc) {
		super(Lang.MENUB_TITLE_VIEW);
		this.getItems().addAll(menuNewTable);
		this.qc = qc;
		this.setOnShowing(this);
		menuNewTable.setOnShowing(new ShowTablesEvent(this));
		menuNewTable.getItems().add(noItems);
	}

	@Override
	public void handle(int index, ActionEvent event) {
		//this class's menu is showing
	}

	@Override
	public void handle(Event event) {
		for (MenuItem mi : this.getItems()){
			mi.disableProperty().set(!Program.DATABASE_CONNECTION.isConnected());
		}
	}

	private class ShowTablesEvent implements EventHandler<Event>, IFXMenuEventHandle{

		private final MainMenu_Data mainMenu;

		public ShowTablesEvent(MainMenu_Data mainMenu) {
			this.mainMenu = mainMenu;
		}

		@Override
		public void handle(Event event) {
			menuNewTable.getItems().clear();
			ArrayList<String> tables = Program.DATABASE_CONNECTION.getAllTableNames();
			if(tables.size() == 0){
				menuNewTable.getItems().add(mainMenu.noItems);
				return;
			}
			for (int i = 0; i < tables.size(); i++){
				FXMenuItem item = new FXMenuItem(tables.get(i));
				FXMenuUtil.addItems(this.mainMenu.menuNewTable, this, item);
			}
		}

		@Override
		public void handle(int index, ActionEvent event) {
			MenuItem item = menuNewTable.getItems().get(index);
			this.mainMenu.qc.addTab(item.getText());
		}
	}
}
