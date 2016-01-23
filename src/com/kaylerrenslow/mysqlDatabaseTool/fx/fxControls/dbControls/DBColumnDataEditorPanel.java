package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.editableControl.EditableControl;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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
	private String data;
	private String columnName, columnDataType;
	private EditableControl dataEditor;

	private DBColumnEditorChoiceBox cb_editors;

	private boolean errorPanelShowing = false;

	public DBColumnDataEditorPanel(String columnName, String columnDataTye, String data, EditableControl dataEditor) {
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

		this.dataEditor.updateData(this.data);

		cb_editors.getSelectionModel().select(0);
	}

	public boolean setDataEditor(DBColumnEditors editor){
		EditableControl editorInstance = null;
		try{
			editorInstance = (EditableControl) Class.forName(editor.clazz.getName()).getConstructor().newInstance();
		}catch(Exception e){
			e.printStackTrace();
			addErrorMessage("Something reaaaally broke: " + e.getMessage());
			return false;
		}
		if(!editorInstance.supportsData(this.dataEditor.getData())){
			addErrorMessage(Lang.DB_COL_EDITOR_MSG_NOT_SUPPORTED);
			return false;
		}

		editorInstance.updateData(this.dataEditor.getData());

		this.getChildren().remove(1); //the editor
		this.getChildren().add(editorInstance.getControl());
		HBox.setHgrow(editorInstance.getControl(), Priority.ALWAYS);

		this.dataEditor = editorInstance;

		return true;
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

	public void addErrorMessage(String msg){
		Label lbl = new Label(msg);
//		lbl.setStyle("-fx-background-color:white;-fx-text-fill:red;");
		lbl.setOpaqueInsets(padding);
//		lbl.setFont(Font.font(15));
		HBox h = new HBox();
		h.setOpaqueInsets(padding);
		Button close = new Button("OK");
		close.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				removeHeaderMessage();
			}
		});
		HBox.setMargin(lbl, new Insets(0, 5 ,0, 0));
		h.getChildren().addAll(lbl, close);
		columnInformationBox.getChildren().add(h);

		this.errorPanelShowing = true;
	}

	public void removeHeaderMessage(){
		if(errorPanelShowing){
			columnInformationBox.getChildren().remove(columnInformationBox.getChildren().size() - 1);
			this.errorPanelShowing = false;
		}
	}

}
