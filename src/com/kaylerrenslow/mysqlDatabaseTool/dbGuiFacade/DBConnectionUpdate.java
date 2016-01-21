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
public class DBConnectionUpdate implements IConnectionUpdate, ChangeListener<Object> {

    private static final String STYLE_ERROR = "-fx-accent:red";
    private static final String STYLE_DEFAULT = "";

    private DatabaseFXController dc;
    private double progress;
    private String style = STYLE_DEFAULT;
    private Task task;

    public DBConnectionUpdate(DatabaseFXController dc) {
        this.dc = dc;
    }

    @Override
    public void connectionUpdate(String msg) {
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
                error(msg);
                break;
        }
        this.task.updateValue();
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newVal) {
        dc.updateStatusText(Program.DATABASE_CONNECTION.getConnectionStatus());
        dc.updateConnectionProgress(this.progress);
        dc.setProgressStyle(this.style);
    }

    private void setProgress(double progress){
        this.progress = progress;
        this.style = STYLE_DEFAULT;
		this.dc.setConsoleText(null);
    }

    private void error(String msg){
        this.setProgress(1);
        this.style = STYLE_ERROR;
        this.dc.setConsoleText(msg);
    }


    /** Set the task that is running the connection thread so that when
     * the connection updates, it will update the task's value. This is a required call for every new task execution.
     * @param task Task object to set the connection update to
     */
    public void setTask(Task task) {
        this.task = task;
    }

}
