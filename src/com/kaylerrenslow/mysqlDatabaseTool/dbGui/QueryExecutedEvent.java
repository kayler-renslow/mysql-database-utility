package com.kaylerrenslow.mysqlDatabaseTool.dbGui;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.MysqlQueryResult;
import com.kaylerrenslow.mysqlDatabaseTool.database.lib.QueryType;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *@author Kayler
 * This class is used when the execute query button is pressed. When the query itself is finished, querySuccess() or queryFail() is ran from DBConnectionUpdate
 *
 * Created on 11/9/15.
 */
public class QueryExecutedEvent implements IQueryExecuteEvent, EventHandler<ActionEvent> {
    private QueryFXController qc;
    private QueryType qt;

    public QueryExecutedEvent(QueryFXController controller) {
        this.qc = controller;
    }

    @Override
    public void handle(ActionEvent event) {
        runQuery();
    }

    public void updateQueryType(QueryType qt){
        this.qt = qt;
    }

    private void runQuery(){
        Program.DATABASE_CONNECTION.prepareQuery(qc.getQueryText(), this.qt);
        Task.runTask(this.qc.getQueryTask());
    }

    @Override
    public void querySuccess(MysqlQueryResult rs) {
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