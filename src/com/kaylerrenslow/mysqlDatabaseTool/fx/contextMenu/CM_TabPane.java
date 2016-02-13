package com.kaylerrenslow.mysqlDatabaseTool.fx.contextMenu;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;

/**
 * Created by Kayler on 02/13/2016.
 */
public class CM_TabPane extends ContextMenu implements IFXMenuEventHandle{
	private final QueryFXController qc;
	private final Tab parent;

	private FXMenuItem closeTab = new FXMenuItem(Lang.CONTEXT_MENU_TAB_PANE_CLOSE);

	public CM_TabPane(Tab parent, QueryFXController qc) {
		this.qc = qc;
		this.parent = parent;
		FXMenuUtil.addItems(this, this, closeTab);
	}

	@Override
	public void handle(int index, ActionEvent event) {
		if(closeTab.matchesIndex(index)){
			this.qc.removeTab(this.parent);
		}
	}
}
