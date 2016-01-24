package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editorWindow;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editors.DBColumnEditors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;

/**
 * @author Kayler
 * Created on 01/22/2016.
 */
public class DBColumnEditorChoiceBox extends ChoiceBox<DBColumnEditors> implements ChangeListener<Number>{
	private DBColumnDataEditorPanel panel;

	public DBColumnEditorChoiceBox(DBColumnDataEditorPanel panel) {
		this.panel = panel;
		initialize();
	}

	private void initialize() {
		for (DBColumnEditors editor : DBColumnEditors.values()){
			this.getItems().add(editor);
		}
		this.getSelectionModel().selectedIndexProperty().addListener(this);
	}

	private boolean selectionChange(int index) {
		return this.panel.setDataEditor(this.getItems().get(index));
	}

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		if(!selectionChange(newValue.intValue())){
			this.getSelectionModel().select(oldValue.intValue());
		}
	}
}
