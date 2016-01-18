package main;

import database.DatabaseConnection;

/**
 * Created by kayler on 10/30/15.
 */
public class Program {

    /*Initial window width*/
    public static final int WINDOW_WIDTH = 720;

    /*Initial window height*/
    public static final int WINDOW_HEIGHT = 480;

    /*Controllers object that contains all the fx.fxControllers.*/
    public static Controllers mainWindow;

    /*There is only one DatabaseConnection object at all times for the program and it is this one.*/
    public static DatabaseConnection dbConnection = new DatabaseConnection();

    public static void loadMainWindow(InitController cont){
        mainWindow = new Controllers(cont);
    }
}
