package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers;

import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.DBConnectionUpdate;
import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.DBTask;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxActionEvent.ConnectionGUIAction;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxActionEvent.LocatePropFileAction;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.scene.control.*;

import java.io.File;

/**
 * @author Kayler
 * JavaFX controller class for all things database. This is also where Database Tasks (connecting and disconnecting) are created and used.
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

	private DBTask taskConnect, taskDisconnect;

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

		setTasks();
    }

	private void setTasks(){
		taskConnect = new DBTask(CONN_UPDATE, DBTask.TaskType.CONNECT);
		taskDisconnect = new DBTask(CONN_UPDATE, DBTask.TaskType.DISCONNECT);

		taskConnect.valueProperty().addListener(CONN_UPDATE);
		taskDisconnect.valueProperty().addListener(CONN_UPDATE);
	}


	/**Task used for connecting to the database*/
	public DBTask getConnectTask(){
		return this.taskConnect;
	}

	/**Task used for disconnecting the database connection*/
	public DBTask getDisconnectTask(){
		return this.taskDisconnect;
	}

    public void setPropertiesFileLocation(File file){
        this.tfPropFileLoc.setText(file.getPath());
		this.tfPropFileLoc.setTooltip(new Tooltip(file.getName()));
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
