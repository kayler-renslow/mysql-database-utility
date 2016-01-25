package com.kaylerrenslow.mysqlDatabaseTool.dbGui;

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

		/**Denotes this task is used to update the server's database with the data in the table*/
		SYNCHRONIZE_DATA,

		/**Denotes this task is used to query a database. The query must be pre-set.*/
        RUN_QUERY
    }

    public final TaskType taskType;

    private final DBConnectionUpdate connUpdate;

    public DBTask(DBConnectionUpdate connUpdate, TaskType type) {
		this.connUpdate = connUpdate;
        this.taskType = type;
		this.valueProperty().addListener(connUpdate);
    }

    @Override
    protected Object call() throws Exception {
		connUpdate.setTask(this);
        switch (taskType){
            case CONNECT: Program.DATABASE_CONNECTION.connect(); break;
            case DISCONNECT: Program.DATABASE_CONNECTION.disconnect(); break;
            case RUN_QUERY: Program.DATABASE_CONNECTION.runQuery(); break;
			case SYNCHRONIZE_DATA: Program.DATABASE_CONNECTION.synchronize(); break;
        }
		return null; //return null so updateValue() won't call the value change listener
    }

	@Override
	public String getName() {
		return "DBTask:"+ this.taskType.name();
	}
}
