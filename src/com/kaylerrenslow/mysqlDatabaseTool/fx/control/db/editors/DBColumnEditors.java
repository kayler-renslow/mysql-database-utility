package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editors;

/**
 * @author Kayler
 *         Created on 01/21/2016.
 */
public enum DBColumnEditors{

	TEXT("Text", EC_TextArea.class),
	HTML_EDITOR("HTML Editor", EC_HTMLEditor.class);

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
