package fx.fxControllers;

import dbGuiFacade.ConnectionUpdate;
import dbGuiFacade.DBTask;
import fx.fxActionEvent.ConnectionGUIAction;
import fx.fxActionEvent.LocatePropFileAction;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import main.Program;

import java.io.File;

/**
 * Created by kayler on 11/6/15.
 */
public class DatabaseFXController {
    private Button btnLocateProperties;
    private Button btnDisconnect;
    private TextField tfPropFileLoc;
    private Button btnConnect;
    private Label lbStatus;
    private ProgressBar pbConnection;

    private final ConnectionUpdate CONN_UPDATE = new ConnectionUpdate(this);

    private QueryFXController qc;

    private DBTask taskConnect, taskDisconnect, taskQuery;

    public DatabaseFXController(TextArea queryText, Button btnExecute, TableView queryResultTable, TextField tfPropFileLoc, Button btnLocateProperties, Button btnConnect, Button btnDisconnect, Label lbStatus, ProgressBar pbConnection) {
        this.qc = new QueryFXController(this, queryText, btnExecute, queryResultTable);

        this.tfPropFileLoc = tfPropFileLoc;
        this.btnLocateProperties = btnLocateProperties;
        this.btnConnect = btnConnect;
        this.btnDisconnect = btnDisconnect;
        this.lbStatus = lbStatus;
        this.pbConnection = pbConnection;

        initialize();
    }

    private void initialize() {
        lbStatus.setText(Program.dbConnection.getConnectionStatus());
        btnConnect.setOnAction(new ConnectionGUIAction(this, true));
        btnDisconnect.setOnAction(new ConnectionGUIAction(this, false));
        btnLocateProperties.setOnAction(new LocatePropFileAction(this));

        Program.dbConnection.setConnectionUpdate(CONN_UPDATE);

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
        Program.dbConnection.setConnectionPropertiesFile(file);
    }

    public Task getConnectTask(){
        return this.taskConnect;
    }

    public Task getDisconnectTask(){
        return this.taskDisconnect;
    }

    public Task getQueryTask(){
        return this.taskQuery;
    }

    public void runTask(Task t) {
        Thread thread = new Thread(t);
        thread.setDaemon(true);
        thread.run();
    }

    public void updateStatusText(String text){
        this.lbStatus.setText(text);
    }

    public void updateConnectionProgress(double prog){
        this.pbConnection.setProgress(prog);
    }

    public void setProgressStyle(String fxStyle){
        this.pbConnection.setStyle(fxStyle);
    }

    public void createNewEntry() {
        this.qc.addEmptyRow();
    }
}
