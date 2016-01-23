package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.DBColumnDataEditorPanel;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.editableControl.EC_TextArea;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.window.IFXWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Created by Kayler on 01/21/2016.
 */
public class DBDataEditorWindow extends VBox implements IFXWindow{

	public DBDataEditorWindow() {
		initialize();
	}

	private void initialize() {
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("Test");
		this.getChildren().add(new DBColumnDataEditorPanel("Column", "data type", list, new EC_TextArea()));
	}

	@Override
	public Region getRoot() {
		return this;
	}

	@Override
	public int getInitWidth() {
		return 640;
	}

	@Override
	public int getInitHeight() {
		return 320;
	}

	@Override
	public String getTitle() {
		return "Edit Table Row Window";
	}
}
