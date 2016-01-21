package com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade;

import com.kaylerrenslow.mysqlDatabaseTool.main.Program;

/**
 * @author Kayler
 * Database implementations are executed on this task. A task is JavaFX's way of placing execution off of the GUI thread and onto a new one.
 * Created on 11/16/15.
 */
public class DBTask extends Task{

    public enum TaskType{
        /**Denotes this task is used to connect to a database*/
        CONNECT,

        /**Denotes this task is used to disconnect from a database*/
        DISCONNECT,

        /**Denotes this task is used to query a database. The query must be pre-set.*/
        RUN_QUERY
    }

    public final TaskType taskType;

    public DBTask(DBConnectionUpdate connUpdate, TaskType type) {
        super(connUpdate);
        this.taskType = type;
    }

    @Override
    protected Object call() throws Exception {
        connUpdate.setTask(this);
        switch (taskType){
            case CONNECT: Program.DATABASE_CONNECTION.connect(); break;
            case DISCONNECT: Program.DATABASE_CONNECTION.disconnect(); break;
            case RUN_QUERY: Program.DATABASE_CONNECTION.runQuery(); break;
        }
        return this.getValue();
    }

    @Override
    public void run() {
        super.runAndReset();
    }

}
