package com.kaylerrenslow.mysqlDatabaseTool.fx.window;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.IFXWindow;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.util.ArrayList;

/**
 * @author Kayler
 * Created on 01/24/2016.
 */
public class AllTablesWindow extends ListView implements IFXWindow{
	public AllTablesWindow() {
		initialize();
	}

	private void initialize() {
		ArrayList<String> tables = Program.DATABASE_CONNECTION.getAllTableNames();
		ObservableList<String> list = FXCollections.observableArrayList();
		list.addAll(tables);
		this.getItems().addAll(list);
	}

	@Override
	public Region getRoot() {
		return this;
	}

	@Override
	public int getInitWidth() {
		return 400;
	}

	@Override
	public int getInitHeight() {
		return 600;
	}

	@Override
	public String getTitle() {
		return "All SQL tables";
	}
}
