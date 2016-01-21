package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers;

import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.DBConnectionUpdate;
import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.DBTask;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxActionEvent.ConnectionGUIAction;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxActionEvent.LocatePropFileAction;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.concurrent.Task;
import javafx.scene.control.*;

import java.io.File;

/**
 * @author Kayler
 * JavaFX controller class for all things database. Also contains the QueryFXController instance. This is also where Database Tasks are created and used.
 * Created on 11/6/15.
 */
public class DatabaseFXController {
    private Button btnLocateProperties;
    private Button btnDisconnect;
    private TextField tfPropFileLoc;
    private Button btnConnect;
    private Label lbStatus;
    private ProgressBar pbConnection;
    private TextArea taConsole;

    private final DBConnectionUpdate CONN_UPDATE = new DBConnectionUpdate(this);

    private DBTask taskConnect, taskDisconnect, taskQuery;

    public DatabaseFXController(TextField tfPropFileLoc, Button btnLocateProperties, Button btnConnect, Button btnDisconnect, Label lbStatus, ProgressBar pbConnection, TextArea taConsole) {
        this.tfPropFileLoc = tfPropFileLoc;
        this.btnLocateProperties = btnLocateProperties;
        this.btnConnect = btnConnect;
        this.btnDisconnect = btnDisconnect;
        this.lbStatus = lbStatus;
        this.pbConnection = pbConnection;
        this.taConsole = taConsole;

        initialize();
    }

    private void initialize() {
        lbStatus.setText(Program.DATABASE_CONNECTION.getConnectionStatus());
        btnConnect.setOnAction(new ConnectionGUIAction(this, true));
        btnDisconnect.setOnAction(new ConnectionGUIAction(this, false));
        btnLocateProperties.setOnAction(new LocatePropFileAction(this));

		taConsole.setEditable(false);

        Program.DATABASE_CONNECTION.setConnectionUpdate(CONN_UPDATE);

        setTasks();
    }

    private void setTasks(){
        taskConnect = new DBTask(CONN_UPDATE, DBTask.TaskType.CONNECT);
        taskDisconnect = new DBTask(CONN_UPDATE, DBTask.TaskType.DISCONNECT);
        taskQuery = new DBTask(CONN_UPDATE, DBTask.TaskType.RUN_QUERY);

        taskConnect.valueProperty().addListener(CONN_UPDATE);
        taskDisconnect.valueProperty().addListener(CONN_UPDATE);
        taskQuery.valueProperty().addListener(CONN_UPDATE);
    }

    public void setPropertiesFileLocation(File file){
        this.tfPropFileLoc.setText(file.getPath());
        Program.DATABASE_CONNECTION.setConnectionPropertiesFile(file);
		this.tfPropFileLoc.setTooltip(new Tooltip(file.getName()));
    }

	/**Task used for connection to the database*/
    public Task getConnectTask(){
        return this.taskConnect;
    }

	/**Task used for disconnecting the database connection*/
    public Task getDisconnectTask(){
        return this.taskDisconnect;
    }

	/**Task used for making database queries*/
    public Task getQueryTask(){
        return this.taskQuery;
    }

	/**Run a task. The task specified should be the tasks available in this class.*/
    public void runTask(Task t) {
        Thread thread = new Thread(t);
        thread.setDaemon(true);
        thread.run();
    }

	/**Update the connection status text*/
    public void updateStatusText(String text){
        this.lbStatus.setText(text);
    }

	/**Update the connection progress bar*/
    public void updateConnectionProgress(double prog){
        this.pbConnection.setProgress(prog);
    }

	/**Set the connection progress bar style*/
    public void setProgressStyle(String fxStyle){
        this.pbConnection.setStyle(fxStyle);
    }

    /**Sets the console text*/
    public void setConsoleText(String text){
        this.taConsole.setText((text == null ? "" : text));
    }

    /**Returns the console text*/
    public String getConsoleText(){
        return this.taConsole.getText();
    }

}
