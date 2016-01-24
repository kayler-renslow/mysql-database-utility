package com.kaylerrenslow.mysqlDatabaseTool.dbGui;

/**
 * @author Kayler
 * Tells JavaFX components that there was an update with the DatabaseConnection's connection (connected, disconnected, etc.)
 *
 * Created on 11/9/15.
 */
public interface IConnectionUpdate {

    /**This method is used when the Database connection gets updated. (From connected to disconnected for example)
     * @param msg text to say something about the update. Can be null.
     * @param data any data that may be a result of the connection update. can be null.
     * */
    void connectionUpdate(String msg, Object data);
}
