package com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade;

/**
 * @author Kayler
 * Tells JavaFX components that there was an update with the DatabaseConnection's connection (connected, disconnected, etc.)
 *
 * Created on 11/9/15.
 */
public interface IConnectionUpdate {

    /**This method is used when the Database connection gets updated. (From connected to disconnected for example)
     * @param msg text to say something about the update. Can be null.
     * */
    void connectionUpdate(String msg);
}
