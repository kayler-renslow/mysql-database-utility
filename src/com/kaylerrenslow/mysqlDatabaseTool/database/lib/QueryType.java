package com.kaylerrenslow.mysqlDatabaseTool.database.lib;

/**
 * This enum is used to determine a type of a query being executed.
 * @author Kayler
 * Created on 02/09/2016.
 */
public enum QueryType{
	/**Used when SELECT is used*/
	SELECTION("SELECTION"),

	/**Used when ALTER, UPDATE, INSERT, etc are used*/
	UPDATE("UPDATE");

	final String text;
	QueryType(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
