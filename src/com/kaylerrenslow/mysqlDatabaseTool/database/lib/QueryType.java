package com.kaylerrenslow.mysqlDatabaseTool.database.lib;

/**
 * @author Kayler
 * Created on 02/09/2016.
 */
public enum QueryType{
	DML("DML"), DDL("DDL");

	final String text;
	QueryType(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
}
