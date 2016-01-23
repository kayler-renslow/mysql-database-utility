package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls;

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

	private void selectionChange(int index) {
		this.panel.setDataEditor(this.getItems().get(index));
	}

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		selectionChange(newValue.intValue());
	}
}
