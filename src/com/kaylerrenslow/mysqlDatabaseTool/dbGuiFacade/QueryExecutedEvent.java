package com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *@author Kayler
 * This class is used when the execute query button is pressed. When the query itself is finished, it runs either querySuccess() or queryFail()
 *
 * Created on 11/9/15.
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
        Program.DATABASE_CONNECTION.prepareQuery(qc.getQueryText());
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