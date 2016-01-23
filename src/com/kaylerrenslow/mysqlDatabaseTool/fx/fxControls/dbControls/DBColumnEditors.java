package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.editableControl.EC_DatePicker;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.dbControls.editableControl.EC_TextArea;

/**
 * @author Kayler
 *         Created on 01/21/2016.
 */
public enum DBColumnEditors{

	TEXT("Text", EC_TextArea.class), DATE_PICKER("Date Picker", EC_DatePicker.class);

	public final String editorName;
	public final Class clazz;

	DBColumnEditors(String editorName, Class c) {
		this.editorName = editorName;
		this.clazz = c;
	}

	@Override
	public String toString() {
		return this.editorName;
	}
}
