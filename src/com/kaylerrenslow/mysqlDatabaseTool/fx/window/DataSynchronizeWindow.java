package com.kaylerrenslow.mysqlDatabaseTool.fx.window;

import com.kaylerrenslow.mysqlDatabaseTool.dbGui.DBTask;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.IFXWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Kayler
 *         Created on 01/24/2016.
 */
public class DataSynchronizeWindow extends VBox implements IFXWindow, EventHandler<ActionEvent>{

	private static final String SYNCHRONIZE_TO_LABEL = "Enter the name of the table to upload the new data to.";
	private static final Insets margin = new Insets(5, 0, 0, 0);
	private final QueryFXController qc;
	private final TextField tftable = new TextField();


	public DataSynchronizeWindow(QueryFXController qc) {
		this.qc = qc;
		initialize();
	}

	private void initialize() {
		this.alignmentProperty().set(Pos.CENTER);
		Label l = new Label(SYNCHRONIZE_TO_LABEL);
		Button b = new Button("Go");

		b.setOnAction(this);

		tftable.setMaxWidth(211);

		VBox.setMargin(l, margin);
		VBox.setMargin(tftable, margin);
		VBox.setMargin(b, margin);

		this.getChildren().addAll(l, tftable, b);
	}

	@Override
	public void handle(ActionEvent event) {
		Program.DATABASE_CONNECTION.prepareSynchronize(this.tftable.getText());
		DBTask.runTask(qc.getDBSynchronizeTask());
		WebsiteDatabaseTool.closeWindow(this);
	}

	@Override
	public Region getRoot() {
		return this;
	}

	@Override
	public int getInitWidth() {
		return 380;
	}

	@Override
	public int getInitHeight() {
		return 110;
	}

	@Override
	public String getTitle() {
		return "Synchronize the table to the server";
	}
}
