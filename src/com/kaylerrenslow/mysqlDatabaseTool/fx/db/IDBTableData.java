package com.kaylerrenslow.mysqlDatabaseTool.fx.db;

import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Kayler
 * Created on 01/24/2016.
 */
public interface IDBTableData{
	/**Fetch the column SQL types used in the columns*/
	String[] getColumnNames();

	/**Fetch the column names*/
	String[] getColumnTypes();

	/**Updates the data in the table at rowIndex with the new data*/
	void updateData(DBTableEdit.EditType type, int rowIndex, ObservableList newData, ObservableList oldData);

	/**Get one row of data*/
	ObservableList getData(int rowIndex);

	/**Checks if the table has data inside it
	 * @return true if there is data, false otherwise
	 * */
	boolean hasColumns();

	/**Gets a list of the edited rows.*/
	List<DBTableEdit> getEditedData();

	/**Marks all the edited rows as unedited*/
	void clearEdited();
}
