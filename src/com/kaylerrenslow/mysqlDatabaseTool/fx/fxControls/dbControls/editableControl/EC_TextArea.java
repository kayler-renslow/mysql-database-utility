package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.editableControl;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.DBColumnEditors;
import javafx.scene.control.TextArea;

/**
 * @author Kayler
 * Created by on 01/21/2016.
 */
public class EC_TextArea extends EditableControl<TextArea>{

	public EC_TextArea() {
		super(new TextArea(), DBColumnEditors.TEXT.editorName);
	}

	@Override
	public void updateData(String data) {
		this.control.setText(data);
	}

	@Override
	public String getData() {
		return this.control.getText();
	}

	@Override
	public boolean supportsData(String data) {
		return true;
	}
}
