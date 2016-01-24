package com.kaylerrenslow.mysqlDatabaseTool.fx.control;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.DBColumnDataEditorPanel;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editor.EC_TextArea;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.IFXWindow;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Kayler
 * Created on 01/21/2016.
 */
public class DBDataEditorWindow extends VBox implements IFXWindow{

	private ObservableList<String> data;
	private VBox content = new VBox();

	public DBDataEditorWindow(String[] columnNames, String[] columnTypes, ObservableList<String> data) {
		ScrollPane scrollPane = new ScrollPane(content);
		this.data = data;
		this.fillWidthProperty().set(true);
		this.content.fillWidthProperty().set(true);
		scrollPane.setFitToWidth(true);

		VBox.setVgrow(scrollPane, Priority.ALWAYS);

		this.getChildren().add(scrollPane);
		initialize(columnNames, columnTypes);
	}

	private void initialize(String[] columnNames, String[] columnTypes) {
		DBColumnDataEditorPanel panel;
		for(int i = 0; i < this.data.size(); i++){
			panel = new DBColumnDataEditorPanel(columnNames[i], columnTypes[i], data.get(i), new EC_TextArea());
			if(i%2 != 0){
				panel.setStyle("-fx-background-color:#dddddd");
			}
			this.content.getChildren().add(panel);
		}
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
