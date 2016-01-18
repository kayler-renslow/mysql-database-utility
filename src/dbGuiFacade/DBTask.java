package dbGuiFacade;

import main.Program;

/**
 * Created by kayler on 11/16/15.
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

    public DBTask(ConnectionUpdate connUpdate, TaskType type) {
        super(connUpdate);
        this.taskType = type;
    }

    @Override
    protected Object call() throws Exception {
        connUpdate.setTask(this);
        switch (taskType){
            case CONNECT: Program.dbConnection.connect(); break;
            case DISCONNECT: Program.dbConnection.disconnect(); break;
            case RUN_QUERY: Program.dbConnection.runQuery(); break;
        }
        return this.getValue();
    }

    @Override
    public void run() {
        super.runAndReset();
    }

}
