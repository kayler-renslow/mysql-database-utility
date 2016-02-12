package com.kaylerrenslow.mysqlDatabaseTool.database.lib;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This class is a semi-wrapper class for JDBC ResultSet class. It is meant to strip out the information relevant from the JDBC ResultSet and store it.
 * Information stored is column names, column types (String), and String arrays of row data. This class also features an Iterator for iterating over the row data.
 *
 * @author Kayler
 *         Created on 02/11/2016.
 */
public class MysqlQueryResult{
	private String[] colNames, colTypes;
	private ResultRow[] rows;

	/**
	 * Construct a result from JDBC result set
	 */
	public MysqlQueryResult(ResultSet rs) throws SQLException {
		load(rs);
	}

	/**
	 * Sets the column names, row data, and column types from JDBC ResultSet
	 */
	private void load(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		this.colNames = new String[rsmd.getColumnCount()];
		this.colTypes = new String[rsmd.getColumnCount()];

		for (int col = 1; col <= rsmd.getColumnCount(); col++){
			this.colNames[col - 1] = rsmd.getColumnName(col);
			this.colTypes[col - 1] = SQLTypes.convertToString(rsmd.getColumnType(col));
		}

		String[] data;
		int rowCount = 0;
		while (rs.next()){
			rowCount++;
		}
		this.rows = new ResultRow[rowCount];
		rs.absolute(0);
		int rowInd = 0;
		while (rs.next()){
			data = new String[rsmd.getColumnCount()];
			for (int col = 1; col <= rsmd.getColumnCount(); col++){
				data[col - 1] = rs.getString(col);
			}
			this.rows[rowInd] = new ResultRow(data);
			rowInd++;
		}
	}

	/**
	 * Create a new MysqlQueryResult from String arrays full of data
	 *
	 * @param colNames column names
	 * @param rowVals  arrays of data. Each array is one row
	 */
	public MysqlQueryResult(String[] colNames, String[]... rowVals) {
		this.colNames = colNames;
		this.rows = new ResultRow[rowVals.length];
		for (int i = 0; i < rowVals.length; i++){
			rows[i] = new ResultRow(rowVals[i]);
		}
	}

	/**
	 * Get a String[] with somewhat user-friendly readability
	 */
	public String[] getColumnTypes() {
		return this.colTypes;
	}

	/**
	 * Get a String[] with all of the column names
	 */
	public String[] getColNames() {
		return this.colNames;
	}

	/**
	 * Get an iterator to iterate over all of the rows.
	 */
	public Iterator<String[]> rowIterator() {
		return new ResultRowIterator(this);
	}

	/**
	 * Number of columns
	 */
	public int getNumColumns() {
		return this.colNames.length;
	}

	/**
	 * Number of rows
	 */
	public int getNumRows() {
		return this.rows.length;
	}

	private class ResultRow{
		final String[] values;

		public ResultRow(String[] values) {
			this.values = values;
		}

		@Override
		public String toString() {
			return "ResultRow{" +
					"values=" + Arrays.toString(values) +
					'}';
		}
	}

	private class ResultRowIterator implements Iterator<String[]>{
		private final MysqlQueryResult mqr;
		private int cursor = 0;

		public ResultRowIterator(MysqlQueryResult mqr) {
			this.mqr = mqr;
		}

		@Override
		public boolean hasNext() {
			return cursor < mqr.getNumRows();
		}

		@Override
		public String[] next() {
			return mqr.rows[cursor++].values;
		}
	}
}
