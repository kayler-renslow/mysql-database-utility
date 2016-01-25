package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editorWindow;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editors.EC_TextArea;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.IFXWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTableEdit;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.IDBTableData;
import com.kaylerrenslow.mysqlDatabaseTool.fx.menu.DBEditorHeaderMenu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Kayler
 *         Created on 01/21/2016.
 */
public class DBDataEditorWindow extends VBox implements IFXWindow{

	private ObservableList<String> oldData;
	private ObservableList<String> newData;
	private VBox vb_content = new VBox();
	private MenuBar menuBar = new MenuBar();
	private DBColumnDataEditorPanel[] panels;

	private final IDBTableData table;
	private final int rowIndex;

	public DBDataEditorWindow(IDBTableData table, int rowIndex, ObservableList<String> data) {
		initializeHeaderMenu();
		this.table = table;
		this.rowIndex = rowIndex;
		initializeContent(data);
	}

	private void initializeHeaderMenu() {
		menuBar.getMenus().addAll(new DBEditorHeaderMenu(this));
		this.getChildren().add(menuBar);
	}

	private void initializeContent(ObservableList<String> data) {
		ScrollPane scrollPane = new ScrollPane(vb_content);
		this.oldData = data;

		this.newData = FXCollections.observableArrayList(new String[this.oldData.size()]); //create a new list with the same size as the old one
		FXCollections.copy(newData, this.oldData);

		this.fillWidthProperty().set(true);
		this.vb_content.fillWidthProperty().set(true);
		scrollPane.setFitToWidth(true);

		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		this.getChildren().add(scrollPane);
		addRowData();
	}

	private void addRowData() {
		panels = new DBColumnDataEditorPanel[this.newData.size()];
		for (int i = 0; i < this.newData.size(); i++){
			panels[i] = new DBColumnDataEditorPanel(table.getColumnNames()[i], table.getColumnTypes()[i], newData.get(i), new EC_TextArea());
			if (i % 2 != 0){
				panels[i].setStyle("-fx-background-color:#dddddd");
			}
			this.vb_content.getChildren().add(panels[i]);
		}
	}

	public void save() {
		for (int i = 0; i < this.panels.length; i++){
			this.newData.set(i, this.panels[i].getEditorData());
		}
		this.table.updateData(DBTableEdit.EditType.UPDATE, this.rowIndex, this.newData, this.oldData);
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
