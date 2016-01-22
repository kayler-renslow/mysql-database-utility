package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.editableControl.EditableControl;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Kayler
 * Created on 01/21/2016.
 */
public class DBColumnDataEditor extends HBox{
	private static final Insets padding = new Insets(5, 5, 5, 5);
	private static final Insets marginTop5 = new Insets(5, 0, 0, 0);
	private static final String EDITOR = "Editor";

	private VBox columnInformationBox = new VBox();
	private ObservableList<String> data;
	private String columnName, columnDataType;
	private EditableControl dataEditor;

	public DBColumnDataEditor(String columnName, String columnDataTye, ObservableList<String> data, EditableControl dataEditor) {
		this.data = data;
		this.columnName = columnName;
		this.columnDataType = columnDataTye;
		this.dataEditor = dataEditor;
		initialize();
	}

	private void initialize(){
		this.setPadding(padding);
		setupColumnInformationBox();

		HBox.setHgrow(dataEditor.getControl(), Priority.ALWAYS);
		this.getChildren().add(dataEditor.getControl());
	}

	public void setDataEditor(EditableControl editor){
		editor.updateData(this.dataEditor.getData());
		this.getChildren().remove(0);
		this.getChildren().add(editor.getControl());

	}

	private void setupColumnInformationBox(){
		this.columnInformationBox.setPadding(padding);
		this.columnInformationBox.setMinWidth(140);

		Label lbl_cname = new Label(columnName);
		lbl_cname.setOpaqueInsets(marginTop5);

		Label lbl_ctype = new Label(columnName);
		lbl_ctype.setOpaqueInsets(marginTop5);

		Label lbl_editor = new Label(EDITOR);
		lbl_editor.setOpaqueInsets(marginTop5);

		ChoiceBox<String> cb_editors = new ChoiceBox<>();

		for(DBColumnEditors editor: DBColumnEditors.values()){
			cb_editors.getItems().add(editor.editorName);
		}

		this.columnInformationBox.getChildren().addAll(lbl_cname, lbl_ctype, lbl_editor, cb_editors);
	}


}
