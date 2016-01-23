package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers;

import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.DBTask;
import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.QueryExecutedEvent;
import com.kaylerrenslow.mysqlDatabaseTool.fx.FXUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.DBTableView;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Kayler
 * Controller class that handles all database querying gui functionality. No database implementation execution is done here, besides calling the DatabaseConnection API.
 * Created on 11/4/15.
 */
public class QueryFXController {
    private static final String STYLE_ERROR = "-fx-background:red;";
    private static final String STYLE_DEFAULT = "";

    private Button btnExecute;
    private TextArea tfTextQuery;
    private DBTableView dbTable;
    private QueryExecutedEvent qee = new QueryExecutedEvent(this);

    private DBTask taskQuery;

    public QueryFXController(DatabaseFXController dc, TextArea queryText, Button btnExecute, TableView queryResultTable) {
        this.tfTextQuery = queryText;
        this.btnExecute = btnExecute;
        this.dbTable = new DBTableView(queryResultTable);
        initialize(dc);
    }

    private void initialize(DatabaseFXController dc) {
        this.btnExecute.setOnAction(qee);
        Program.DATABASE_CONNECTION.setQueryExecutedEvent(qee);
        FXUtil.setEmptyContextMenu(this.tfTextQuery);

        setTasks(dc);
    }

    private void setTasks(DatabaseFXController dc) {
        taskQuery = new DBTask(dc.CONN_UPDATE, DBTask.TaskType.RUN_QUERY);

        taskQuery.valueProperty().addListener(dc.CONN_UPDATE);
        /**Task used for connection to the database*/
    }

    /**Task used for making database queries*/
    public DBTask getQueryTask(){
        return this.taskQuery;
    }

    /**Get the query text area's text*/
    public String getQueryText() {
        return tfTextQuery.getText();
    }

    /**Return the DBTableView instance*/
    public DBTableView getDBTableInstance(){
        return dbTable;
    }

    /**This should be invoked whenever a query succeeds.
     * Simply resets the text query's style in case there was previously an error.*/
    public void querySuccess(){
        this.tfTextQuery.setStyle(STYLE_DEFAULT);
    }

    /**This should be invoked whenever a query failed. It shows the context menu with the error and
     * set's the query's text area border red.*/
    public void queryFailed(String errorMsg) {
        this.tfTextQuery.setStyle(STYLE_ERROR);

        this.dbTable.addTableRowError(Lang.NOTIF_TITLE_QUERY_FAILED, errorMsg);
    }

    /**Handle's the query result and adds it into the TableView*/
    public void querySuccess(ResultSet rs) throws SQLException{
        this.dbTable.querySuccess(rs);
    }

    /**Add a new and empty entry to the database table*/
    public void addEmptyRow(){
        if(Program.DATABASE_CONNECTION.isConnected()){
           this.dbTable.addEmptyRow();
        }else{
            this.getDBTableInstance().addTableRowError(Lang.NOTIF_TITLE_NEW_ENTRY_ERROR, Lang.NOTIF_BODY_NOT_CONNECTED);
        }

    }
}
