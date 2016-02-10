package com.kaylerrenslow.mysqlDatabaseTool.fx.db;

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
	private final TableView<ObservableList> tv;
	private ContextMenu cm;
	private String[] columnTypes;
	private JDBCType[] columnJDBCTypes;
	private String[] columnNames;

	/**The table row data that was edited before the last database synchronization*/
	private ArrayList<DBTableEdit> rowsEdited = new ArrayList<>();

	public DBTable(TableView tv) {
		this.tv = tv;
		initializeContextMenu();
	}

	private void initializeContextMenu() {
		cm = new CM_DBTableView(this);
		this.tv.setContextMenu(cm);
	}

	public void clearTable(){
		this.tv.getItems().clear();
	}

	/**
	 * Clears the table and then adds the query data to the table.
	 */
	public void addQueryDataToTable(ResultSet rs) throws SQLException {
		tv.getColumns().clear();
		if(rs == null){
			return;
		}
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

	/**Get the current selected row index*/
	public int getSelectedRowIndex(){
		return this.tv.getSelectionModel().getSelectedIndex();
	}

	/**Get the current selected row data*/
	public ObservableList getSelectedRowData(){
		return this.tv.getSelectionModel().getSelectedItem();
	}

	/**Removes the currently selected row.*/
	public void removeSelectedRow() {
		if(this.getSelectedRowIndex() > -1){
			updateData(DBTableEdit.EditType.DELETION, this.getSelectedRowIndex(), null, getSelectedRowData());
		}
	}

	/**Duplicates the row at index selectedRowIndex and appends it to the bottom of the table*/
	public ObservableList<String> duplicateRow(int selectedRowIndex) {
		if(this.getSelectedRowIndex() == -1){
			return null;
		}
		ObservableList old = this.tv.getItems().get(selectedRowIndex);
		ObservableList neww =FXCollections.observableArrayList(new String[old.size()]);
		FXCollections.copy(neww, old);
		return neww;
		//			updateData(DBTableEdit.EditType.ADDITION, this.tv.getItems().size(), neww, old);
	}

	/**Mark a table row's data at index as edited. When database synchronization happens, this edited data will be used to update the database.*/
	private void markAsEdited(DBTableEdit.EditType type, int index, ObservableList newData, ObservableList oldData){
		DBTableEdit edit = new DBTableEdit(type, index, newData, oldData);
		this.rowsEdited.add(edit);
	}

	@Override
	public void clearEdited(){
		this.rowsEdited.clear();
	}

	/**
	 * Creates and returns a new list of data
	 */
	public ObservableList<String> getNewRowData() {
		if (!this.hasColumns()){
			throw new IllegalStateException("Can't add an empty row when there isn't any data to add to");
		}

		String[] data = new String[this.tv.getColumns().size()];
		for (int i = 0; i < this.tv.getColumns().size(); i++){
			data[i] = "new data";
		}

		ObservableList<String> row = FXCollections.observableArrayList();
		row.addAll(data);
		return row;
//		updateData(DBTableEdit.EditType.ADDITION, this.tv.getItems().size(), row, null);
	}

	@Override
	public void updateData(DBTableEdit.EditType type, int rowIndex, ObservableList newData, ObservableList oldData) {
		if(type == DBTableEdit.EditType.ADDITION){
			this.tv.getItems().add(newData);
		}else if (type == DBTableEdit.EditType.DELETION){
			this.tv.getItems().remove(rowIndex);
		}else{
			this.tv.getItems().set(rowIndex, newData);
		}

		if(type == DBTableEdit.EditType.DELETION){
			for(int i = 0; i < this.rowsEdited.size(); i++){
				if(this.rowsEdited.get(i).rowIndex() > rowIndex){
					this.rowsEdited.get(i).decrementRowIndex(1); //since an element is getting removed from the table, the indexes need to be shifted -1 to realign their indexes
				}
			}
		}

		this.markAsEdited(type, rowIndex, newData, oldData);
	}

	/**Undo the edit made to the table*/
	public void undoLastEdit() {
		if(this.rowsEdited.size() == 0){
			return;
		}
		DBTableEdit edit = this.rowsEdited.get(this.rowsEdited.size() - 1);
		if(edit.type() == DBTableEdit.EditType.DELETION){
			this.tv.getItems().add(edit.rowIndex(), edit.oldRowData());
		}else if(edit.type() == DBTableEdit.EditType.ADDITION){
			this.tv.getItems().remove(edit.rowIndex());
		}else{
			this.tv.getItems().set(edit.rowIndex(), edit.oldRowData());
		}
		this.rowsEdited.remove(this.rowsEdited.size() - 1);
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
	public Iterator<DBTableEdit> iterator(boolean reversed) {
		return new EditedDataIterator(this, reversed);
	}

	@Override
	public String[] getColumnTypes(){
		return this.columnTypes;
	}

	@Override
	public String[] getColumnNames(){
		return this.columnNames;
	}

	/**Get the number of rows in the table*/
	public int getRowSize() {
		return this.tv.getItems().size();
	}

	private class EditedDataIterator implements Iterator<DBTableEdit>{

		private final DBTable table;
		private int cursor = 0;
		private final boolean reverse;


		public EditedDataIterator(DBTable table, boolean reverse) {
			this.table = table;
			this.reverse = reverse;
			if(reverse){
				this.cursor = this.table.rowsEdited.size() - 1;
			}
		}

		@Override
		public boolean hasNext() {
			if(reverse){
				return cursor >= 0;
			}
			return cursor < table.rowsEdited.size();
		}

		@Override
		public DBTableEdit next() {
			if(reverse){
				return this.table.rowsEdited.get(cursor--);
			}
			return this.table.rowsEdited.get(cursor++);
		}
	}

}
