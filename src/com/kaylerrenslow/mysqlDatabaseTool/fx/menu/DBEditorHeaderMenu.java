package com.kaylerrenslow.mysqlDatabaseTool.fx.menu;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editorWindow.DBDataEditorWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;

/**
 * @author Kayler
 * Created on 01/24/2016.
 */
public class DBEditorHeaderMenu extends Menu implements IFXMenuEventHandle{

	private FXMenuItem save = new FXMenuItem(Lang.DB_EDITOR_MENU_DATA_SAVE);
	private DBDataEditorWindow window;

	public DBEditorHeaderMenu(DBDataEditorWindow window) {
		super(Lang.DB_EDITOR_MENU_TITLE);
		this.window = window;
		initialize();
	}

	private void initialize() {
		FXMenuUtil.addItems(this, this, save);
	}

	@Override
	public void handle(int index, ActionEvent event) {
		if(save.matchesIndex(index)){
			window.save();
		}
	}
}
