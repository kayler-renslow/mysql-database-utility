package com.kaylerrenslow.mysqlDatabaseTool.main;

/**
 * @author Kayler
 * Created on 11/20/15.
 */
public class Lang {

    /**Title for properties file FileChooser*/
    public static final String CHOOSER_TITLE = "Locate Properties File";


    /**Connection status text for disconnected*/
    public static final String CONN_STATUS_DISCONNECTED = "disconnected";

    /**Connection status text for connecting*/
    public static final String CONN_STATUS_CONNECTING = "connecting";

    /**Connection status text for connected*/
    public static final String CONN_STATUS_CONNECTED = "connected";

    /**Connection status text for connection failed/error*/
    public static final String CONN_STATUS_CONN_ERROR = "connection failed";

    /**Connection status text for bad connection properties file*/
    public static final String CONN_STATUS_BAD_PROP = "bad prop file";

    /**Connection status text for when no properties file is selected*/
    public static final String CONN_STATUS_NO_PROPERTIES = "no properties set";
	public static final String CONN_STATUS_NO_PROPERTIES_LONG = "Properties file has not been located";

    /**Connection status text for when a query is run but the connection has not been set*/
    public static final String CONN_STATUS_NOT_CONNECTED = "no connection set";
	public static final String CONN_STATUS_NOT_CONNECTED_LONG = "Not connected to a database";


	/**Connection status text for when query is starting*/
    public static final String CONN_STATUS_BEGIN_QUERY = "querying database";
	public static final String CONN_STATUS_BEGIN_QUERY_LONG = "Querying database with SQL=";

	/**Connection status text for when query is complete*/
	public static final String CONN_STATUS_END_QUERY = "query complete";

	/**Connection status text for when query had an error*/
	public static final String CONN_STATUS_QUERY_ERROR = "query error";
	public static final String CONN_STATUS_QUERY_ERROR_LONG = "Query failed";

	public static final String NOTIF_TITLE_QUERY_FAILED = "Query failed";

    public static final String NOTIF_BODY_NOT_CONNECTED = "Not connected to a server.";

    /**String that appears in the tableview when not connected to database and the user wanted to add a new entry to data in tableview*/
    public static final String NOTIF_TITLE_NEW_ENTRY_ERROR = "Can't add empty row";
    public static final String NOTIF_BODY_NO_COLUMNS = "No columns to add data to. Try running a query first.";

    /**Connection status prefix text*/
    public static final String DB_STATUS_PREFIX = "Status: ";

    /**Main window's title*/
    public static final String PROGRAM_WINDOW_TITLE = "Website Database Tool";

    /*Begin constants for menu bar strings*/
    public static final String MENUB_TITLE_VIEW = "View";
	public static final String MENUB_ITEM_ADD_NEW_TABLE = "Add New Table";
	public static final String MENUB_ITEM_NO_TABLES = "No Tables to Show";
	/*End constants for menu bar strings*/

    /*Begin constants for context menu strings*/
	public static final String CONTEXT_MENU_DBTV_EDIT = "Edit";
	public static final String CONTEXT_MENU_DBTV_REMOVE_ROW = "Remove Row";
	public static final String CONTEXT_MENU_DBTV_DUPLICATE = "Duplicate";
	public static final String CONTEXT_MENU_DBTV_DATA_NEW_ENTRY = "New Entry";
	public static final String CONTEXT_MENU_DBTV_VIEW_EDITS = "View Edits";
	public static final String CONTEXT_MENU_DBTV_REFRESH = "Refresh";
	public static final String CONTEXT_MENU_DBTV_SYNC_DATA = "Synchronize to Database";
	/*End constants for context menu strings*/

	public static final String CONTEXT_MENU_TAB_PANE_CLOSE = "Close Tab";

	public static final String DB_COL_EDITOR_MSG_NOT_SUPPORTED = "Editor '%s' incompatible with data type.";

	public static final String DB_EDITOR_MENU_TITLE = "Data";
	public static final String DB_EDITOR_MENU_DATA_SAVE = "Save";

	public static final String DB_EDITOR_CONTROL_HTML_EDITOR_TOGGLE_BUTTON = "Show HTML";

	public static final String TAB_PANE_TITLE_QUERY_RESULT = "Query Result";

	public static final String WINDOW_ALL_TABLES = "All SQL tables";
	public static final String WINDOW_VIEW_EDITS_PREFIX = "All Edits Made to ";
	public static final String WINDOW_VIEW_EDITS_NO_EDITS = "No changes have been made.";

	public static final String WINDOW_CONFIRM_SYNC_TITLE = "Confirm Synchronization Changes";
	public static final String WINDOW_CONFIRM_SYNC_MESSAGE = "Are you sure you want to synchronize '%s' to the server's database?";
}
