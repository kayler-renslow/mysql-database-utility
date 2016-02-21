package com.kaylerrenslow.mysqlDatabaseTool.fx.window;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.ViewEditsPanel;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.IFXWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTable;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.MySQLDatabaseUtility;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Kayler
 *         Created on 02/13/2016.
 */
public class ConfirmSyncWindow extends VBox implements IFXWindow, EventHandler<ActionEvent>{
	private ViewEditsPanel editsPanel;
	private static final Insets MARGIN = new Insets(0, 5, 0, 0);
	private static final Insets PADDING = new Insets(5, 5, 5, 5);
	private static final Insets MARGIN_TOP5_BOTTOM5 = new Insets(5, 0, 5, 0);

	public ConfirmSyncWindow(DBTable table, QueryFXController qc) {
		this.editsPanel = new ViewEditsPanel(table, qc, false);
		this.editsPanel.setPadding(MARGIN_TOP5_BOTTOM5);
		Label lblmsg = new Label(String.format(Lang.WINDOW_CONFIRM_SYNC_MESSAGE, table.getTableName()));

		HBox hbOptions = new HBox();
		hbOptions.setAlignment(Pos.BOTTOM_RIGHT);
		Button btnConfirm = new Button("Yes");
		Button btnCancel = new Button("Cancel");
		HBox.setMargin(btnConfirm, MARGIN);
		hbOptions.getChildren().addAll(btnConfirm, btnCancel);

		btnCancel.setOnAction(this);
		btnConfirm.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				qc.forceSynchronizeToDatabase(table);
				closeWindow();
			}
		});

		this.setPadding(PADDING);
		this.getChildren().addAll(lblmsg, editsPanel, hbOptions);

		this.setFillWidth(true);
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
		return Lang.WINDOW_CONFIRM_SYNC_TITLE;
	}

	@Override
	public void handle(ActionEvent event) { //cancel action
		closeWindow();
	}

	private void closeWindow() {
		MySQLDatabaseUtility.closeWindow(this);
	}
}
