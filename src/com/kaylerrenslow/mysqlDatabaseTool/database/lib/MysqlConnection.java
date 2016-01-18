package com.kaylerrenslow.mysqlDatabaseTool.database.lib;

import com.kaylerrenslow.mysqlDatabaseTool.database.ConnectionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Kayler
 * Library code for running a MySQL database connection. All connection properties (username, password, etc) are located in a .properties file. This library uses JDBC.
 * */
public class MysqlConnection{
	private String server;
	private String database;

	private Properties connectionProps = new Properties();
	private boolean connectionPropertiesSet = false;
	private Connection conn;
	private PrintStream stream;

	private static final String SUCCESS = "Connection successfully opened.";

	/** Creates a mysql connection object. Use loadConnectionProperties() to set the connection values,
	 *   connect() and disconnect() to make the connection or to disconnect, and finally query(String) to run a query.
	 * @param stream PrintSteam to print feedback to. Can be null. (System.out may be used)
	 * @throws  IllegalArgumentException when the properties file is improperly configured
	 */
	public MysqlConnection(PrintStream stream) throws IllegalArgumentException {
		this.stream = stream;
	}

	/**
	 * Set's up properties to connect to a MySQL database.
	 * <br />The .properties file <b>must</b> include the following:
	 * <ul>
	 *     <li>user (username)</li>
	 *     <li>password (password)</li>
	 *     <li>server (URL/IP to the server (can be localhost))</li>
	 *     <li>database (database name)</li>
	 * <ul/>
	 * @param pathToProperties file that contains the path to the connection properties
	 * @throws IOException when pathToProperties doesn't exist or can't be loaded.
	 * @throws IllegalArgumentException when a property that is required isn't set
	 * */
	public void loadConnectionProperties(File pathToProperties) throws IOException{
		FileInputStream fis = new FileInputStream(pathToProperties);
		connectionProps.load(fis);

		String r = connectionProps.getProperty("user");
		if(r == null){
			throw new IllegalArgumentException("user not defined in properties file");
		}

		r = connectionProps.getProperty("password");
		if(r == null){
			throw new IllegalArgumentException("password not defined in properties file");
		}

		r = connectionProps.getProperty("server");
		if(r == null){
			throw new IllegalArgumentException("server not defined in properties file");
		}
		this.server = r;

		r = connectionProps.getProperty("com/kaylerrenslow/mysqlDatabaseTool/database");
		if(r == null){
			throw new IllegalArgumentException("database not defined in properties file");
		}
		this.database = r;

		this.connectionPropertiesSet = true;
	}


	/** Attempts to connect to the MySQL database. */
	public void connect(){
		if(conn != null){
			printToStream(SUCCESS);
			return;
		}
		try{
			conn = createConnection();
		}catch(Exception e){
			printToStream("An error occurred connecting to database.");
			printStackTrace(e);
			return;
		}
		printToStream(SUCCESS);
		return;
	}

	/**Returns true if the connection is established, false otherwise*/
	public boolean isConnected(){
		return this.conn != null;
	}


	/** Attempts to disconnect to the MySQL database.
	 * @throws ConnectionException if the disconnect failed
	 */
	public void disconnect(){
		String success = "Connection successfully closed.";
		if(conn == null){
			printToStream(success);
			return;
		}
		try{
			this.conn.close();
		}catch(Exception e){
			printToStream("An error occurred disconnecting from database:" + this.database);
			printStackTrace(e);
			return;
		}
		this.conn = null;
		printToStream(success);
		return;
	}

	/** Runs a MySQL query.
	 * @param sql sql String to execute
	 * @return ResultSet containing the results. Returns null if the query failed.
	 * @throws IllegalArgumentException when a connection has not been set (should run connect())
	 * @throws QueryFailedException when the query failed. The reason for the fail will be inside the exception's message
	 */
	public ResultSet query(String sql) throws QueryFailedException{
		if(this.conn == null){
			throw new IllegalStateException("Can not run a query without connecting.");
		}
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			printToStream("Query was successful");
			return rs;
		} catch (SQLException e) {
			printToStream("Query '" + sql + "' failed.");
			printStackTrace(e);
			throw new QueryFailedException(e.getMessage());
		}
	}

    /**TODO for outside implementation: getColumns(null,null,tablename,null)*/
	public DatabaseMetaData getDBMetadata() throws SQLException{
		if(!this.isConnected()){
			throw new IllegalStateException("Can't retrieve database metadata when a connection isn't set.");
		}

		DatabaseMetaData dmd = this.conn.getMetaData();
        /*getColumns(null,null,tablename,null)*/
		return dmd;
	}

	private Connection createConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://" + this.server + "/" + this.database, connectionProps);
		printToStream("Connected to database " + this.database + " on server " + this.server);
		return conn;
	}

	private void printToStream(String msg){
		if(stream != null){
			stream.println(msg);
		}
	}

	private void printStackTrace(Exception e){
		if(this.stream != null){
			e.printStackTrace(this.stream);
		}
	}

	/**Returns true if the connection has connection properties file that appears to be valid, false otherwise*/
	public boolean connectionPropertiesSet() {
		return this.connectionPropertiesSet;
	}
}
