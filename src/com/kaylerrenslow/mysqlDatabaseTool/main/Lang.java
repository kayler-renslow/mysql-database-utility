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
    public static final String CONN_STATUS_NO_PROPERTIES = "no properties";

    /**Connection status text for when a query is run but the connection has not been set*/
    public static final String CONN_STATUS_NOT_CONNECTED = "no connection set";


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
    public static final String MENUB_DATA_TITLE = "Data";
    public static final String MENUB_DATA_NEW_ENTRY = "New Entry";

    public static final String MENUB_DATA_SYNC_DATA = "Sync data to Database";
    /*End constants for menu bar strings*/

    /*Begin constants for context menu strings*/

    public static final String CONTEXT_MENU_DBTV_EDIT = "Edit";

    /*End constants for context menu strings*/
}
