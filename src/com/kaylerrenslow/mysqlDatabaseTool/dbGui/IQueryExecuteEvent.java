package com.kaylerrenslow.mysqlDatabaseTool.dbGui;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.MysqlQueryResult;
/**
 * @author Kayler
 * Interface used to tell JavaFX controllers that a query was executed in the DatabaseConnection.
 *
 * Created on 11/11/15.
 */
public interface IQueryExecuteEvent {
    /**This method is ran when a query has been successfully executed. MysqlQueryResult rs is the result of the query.*/
    void querySuccess(MysqlQueryResult rs);

    /**This method is ran when a query that was executed failed.
     * @param failMsg message that explains why the query failed*/
    void queryFail(String failMsg);
}
