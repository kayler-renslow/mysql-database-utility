package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db;

import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTable;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTableEdit;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Iterator;

/**
 * @author Kayler
 *         Created on 02/13/2016.
 */
public class ViewEditsPanel extends VBox{
	private static final String TF_STYLE_DELETED_DATA = "-fx-background-color:red; -fx-text-fill:white;";
	private static final String TF_STYLE_NEW_DATA = "-fx-background-color:green; -fx-text-fill:white;";

	private static final String TF_STYLE_OLD_DATA = "";

	private static final Insets MARGIN_BOTTOM5 = new Insets(0, 0, 5, 0);
	private static final Insets MARGIN_RIGHT5 = new Insets(0, 5, 0, 0);
	private static final Insets PADDING = new Insets(5, 5, 5, 5);
	private VBox vbEdits = new VBox();
	private ScrollPane scrollPane = new ScrollPane(vbEdits);

	public ViewEditsPanel(DBTable table, QueryFXController qc, boolean showRemoveEditsButton) {
		initialize(table, qc, showRemoveEditsButton);
	}

	private void initialize(DBTable table, QueryFXController qc, boolean showEditsButton) {
		this.scrollPane.setFitToWidth(true);


		Iterator<DBTableEdit> iter = table.iterator(true);

		Button btnRemoveLastestEdit = new Button("Remove Latest Edit");
		if (showEditsButton){
			this.getChildren().add(btnRemoveLastestEdit);
		}
		VBox.setMargin(btnRemoveLastestEdit, MARGIN_BOTTOM5);

		btnRemoveLastestEdit.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				table.undoLastEdit();
				removeFirstChild();
			}
		});

		if (!iter.hasNext()){ //no edits
			Label l = new Label(Lang.WINDOW_VIEW_EDITS_NO_EDITS);
			this.vbEdits.getChildren().add(l);
			btnRemoveLastestEdit.setDisable(true);
		}else {
			while (iter.hasNext()){
				addEditHboxPanel(iter.next());
			}
		}

		this.getChildren().add(scrollPane);

	}

	private void addEditHboxPanel(DBTableEdit edit) {
		TextField tfOldData;
		TextField tfNewData;
		VBox vbDataChange;
		Label lblEditType;
		HBox hbRowContent;

		tfOldData = new TextField();
		tfNewData = new TextField();
		tfOldData.setMaxWidth(Double.MAX_VALUE);
		tfNewData.setMaxWidth(Double.MAX_VALUE);

		tfOldData.setEditable(false);
		tfNewData.setEditable(false);
		vbDataChange = new VBox();
		VBox.setMargin(tfOldData, MARGIN_BOTTOM5);
		if (edit.type() == DBTableEdit.EditType.DELETION){
			tfOldData.setStyle(TF_STYLE_DELETED_DATA);
			tfOldData.setText(edit.oldRowData().toString());

			vbDataChange.getChildren().add(tfOldData);
		}else if (edit.type() == DBTableEdit.EditType.UPDATE){
			tfOldData.setStyle(TF_STYLE_OLD_DATA);
			tfOldData.setText(edit.oldRowData().toString());

			tfNewData.setText(edit.newRowData().toString());
			tfNewData.setStyle(TF_STYLE_NEW_DATA);

			vbDataChange.getChildren().addAll(tfOldData, tfNewData);
		}else if (edit.type() == DBTableEdit.EditType.ADDITION){
			tfNewData.setText(edit.newRowData().toString());
			tfNewData.setStyle(TF_STYLE_NEW_DATA);

			vbDataChange.getChildren().add(tfNewData);
		}

		lblEditType = new Label(edit.type().name());

		hbRowContent = new HBox();
		hbRowContent.setMaxWidth(Double.MAX_VALUE);
		hbRowContent.getChildren().addAll(lblEditType, vbDataChange);
		vbDataChange.setFillWidth(true);
		HBox.setHgrow(vbDataChange, Priority.ALWAYS);
		HBox.setMargin(lblEditType, MARGIN_RIGHT5);

		hbRowContent.setPadding(PADDING);

		this.vbEdits.getChildren().add(hbRowContent);

		HBox.setHgrow(tfNewData, Priority.ALWAYS);
		HBox.setHgrow(tfOldData, Priority.ALWAYS);
		VBox.setMargin(hbRowContent, MARGIN_BOTTOM5);
	}

	private void removeFirstChild() {
		if (this.vbEdits.getChildren().size() > 0){
			this.vbEdits.getChildren().remove(0);
		}
	}
}
