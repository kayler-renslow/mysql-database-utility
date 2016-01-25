package com.kaylerrenslow.mysqlDatabaseTool.fx.db;

import javafx.collections.ObservableList;

/**
 * @author Kayler
 * Created on 01/24/2016.
 */
public class DBTableEdit{
	public enum EditType{
		DELETION, ADDITION, UPDATE
	}

	private final EditType type;
	private final ObservableList oldRowData;
	private ObservableList newRowData;
	private int rowIndex;

	private static int NEXT_ID = 0;

	private final int id = NEXT_ID++;

	public DBTableEdit(EditType type, int rowIndex, ObservableList newRowData, ObservableList oldRowData) {
		this.type = type;
		this.rowIndex = rowIndex;
		this.oldRowData = oldRowData;
		this.newRowData = newRowData;
	}

	void setNewRowData(ObservableList newRowData){
		this.newRowData = newRowData;
	}

	void decrementRowIndex(int amount) {
		this.rowIndex -= amount;
	}

	public ObservableList oldRowData(){
		return this.oldRowData;
	}

	public ObservableList newRowData() {
		return newRowData;
	}

	public EditType type() {
		return type;
	}

	public int rowIndex() {
		return rowIndex;
	}


	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof DBTableEdit)){
			return false;
		}
		DBTableEdit other = (DBTableEdit)obj;
		return this.id == other.id;
	}

	@Override
	public String toString() {
		return "DBTableEdit{" +
				"type=" + type +
				", oldRowData=" + oldRowData +
				", newRowData=" + newRowData +
				", rowIndex=" + rowIndex +
				'}';
	}
}
