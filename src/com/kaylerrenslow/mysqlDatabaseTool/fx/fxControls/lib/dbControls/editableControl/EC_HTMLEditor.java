package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.editableControl;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.DBColumnEditors;
import javafx.scene.web.HTMLEditor;

/**
 * Created by Kayler on 01/21/2016.
 */
public class EC_HTMLEditor extends EditableControl<HTMLEditor>{

	public EC_HTMLEditor() {
		super(new HTMLEditor(), DBColumnEditors.HTML.editorName);
	}

	@Override
	public void updateData(String data) {
		this.control.setHtmlText(data);
	}

	@Override
	public String getData() {
		return this.control.getHtmlText();
	}
}

