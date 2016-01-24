package com.kaylerrenslow.mysqlDatabaseTool.dbGuiFacade;

import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.ResultSet;

/**
 *@author Kayler
 * This class is used when the execute query button is pressed. When the query itself is finished, querySuccess() or queryFail() is ran from DBConnectionUpdate
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
        Task.runTask(this.qc.getQueryTask());
    }

    @Override
    public void querySuccess(ResultSet rs) {
        try{
            this.qc.querySuccess(rs);
        }catch(Exception e){
            e.printStackTrace();
            this.queryFail(e.getMessage());
        }
    }

    @Override
    public void queryFail(String failMsg) {
        this.qc.queryFailed(failMsg);
    }


}