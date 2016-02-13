package com.kaylerrenslow.mysqlDatabaseTool.fx.window;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.ViewEditsPanel;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.IFXWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTable;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;

/**
 * @author Kayler
 *         Created on 02/09/2016.
 */
public class ViewEditsWindow implements IFXWindow{
	private String title;
	private ViewEditsPanel editsPanel;
	private static final Insets PADDING = new Insets(5, 5, 5, 5);

	public ViewEditsWindow(DBTable table, QueryFXController qc) {
		this.title = Lang.WINDOW_VIEW_EDITS_PREFIX + table.getTableName();
		this.editsPanel = new ViewEditsPanel(table, qc, true);
		this.editsPanel.setPadding(PADDING);
	}

	@Override
	public Region getRoot() {
		return this.editsPanel;
	}

	@Override
	public int getInitWidth() {
		return 500;
	}

	@Override
	public int getInitHeight() {
		return 500;
	}

	@Override
	public String getTitle() {
		return title;
	}
}
