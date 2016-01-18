package com.kaylerrenslow.mysqlDatabaseTool.database;

import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;

/**
 * @author Kayler
 * All types of connection states/statuses
 * Created on 11/6/15.
 */
public enum ConnectionStatus{
    DISCONNECTED(Lang.CONN_STATUS_DISCONNECTED), CONNECTING(Lang.CONN_STATUS_CONNECTING), CONNECTED(Lang.CONN_STATUS_CONNECTED), CONNECTION_ERROR(Lang.CONN_STATUS_CONN_ERROR),
    BAD_PROPERTIES(Lang.CONN_STATUS_BAD_PROP), NO_PROPERTIES(Lang.CONN_STATUS_NO_PROPERTIES), NOT_CONNECTED(Lang.CONN_STATUS_NOT_CONNECTED);

    public String msg;
    ConnectionStatus(String msg){
        this.msg = msg;
    }

}
