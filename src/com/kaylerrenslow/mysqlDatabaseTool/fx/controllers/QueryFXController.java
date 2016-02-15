package com.kaylerrenslow.mysqlDatabaseTool.fx.controllers;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.MysqlQueryResult;
import com.kaylerrenslow.mysqlDatabaseTool.database.lib.QueryType;
import com.kaylerrenslow.mysqlDatabaseTool.dbGui.DBTask;
import com.kaylerrenslow.mysqlDatabaseTool.dbGui.QueryExecutedEvent;
import com.kaylerrenslow.mysqlDatabaseTool.fx.FXUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.contextMenu.CM_TabPane;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTable;
import com.kaylerrenslow.mysqlDatabaseTool.fx.window.ConfirmSyncWindow;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;

import java.sql.SQLException;
import java.util.ArrayList;

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
	private DBTable dbTableQueryResults;

	private TabPane tabPane;
	private ArrayList<TabTableView> tabs = new ArrayList<>();

	public final DBConnectionFXController connControl;
	public QueryExecutedEvent qee = new QueryExecutedEvent(this);

	private DBTask taskQuery;
	private DBTask taskSync;

	private TabTableView tableForQuery;
	private boolean initialized;

	public QueryFXController(DBConnectionFXController connControl, TextArea queryText, Button btnExecute, ChoiceBox cbDmlDdl, TabPane tabPane) {
		this.connControl = connControl;
		this.tfTextQuery = queryText;
		this.btnExecute = btnExecute;
		this.tabPane = tabPane;

		this.dbTableQueryResults = new DBTable(null, this);
		setConstructorListeners(cbDmlDdl);
	}

	private void initTabPane() {
		addTab(Lang.TAB_PANE_TITLE_QUERY_RESULT, this.dbTableQueryResults);
	}

	private void setConstructorListeners(ChoiceBox<QueryType> cbDmlDdl) {
		cbDmlDdl.getItems().addAll(QueryType.values());
		cbDmlDdl.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QueryType>(){
			@Override
			public void changed(ObservableValue observable, QueryType oldValue, QueryType newValue) {
				qee.updateQueryType((QueryType) cbDmlDdl.getSelectionModel().getSelectedItem());
			}
		});
		cbDmlDdl.getSelectionModel().select(QueryType.SELECTION);
	}

	public void initialize() {
		this.btnExecute.setOnAction(qee);
		Program.DATABASE_CONNECTION.setQueryExecutedEvent(qee);
		Program.DATABASE_CONNECTION.setDBTable(dbTableQueryResults);
		FXUtil.setEmptyContextMenu(this.tfTextQuery);

		setTasks();
		initTabPane();
		this.initialized = true;
	}

	private void setTasks() {
		taskQuery = new DBTask(this.connControl.getConnectionUpdate(), DBTask.TaskType.RUN_QUERY);
		taskSync = new DBTask(this.connControl.getConnectionUpdate(), DBTask.TaskType.SYNCHRONIZE_DATA);
	}

	/**
	 * Adds a new tab to the tab pane.
	 */
	public void addTab(String tabName) {
		DBTable table = new DBTable(tabName, this);
		addTab(tabName, table);
	}

	private void addTab(String tabName, DBTable table) {
		TabTableView ttt = new TabTableView(tabName, table, this);
		tabs.add(ttt);
		tabPane.getTabs().add(ttt);
		if (table != this.dbTableQueryResults){
			ttt.setContextMenu(new CM_TabPane(ttt, this));
		}
		loadDataIntoTable(ttt);

	}

	/**Fetch the data for the new tab*/
	private void loadDataIntoTable(TabTableView ttt) {
		if (!this.initialized){ //don't run when this controller is being initialized because it will make the progress bar red since the query field will be empty
			return;
		}
		tableForQuery = ttt;
		ttt.getTable().clearTable();
		Program.DATABASE_CONNECTION.prepareQueryToLoadTable(ttt.getTable().getTableName());
		Program.DATABASE_CONNECTION.setDBTable(ttt.getTable());
		DBTask.runTask(getQueryTask());
	}

	/**
	 * Removes the given tab from the tab pane
	 */
	public void removeTab(Tab tab) {
		if (tab == this.tabs.get(0)){
			throw new IllegalArgumentException("Not allowed to close the query result tab");
		}
		this.tabs.remove(tab);
		this.tabPane.getTabs().remove(tab);
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

	public DBTask getDBSynchronizeTask() {
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
	public void querySuccess(MysqlQueryResult rs) throws SQLException {
		this.tfTextQuery.setStyle(STYLE_DEFAULT);
		this.tableForQuery.getTable().addQueryDataToTable(rs);
		this.tabPane.getSelectionModel().select(this.tableForQuery); //focus to tab
		tableForQuery = tabs.get(0); //default query result tab
		Program.DATABASE_CONNECTION.setDBTable(dbTableQueryResults);
	}

	/**
	 * Clears all the tables and removes all table tabs
	 */
	public void clearTables() {
		this.tabs.clear();
		this.tabPane.getTabs().clear();
		initTabPane();
	}

	/**Prompts the user to confirm the sync*/
	public void synchronizeToDatabase(DBTable table) {
		WebsiteDatabaseTool.createNewWindow(new ConfirmSyncWindow(table, this));
	}

	/**Syncs the data without notifying the user.*/
	public void forceSynchronizeToDatabase(DBTable table) {
		Program.DATABASE_CONNECTION.setDBTable(table);
		DBTask.runTask(getDBSynchronizeTask());
	}

	/**Refreshes the given DBTable. Refreshing is clearing the current tableview's data and pulling the data from the database and injecting that into the tableview*/
	public void refreshTable(DBTable dbTable) {
		for(TabTableView ttt : tabs){
			if(ttt.getTable() == dbTable){
				loadDataIntoTable(ttt);
				return;
			}
		}
		throw new IllegalArgumentException("Can't refresh table " + dbTable.getTableName());
	}

	private class TabTableView extends Tab{

		private DBTable table;

		public TabTableView(String tabName, DBTable table, QueryFXController qc) {
			super(tabName);
			if (table == null){
				this.table = new DBTable(tabName, qc);
			}else {
				this.table = table;
			}
			ScrollPane scroll = new ScrollPane(this.table.getTableView());
			scroll.setFitToWidth(true);
			scroll.setFitToHeight(true);
			this.setContent(scroll);
		}

		public DBTable getTable() {
			return this.table;
		}
	}

}
