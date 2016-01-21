package com.kaylerrenslow.mysqlDatabaseTool.database.lib;

/**
 * @author Kayler
 * Created on 12/2/15.
 */
public class QueryFailedException extends Exception {
    public QueryFailedException(String message) {
        super(message);
    }
}
