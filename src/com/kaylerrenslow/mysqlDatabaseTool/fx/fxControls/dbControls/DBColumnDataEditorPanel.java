package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.editableControl.EditableControl;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * @author Kayler
 * Created on 01/21/2016.
 */
public class DBColumnDataEditorPanel extends HBox{
	private static final Insets padding = new Insets(5, 5, 5, 5);
	private static final Insets marginTop5 = new Insets(5, 0, 0, 0);
	private static final String EDITOR = "Editor";

	private VBox columnInformationBox = new VBox();
	private ObservableList<String> data;
	private String columnName, columnDataType;
	private EditableControl dataEditor;

	private DBColumnEditorChoiceBox cb_editors;

	public DBColumnDataEditorPanel(String columnName, String columnDataTye, ObservableList<String> data, EditableControl dataEditor) {
		this.data = data;
		this.columnName = columnName;
		this.columnDataType = columnDataTye;
		this.dataEditor = dataEditor;
		initialize();
	}

	private void initialize(){
		this.setPadding(padding);
		setupColumnInformationBox();
		this.getChildren().add(this.columnInformationBox);

		HBox.setHgrow(dataEditor.getControl(), Priority.ALWAYS);
		this.getChildren().add(dataEditor.getControl());

		cb_editors.getSelectionModel().select(0);
	}

	public void setDataEditor(DBColumnEditors editor){
		EditableControl editorInstance = null;
		try{
			editorInstance = (EditableControl) Class.forName(editor.clazz.getName()).getConstructor().newInstance();
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		editorInstance.updateData(this.dataEditor.getData());
		this.getChildren().remove(1); //the editor
		this.getChildren().add(editorInstance.getControl());
		HBox.setHgrow(editorInstance.getControl(), Priority.ALWAYS);
	}

	private void setupColumnInformationBox(){
		this.columnInformationBox.setPadding(padding);
		this.columnInformationBox.setMinWidth(140);

		Label lbl_cname = new Label(columnName);
		lbl_cname.setOpaqueInsets(marginTop5);

		Label lbl_ctype = new Label(columnDataType);
		lbl_ctype.setOpaqueInsets(marginTop5);

		Label lbl_editor = new Label(EDITOR);
		lbl_editor.setOpaqueInsets(marginTop5);

		cb_editors = new DBColumnEditorChoiceBox(this);
//		cb_editors.getSelectionModel().select(DBColumnEditors.TEXT.editorName);
		this.columnInformationBox.getChildren().addAll(lbl_cname, lbl_ctype, lbl_editor, cb_editors);
	}

}
