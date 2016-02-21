package com.kaylerrenslow.mysqlDatabaseTool.dbGui;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.MysqlQueryResult;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.DatabaseFXController;
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

	private Object data;

    public DBConnectionUpdate(DatabaseFXController dc) {
        this.dc = dc;
    }

    @Override
    public void connectionUpdate(String msg, Object data) {
        /*This method is NOT run on the JavaFX thread.*/

		if(this.task == null){
			System.err.println("WARNING: task for the DBConnectionUpdate is not set.");
			return;
        }
        switch(Program.DATABASE_CONNECTION.status()) {
            case CONNECTED:
                setProgress(1.0);
                break;
            case CONNECTING:
                setProgress(-1.0);
                break;
            case DISCONNECTED:
                setProgress(0);
                break;
			case QUERY_BEGIN:
				setProgress(-1.0);
				break;
			case QUERY_END:
				setProgress(1.0);
				break;
			case QUERY_END_UPDATE:
				setProgress(1);
				break;
			case QUERY_FAIL:
				this.dc.setConsoleText(msg, true);
				setProgress(1);
				break;
            default:
                error(msg);
                break;
        }
		this.data = data;
		this.task.notifyValuePropertyListeners();
    }

	private void queryError() {
		Program.DATABASE_CONNECTION.getQueryExecuteEvent().queryFail((String) this.data);
	}

	private void queryEnd() {
		Program.DATABASE_CONNECTION.getQueryExecuteEvent().querySuccess((MysqlQueryResult) this.data);
	}

	@Override
    public void changed(ObservableValue observable, Object oldValue, Object newVal) {
		/*this method IS ran on the JavaFX thread*/

		if(Program.DATABASE_CONNECTION.status().isQueryStatus()){
			switch (Program.DATABASE_CONNECTION.status()){
				case QUERY_END:
					queryEnd();
					break;
				case QUERY_FAIL:
					queryError();
					break;
			}
		}else{
			dc.updateStatusText(Program.DATABASE_CONNECTION.getConnectionStatusMessage());
		}
        dc.updateConnectionProgress(this.progress);
        dc.setProgressStyle(this.style);
    }

    private void setProgress(double progress){
        this.progress = progress;
        this.style = STYLE_DEFAULT;
		this.dc.setConsoleText(null, false);
    }

    private void error(String msg){
		this.setProgress(1);
        this.style = STYLE_ERROR;
		this.dc.setConsoleText(msg, true);
    }


    /** Set the task that is running the connection thread so that when
     * the connection updates, it will update the task's value. This is a required call for every new task execution.
     * @param task Task object to set the connection update to
     */
    public void setTask(Task task) {
        this.task = task;
    }

}
