package com.kaylerrenslow.mysqlDatabaseTool.fx.controllers;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.QueryType;
import com.kaylerrenslow.mysqlDatabaseTool.dbGui.DBTask;
import com.kaylerrenslow.mysqlDatabaseTool.dbGui.QueryExecutedEvent;
import com.kaylerrenslow.mysqlDatabaseTool.fx.FXUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTable;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTableEdit;
import com.kaylerrenslow.mysqlDatabaseTool.fx.window.DBDataEditorWindow;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

/**
 * @author Kayler
 *         Controller class that handles all database querying gui functionality. No database implementation execution is done here, besides calling the DatabaseConnection API.
 *         Created on 11/4/15.
 */
public class QueryFXController{
	private static final String STYLE_ERROR = "-fx-background:red;";
	private static final String STYLE_DEFAULT = "";
	private Button btnExecute;

	private TextArea tfTextQuery;
	private DBTable dbTable;

	private DBConnectionFXController connControl;
	public QueryExecutedEvent qee = new QueryExecutedEvent(this);

	private DBTask taskQuery;
	private DBTask taskSync;

	public QueryFXController(DBConnectionFXController connControl, TextArea queryText, Button btnExecute, ChoiceBox cbDmlDdl, TableView queryResultTable) {
		this.connControl = connControl;
		this.tfTextQuery = queryText;
		this.btnExecute = btnExecute;
		this.dbTable = new DBTable(queryResultTable);

		setConstructorListeners(cbDmlDdl);
	}

	private void setConstructorListeners(ChoiceBox cbDmlDdl) {
		cbDmlDdl.getItems().addAll(QueryType.DDL, QueryType.DML);
		cbDmlDdl.getSelectionModel().selectedItemProperty().addListener(new ChangeListener(){
			@Override
			public void changed(ObservableValue observable, Object oldValue, Object newValue) {
				if(cbDmlDdl.getSelectionModel().getSelectedItem() == QueryType.DDL){
					qee.updateQueryType(QueryType.DDL);
				}else{
					qee.updateQueryType(QueryType.DML);
				}
			}
		});
		cbDmlDdl.getSelectionModel().select(QueryType.DDL);
	}

	public void initialize() {
		this.btnExecute.setOnAction(qee);
		Program.DATABASE_CONNECTION.setQueryExecutedEvent(qee);
		Program.DATABASE_CONNECTION.setDBTable(dbTable);
		FXUtil.setEmptyContextMenu(this.tfTextQuery);

		setTasks();
	}

	private void setTasks() {
		taskQuery = new DBTask(this.connControl.getConnectionUpdate(), DBTask.TaskType.RUN_QUERY);
		taskSync = new DBTask(this.connControl.getConnectionUpdate(), DBTask.TaskType.SYNCHRONIZE_DATA);
	}

	/**
	 * Task used for making database queries
	 */
	public DBTask getQueryTask() {
		return this.taskQuery;
	}

	/**
	 * Get the query text area's text
	 */
	public String getQueryText() {
		return tfTextQuery.getText();
	}

	public DBTask getDBSynchronizeTask(){
		return this.taskSync;
	}

	/**
	 * This should be invoked whenever a query failed. It shows the context menu with the error and
	 * set's the query's text area border red.
	 */
	public void queryFailed(String errorMsg) {
		this.tfTextQuery.setStyle(STYLE_ERROR);
		this.connControl.getDatabaseFXController().setConsoleText(Lang.NOTIF_TITLE_QUERY_FAILED + "\n" + errorMsg);
	}

	/**
	 * Handle's the query result and adds it into the TableView
	 */
	public void querySuccess(ResultSet rs) throws SQLException {
		this.tfTextQuery.setStyle(STYLE_DEFAULT);
		this.dbTable.addQueryDataToTable(rs);
	}

	/**
	 * Add a new and empty entry to the database table
	 */
	public void addEmptyRow() {
		if (Program.DATABASE_CONNECTION.isConnected()){
			if (this.dbTable.hasColumns()){
				WebsiteDatabaseTool.createNewWindow(new DBDataEditorWindow(this.dbTable, this.dbTable.getRowSize(), this.dbTable.getNewRowData(), true));
			}else{
				this.connControl.getDatabaseFXController().setConsoleText(Lang.NOTIF_TITLE_NEW_ENTRY_ERROR + "\n" + Lang.NOTIF_BODY_NO_COLUMNS);
			}
		}else {
			this.connControl.getDatabaseFXController().setConsoleText(Lang.NOTIF_TITLE_NEW_ENTRY_ERROR + "\n" + Lang.NOTIF_BODY_NOT_CONNECTED);
		}
	}

	/**Returns true if an empty row can be added to the table, false otherwise*/
	public boolean canAddEmptyRow(){
		return Program.DATABASE_CONNECTION.isConnected() && this.dbTable.hasColumns();
	}

	/**Clears the entire table*/
	public void clearTable() {
		this.dbTable.clearTable();
	}

	public Iterator<DBTableEdit> tableEditIterator(){
		return this.dbTable.iterator(true);
	}

	public void removeLatestTableUpdate() {
		this.dbTable.undoLastEdit();
	}
}
