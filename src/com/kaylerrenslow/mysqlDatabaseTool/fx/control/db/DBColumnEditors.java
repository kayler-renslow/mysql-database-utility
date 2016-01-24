package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editor.EC_DatePicker;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editor.EC_TextArea;

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
