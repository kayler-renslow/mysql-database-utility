package com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.DatabaseFXController;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * @author Kayler
 * When the Database connection updates (from connected to disconnected for example), the connectionUpdate() method is invoked.
 * Created on 11/9/15.
 */
public class ConnectionUpdate implements IConnectionUpdate, ChangeListener<Object> {

    private static final String STYLE_ERROR = "-fx-accent:red";
    private static final String STYLE_DEFAULT = "";

    private DatabaseFXController dc;
    private double prog;
    private boolean error;
    private String style = STYLE_DEFAULT;
    private Task task;

    public ConnectionUpdate(DatabaseFXController dc) {
        this.dc = dc;
    }

    @Override
    public void connectionUpdate() {
        switch(Program.DATABASE_CONNECTION.status) {
            case CONNECTED:
                setProgress(1.0);
                break;
            case CONNECTING:
                setProgress(-1.0);
                break;
            case DISCONNECTED:
                setProgress(0);
                break;
            default:
                error();
                break;
        }

        this.task.updateValue();
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newVal) {
        dc.updateStatusText(Program.DATABASE_CONNECTION.getConnectionStatus());
        dc.updateConnectionProgress(this.prog);
        dc.setProgressStyle(this.style);
    }

    private void setProgress(double prog){
        this.prog = prog;
        this.style = STYLE_DEFAULT;
    }

    private void error(){
        this.setProgress(1);
        this.error = true;
        this.style = STYLE_ERROR;
    }


    /** Set the task that is running the connection thread so that when
     * the connection updates, it will update the task's value. This is a required call for every new task execution.
     * @param task Task object to set the connection update to
     */
    public void setTask(Task task) {
        this.task = task;
    }

}
