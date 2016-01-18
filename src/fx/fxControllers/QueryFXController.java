package fx.fxControllers;

import dbGuiFacade.QueryExecutedEvent;
import fx.FXUtil;
import fx.fxControls.DBTableView;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import main.Lang;
import main.Program;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kayler on 11/4/15.
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
        Program.dbConnection.setQueryExecutedEvent(qee);
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
        if(Program.dbConnection.isConnected()){
           this.dbTable.addEmptyRow();
        }else{
            this.getDBTableInstance().addTableRowError(Lang.NOTIF_TITLE_NEW_ENTRY_ERROR, Lang.NOTIF_BODY_NOT_CONNECTED);
        }

    }
}
