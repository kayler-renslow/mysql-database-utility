package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editorWindow;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editors.DBColumnEditors;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editors.EditableControl;
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
	private static final Insets marginTop10 = new Insets(10, 0, 0, 0);
	private static final String EDITOR = "Editor";
	private static final String COL_NAME = "Column Name";
	private static final String COL_TYPE = "Column Type";
	private static final String TEXT_BOLD = "-fx-font-weight:bold";

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

	/**Gets the editor's data*/
	public String getEditorData(){
		return this.dataEditor.getData();
	}

	/**Set the editor to a new data editor. The editor won't get added when the new editor doesn't support the data type of the current data in the current editor<br>
	 * @return true if the editor was added, false if it wasn't.
	 * */
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
			addErrorMessage(String.format(Lang.DB_COL_EDITOR_MSG_NOT_SUPPORTED, editor.editorName));
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

		Label lbl_cname_label = new Label(COL_NAME);
		lbl_cname_label.setStyle(TEXT_BOLD);

		Label lbl_cname = new Label(columnName);

		Label lbl_ctype_label = new Label(COL_TYPE);
		lbl_ctype_label.setStyle(TEXT_BOLD);
		VBox.setMargin(lbl_ctype_label, marginTop10);

		Label lbl_ctype = new Label(columnDataType);

		Label lbl_editor = new Label(EDITOR);
		lbl_editor.setStyle(TEXT_BOLD);
		VBox.setMargin(lbl_editor, marginTop10);

		cb_editors = new DBColumnEditorChoiceBox(this);
		this.columnInformationBox.getChildren().addAll(lbl_cname_label, lbl_cname, lbl_ctype_label, lbl_ctype, lbl_editor, cb_editors);
	}

	private void addErrorMessage(String msg){
		Label lbl = new Label(msg);
		lbl.setOpaqueInsets(padding);
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

	private void removeHeaderMessage(){
		if(errorPanelShowing){
			columnInformationBox.getChildren().remove(columnInformationBox.getChildren().size() - 1);
			this.errorPanelShowing = false;
		}
	}

}
