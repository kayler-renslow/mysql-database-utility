package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers;

import com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade.QueryExecutedEvent;
import com.kaylerrenslow.mysqlDatabaseTool.fx.FXUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.DBTableView;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.collections.ObservableList;
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
    private DatabaseFXController dc;
    private QueryExecutedEvent qee = new QueryExecutedEvent(this);

    public QueryFXController(DatabaseFXController dc, TextArea queryText, Button btnExecute, TableView queryResultTable) {
        this.dc = dc;
        this.tfTextQuery = queryText;
        this.btnExecute = btnExecute;
        this.dbTable = new DBTableView(queryResultTable);
        initialize();
    }

    private void initialize() {
        this.btnExecute.setOnAction(qee);
        Program.DATABASE_CONNECTION.setQueryExecutedEvent(qee);
        FXUtil.setEmptyContextMenu(this.tfTextQuery);
    }

    /**Get the DatabaseFXController associated with this QueryFXController*/
    public DatabaseFXController getDatabaseController(){
        return this.dc;
    }

    /**Get the query text area's text*/
    public String getQueryText() {
        return tfTextQuery.getText();
    }

    /**Return the Database's TableView for displaying query results.*/
    public TableView<ObservableList> getDBTableView() {
        return dbTable.tv;
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

    void addEmptyRow(){
        if(Program.DATABASE_CONNECTION.isConnected()){
           this.dbTable.addEmptyRow();
        }else{
            this.getDBTableInstance().addTableRowError(Lang.NOTIF_TITLE_NEW_ENTRY_ERROR, Lang.NOTIF_BODY_NOT_CONNECTED);
        }

    }
}
