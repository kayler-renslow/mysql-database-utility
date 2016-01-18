package com.kaylerrenslow.mysqlDatabaseTool.main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**Base controller class for the main window's fxml*/
public class InitController implements Initializable{
    @FXML
    VBox vboxMain;

    /**/

    @FXML
    MenuBar mainMenuBar;

    /**/

    @FXML
    TableView<String> tableQueryResults;

    /**/

    @FXML
    TextArea textAreaQuery;

    @FXML
    Button btnExecuteQuery;

    /**/

    @FXML
    TextField textFieldFileURL;

    @FXML
    Button btnLocateProperties;

    @FXML
    Button btnConnect;

    @FXML
    Button btnDisconnect;

    @FXML
    Label labelConnectionStatus;

    @FXML
    ProgressBar progressConnection;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Program.loadMainWindow(this);
    }
}
