package com.kaylerrenslow.mysqlDatabaseTool.database;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.ConnectionException;
import com.kaylerrenslow.mysqlDatabaseTool.database.lib.MysqlConnection;
import com.kaylerrenslow.mysqlDatabaseTool.database.lib.QueryFailedException;
import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.IConnectionUpdate;
import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.IQueryExecuteEvent;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * @author Kayler
 * Runs a database connection and query utility synchronously.
 * Created on 11/6/15.
 */
public class DatabaseConnection {
    private File propFile;
    private MysqlConnection conn = new MysqlConnection(System.out);
    private IConnectionUpdate conUpdate;
    private IQueryExecuteEvent qee;
    private String sql;

    public ConnectionStatus status = ConnectionStatus.DISCONNECTED;

    /**The conUpdate is what is used to describe the state of the connection through each step of the connection (disconnect, connect, query, etc).*/
    public void setConnectionUpdate(IConnectionUpdate conEvent){
        this.conUpdate = conEvent;
    }

    public void setQueryExecutedEvent(IQueryExecuteEvent qee){
        this.qee = qee;
    }

    /**Sets the database connection properties file*/
    public void setConnectionPropertiesFile(File propCon){
        this.propFile = propCon;
    }

    /**Sets the current SQL query to parameter sql*/
    public void prepareQuery(String sql){
        this.sql = sql;
    }


    /**Attempts a connection to the database synchronously. */
    public void connect(){
        if(propFile == null){
            status = ConnectionStatus.NO_PROPERTIES;
            connectionUpdate("No properties file");
            return;
        }
        if(!conn.connectionPropertiesSet()){
            try{
                conn.loadConnectionProperties(this.propFile);
            }catch(IllegalArgumentException e){
                //e.printStackTrace();
                status = ConnectionStatus.BAD_PROPERTIES;
                connectionUpdate(e.getMessage());
                return;
            }catch(IOException ioe){
                ioe.printStackTrace();
                status = ConnectionStatus.BAD_PROPERTIES;
                connectionUpdate("Error occurred with the properties file.\n"+ioe.getMessage());
            }
        }
        status = ConnectionStatus.CONNECTING;
        connectionUpdate(null);
        try{
            conn.connect();
        }catch(ConnectionException e){
            status = ConnectionStatus.CONNECTION_ERROR;
            connectionUpdate("An error occurred while trying to connect to the database.\n"+e.getMessage());
            return;
        }

        status = ConnectionStatus.CONNECTED;
        connectionUpdate(null);
    }


    /**Disconnects from the database synchronously. This has no effect if the connection was already disconnected.*/
    public void disconnect(){
        conn.disconnect();
        status = ConnectionStatus.DISCONNECTED;
        connectionUpdate(null);
    }


    /**Get a String of the current status' message.*/
    public String getConnectionStatus(){
        return Lang.DB_STATUS_PREFIX + status.msg;
    }

    /**Runs the prepared query synchronously*/
    public void runQuery(){
        if(!this.isConnected()){
            this.status = ConnectionStatus.NOT_CONNECTED;
            this.qee.queryFail(Lang.NOTIF_BODY_NOT_CONNECTED);
            connectionUpdate(null);
            return;
        }
        try{
            ResultSet rs = conn.query(this.sql);
            this.qee.querySuccess(rs);
        }catch (QueryFailedException e){
            this.qee.queryFail(e.getMessage());
        }
    }

    private void connectionUpdate(String msg){
        if(this.conUpdate == null){
            return;
        }
        this.conUpdate.connectionUpdate(msg);
    }

    public boolean isConnected(){
        return this.conn.isConnected();
    }

}
