package com.kaylerrenslow.mysqlDatabaseTool.fx.control.db;

import com.kaylerrenslow.mysqlDatabaseTool.database.lib.SQLTypes;
import com.kaylerrenslow.mysqlDatabaseTool.fx.contextMenu.CM_DBTableView;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.factory.TableRowFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Kayler
 *         Class that holds a TableView for showing the database's information
 *         Created on 12/9/15.
 */
public class DBTable implements IDBTableData{
	public final TableView<ObservableList> tv;
	private ContextMenu cm;
	private boolean error = false;
	private String[] columnTypes;
	private JDBCType[] columnJDBCTypes;
	private String[] columnNames;

	/**The table row data that was edited before the last database synchronization*/
	private ArrayList<Integer> rowsEdited = new ArrayList<>();

	public DBTable(TableView tv) {
		this.tv = tv;
		initializeContextMenu();
	}

	private void initializeContextMenu() {
		cm = new CM_DBTableView(this);
		this.tv.setContextMenu(cm);
	}

	/**
	 * Clears the table and then adds the query data to the table.
	 */
	public void addQueryDataToTable(ResultSet rs) throws SQLException {
		error = false;
		tv.getColumns().clear();
		ResultSetMetaData rsmd = rs.getMetaData();

		//add all the table columns
		String colName;
		TableColumn tcol;
		this.columnTypes = new String[rsmd.getColumnCount()];
		this.columnNames = new String[rsmd.getColumnCount()];
		this.columnJDBCTypes = new JDBCType[rsmd.getColumnCount()];
		for (int col = 1; col <= rsmd.getColumnCount(); col++){
			colName = rsmd.getColumnName(col);
			this.columnTypes[col - 1] = SQLTypes.convertToString(rsmd.getColumnType(col));
			this.columnNames[col - 1] = colName;
			this.columnJDBCTypes[col - 1] = SQLTypes.convertToType(rsmd.getColumnType(col));
			tcol = new TableColumn<>(colName);
			tcol.setCellValueFactory(new TableRowFactory(col - 1));
			tv.getColumns().add(tcol);
		}

		//object that holds all the row data
		ObservableList<ObservableList> allRows = FXCollections.observableArrayList();

		addNewRows(rs, rsmd.getColumnCount(), allRows);

		tv.setItems(allRows);
	}

	private void addNewRows(ResultSet rs, int columnCount, ObservableList allRows) throws SQLException {
		ObservableList<String> row;

		//now add all of the row data
		while (rs.next()){
			row = FXCollections.observableArrayList();
			for (int col = 1; col <= columnCount; col++){
				row.add(rs.getString(col));
			}
			allRows.add(row);
		}
	}

	/**Mark a table row's data at index as edited. When database synchronization happens, this edited data will be used to update the database.*/
	private void markAsEdited(int index){
		if(!this.rowsEdited.contains(index)){
			this.rowsEdited.add(index);
		}
	}

	/**Gets an iterator for the edited rows.*/
	public Iterator<ObservableList> getEditedDataIterator(){
		return new EditedDataIterator(this);
	}

	/**Marks all the edited rows as unedited*/
	public void clearEdited(){
		this.rowsEdited.clear();
	}

	/**
	 * Adds an empty row to the table view.
	 */
	public void addEmptyRow() {
		if (!this.hasColumns()){
			throw new IllegalStateException("Can't add an empty row when there isn't any data to add to");
		}

		String[] data = new String[this.tv.getColumns().size()];
		for (int i = 0; i < this.tv.getColumns().size(); i++){
			data[i] = "newData";
		}
		ObservableList<String> row = FXCollections.observableArrayList();
		row.addAll(data);
		this.tv.getItems().add(row);
	}

	@Override
	public void updateData(int rowIndex, ObservableList newData) {
		this.tv.getItems().set(rowIndex, newData);
		this.markAsEdited(rowIndex);

	}

	@Override
	public ObservableList getData(int rowIndex) {
		return this.tv.getItems().get(rowIndex);
	}

	@Override
	public boolean hasColumns() {
		return tv.getColumns().size() > 0;
	}

	@Override
	public String[] getColumnTypes(){
		return this.columnTypes;
	}

	@Override
	public String[] getColumnNames(){
		return this.columnNames;
	}


	private class EditedDataIterator implements Iterator<ObservableList>{

		private final DBTable table;
		private int cursor = 0;

		public EditedDataIterator(DBTable table) {
			this.table = table;
		}

		@Override
		public boolean hasNext() {
			return cursor < table.rowsEdited.size();
		}

		@Override
		public ObservableList next() {
			return this.table.getData(cursor++);
		}
	}

}
