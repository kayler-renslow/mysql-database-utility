package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.editableControl.EC_DatePicker;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.editableControl.EC_HTMLEditor;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.dbControls.editableControl.EC_TextArea;

/**
 * @author Kayler
 *         Created on 01/21/2016.
 */
public enum DBColumnEditors{

	TEXT("Text", EC_TextArea.class), HTML("HTML Editor", EC_HTMLEditor.class), DATE_PICKER("Date Picker", EC_DatePicker.class);

	public final String editorName;
	public final Class classs;

	DBColumnEditors(String editorName, Class c) {
		this.editorName = editorName;
		this.classs = c;
	}
}
