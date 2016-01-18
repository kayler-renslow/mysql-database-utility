package database;

import database.lib.MysqlConnection;
import database.lib.QueryFailedException;
import dbGuiFacade.IConnectionUpdate;
import dbGuiFacade.IQueryExecuteEvent;
import main.Lang;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;

/**
 * Created by kayler on 11/6/15.
 *
 * Runs a database connection and query utility synchronously.
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
            connectionUpdate();
            return;
        }
        if(!conn.connectionPropertiesSet()){
            try{
                conn.loadConnectionProperties(this.propFile);
            }catch(IllegalArgumentException e){
                //e.printStackTrace();
                status = ConnectionStatus.BAD_PROPERTIES;
                connectionUpdate();
                return;
            }catch(IOException ioe){
                ioe.printStackTrace();
                status = ConnectionStatus.BAD_PROPERTIES;
                connectionUpdate();
            }
        }
        status = ConnectionStatus.CONNECTING;
        connectionUpdate();
        conn.connect();

        if(!conn.isConnected()){
            status = ConnectionStatus.CONNECTION_ERROR;
            connectionUpdate();
            return;
        }
        status = ConnectionStatus.CONNECTED;
        connectionUpdate();
    }


    /**Disconnects from the database synchronously. This has no effect if the connection was already disconnected.*/
    public void disconnect(){
        conn.disconnect();
        status = ConnectionStatus.DISCONNECTED;
        connectionUpdate();
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
            connectionUpdate();
            return;
        }
        try{
            ResultSet rs = conn.query(this.sql);
            this.qee.querySuccess(rs);
        }catch (QueryFailedException e){
            this.qee.queryFail(e.getMessage());
        }
    }

    private void connectionUpdate(){
        if(this.conUpdate == null){
            return;
        }
        this.conUpdate.connectionUpdate();
    }

    public boolean isConnected(){
        return this.conn.isConnected();
    }

}
