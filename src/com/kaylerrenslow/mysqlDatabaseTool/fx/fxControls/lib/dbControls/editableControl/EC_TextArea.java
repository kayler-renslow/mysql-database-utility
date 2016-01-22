package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.editableControl;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.DBColumnEditors;
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
}
