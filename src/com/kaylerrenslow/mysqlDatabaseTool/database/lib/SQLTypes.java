package com.kaylerrenslow.mysqlDatabaseTool.database.lib;

import java.sql.JDBCType;

/**
 * @author Kayler
 * Created on 01/24/2016.
 */
public class SQLTypes{

	/**Converts java.sql.Types integer value into a String representation*/
	public static String convert(int type){
		try{
			return JDBCType.valueOf(type).getName();
		}catch (Exception e){
			return "?";
		}

	}

}
