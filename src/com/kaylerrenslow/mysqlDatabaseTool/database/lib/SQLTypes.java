package com.kaylerrenslow.mysqlDatabaseTool.database.lib;

import java.sql.JDBCType;

/**
 * @author Kayler
 * Created on 01/24/2016.
 */
public class SQLTypes{

	/**Converts java.sql.Types integer value into a String representation*/
	public static String convertToString(int type){
		JDBCType t = convertToType(type);
		return t == null ? "?" : t.getName();

	}

	/**Converts java.sql.Types integer value into JDBC enum value. Will return null if the type is undefined*/
	public static JDBCType convertToType(int type) {
		try{
			return JDBCType.valueOf(type);
		}catch (Exception e){
			return null;
		}
	}
}
