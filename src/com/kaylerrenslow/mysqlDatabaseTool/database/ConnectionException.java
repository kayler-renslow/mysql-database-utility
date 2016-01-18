package com.kaylerrenslow.mysqlDatabaseTool.database;

/**
 * @author Kayler
 * Created on 11/3/15.
 */
public class ConnectionException extends Exception {

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException() {}
}
