package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers;

import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.DBTask;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxActionEvent.ConnectionGUIAction;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxActionEvent.LocatePropFileAction;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

import java.io.File;

/**
 * @author Kayler
 * JavaFX controller class for all things database. This is also where Database Tasks (connecting and disconnecting) are created and used.
 * Created on 11/6/15.
 */
public class DatabaseFXController {
	private DBConnectionFXController connControl;
	private Button btnLocateProperties;
    private Button btnDisconnect;
    private TextField tfPropFileLoc;
    private Button btnConnect;
    private Label lbStatus;
    private ProgressBar pbConnection;
    private TextArea taConsole;

	private DBTask taskConnect, taskDisconnect;

    public DatabaseFXController(DBConnectionFXController connControl, TextField tfPropFileLoc, Button btnLocateProperties, Button btnConnect, Button btnDisconnect, Label lbStatus, ProgressBar pbConnection, TextArea taConsole) {
        this.connControl = connControl;
		this.tfPropFileLoc = tfPropFileLoc;
        this.btnLocateProperties = btnLocateProperties;
        this.btnConnect = btnConnect;
        this.btnDisconnect = btnDisconnect;
        this.lbStatus = lbStatus;
        this.pbConnection = pbConnection;
        this.taConsole = taConsole;

    }

    public void initialize() {
        lbStatus.setText(Program.DATABASE_CONNECTION.getConnectionStatusMessage());
        btnConnect.setOnAction(new ConnectionGUIAction(this, true));
        btnDisconnect.setOnAction(new ConnectionGUIAction(this, false));
        btnLocateProperties.setOnAction(new LocatePropFileAction(this));

		tfPropFileLoc.textProperty().addListener(new ChangeListener<String>(){
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				boolean good = LocatePropFileAction.attemptSetConnectionPropertiesFile(newValue);
				if(good){
					tfPropFileLoc.setStyle("");
				}else{
					tfPropFileLoc.setStyle("-fx-background-color:red;-fx-text-fill:white;");
				}
			}
		});

		taConsole.setEditable(false);

		Program.DATABASE_CONNECTION.setConnectionUpdate(connControl.getConnectionUpdate());

		setTasks();
    }

	private void setTasks(){
		taskConnect = new DBTask(connControl.getConnectionUpdate(), DBTask.TaskType.CONNECT);
		taskDisconnect = new DBTask(connControl.getConnectionUpdate(), DBTask.TaskType.DISCONNECT);

		taskConnect.valueProperty().addListener(connControl.getConnectionUpdate());
		taskDisconnect.valueProperty().addListener(connControl.getConnectionUpdate());
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
