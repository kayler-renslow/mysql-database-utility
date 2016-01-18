package dbGuiFacade;

import fx.fxControllers.QueryFXController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import main.Program;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by kayler on 11/9/15.
 *
 * This class is used when the execute query button is pressed. When the query itself is finished, it runs either querySuccess() or queryFail()
 */
public class QueryExecutedEvent implements IQueryExecuteEvent, EventHandler<ActionEvent> {
    private QueryFXController qc;

    public QueryExecutedEvent(QueryFXController controller) {
        this.qc = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        runQuery();
    }

    private void runQuery(){
        Program.dbConnection.prepareQuery(qc.getQueryText());
        this.qc.getDatabaseController().runTask(this.qc.getDatabaseController().getQueryTask());
    }

    @Override
    public void querySuccess(ResultSet rs) {
        try{
            handleQuery(rs);
            this.qc.querySuccess();
        }catch(Exception e){
            e.printStackTrace();
            this.queryFail(e.getMessage());
        }
    }

    @Override
    public void queryFail(String failMsg) {
        this.qc.queryFailed(failMsg);
    }

    /**Handles the result of the query by adding all the query data into the tableview*/
    private void handleQuery(ResultSet rs) throws SQLException{
        this.qc.querySuccess(rs);
    }

}