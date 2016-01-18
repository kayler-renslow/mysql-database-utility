package main;

import database.DatabaseConnection;

/**
 * @author Kayler
 * This class holds all program information that may be needed anywhere else in the program.
 * Created on 10/30/15.
 */
public class Program {

    /**Initial main window width*/
    public static final int WINDOW_WIDTH = 720;

    /**Initial main window height*/
    public static final int WINDOW_HEIGHT = 480;

    /**Controllers object that contains all the fx.fxControllers.*/
    public static Controllers controllers;

    /**There is only one DatabaseConnection object at all times for the program and it is this one.*/
    public static final DatabaseConnection DATABASE_CONNECTION = new DatabaseConnection();

    public static void loadMainWindow(InitController cont){
        controllers = new Controllers(cont);
    }
}
