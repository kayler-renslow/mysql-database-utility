package com.kaylerrenslow.mysqlDatabaseTool.main;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.FXStageWrapper;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.IFXWindow;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Kayler
 * Where execution of the program begins. Creates the JavaFX Stage by loading it from the .fxml*/
public class MySQLDatabaseUtility extends Application implements EventHandler<WindowEvent>{

    private static VBox root = null;
    private static Scene scene;
    public static Stage stage;
	private static final String ICON_PATH = "/com/kaylerrenslow/mysqlDatabaseTool/resources/website_database_tool_icon.png";

    private static ArrayList<FXStageWrapper> subWindows = new ArrayList<>();
	private static Image ICON;

	public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        MySQLDatabaseUtility.stage = stage;
        root = FXMLLoader.load(getClass().getResource("/com/kaylerrenslow/mysqlDatabaseTool/resources/mainWindow.fxml"));
        scene = new Scene(root, Program.WINDOW_WIDTH, Program.WINDOW_HEIGHT);
        MySQLDatabaseUtility.stage.setTitle(Lang.PROGRAM_WINDOW_TITLE);
        MySQLDatabaseUtility.stage.setScene(scene);
        MySQLDatabaseUtility.stage.show();
		stage.setOnCloseRequest(this);

		ICON = new Image(getClass().getResourceAsStream(ICON_PATH));
		stage.getIcons().add(ICON);
    }


    public static void createNewWindow(IFXWindow window){
        Stage stage = new Stage();
        stage.setScene(new Scene(window.getRoot()));
        window.getRoot().setPrefWidth(window.getInitWidth());
		window.getRoot().setPrefHeight(window.getInitHeight());
        subWindows.add(new FXStageWrapper(stage, window));
        stage.setTitle(window.getTitle());
        stage.show();
		stage.getIcons().add(ICON);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
			@Override
			public void handle(WindowEvent event) {
				window.closing();
			}
		});
    }

	public static void showErrorWindow(String title, String errorMsg){
		JOptionPane.showMessageDialog(null, errorMsg, title, JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void handle(WindowEvent event) {
		if(event.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)){
			Program.DATABASE_CONNECTION.disconnect();
			for(int i = 0; i < MySQLDatabaseUtility.subWindows.size(); i++){
				if(MySQLDatabaseUtility.subWindows.get(i) != null){
					MySQLDatabaseUtility.subWindows.get(i).getStage().close();
				}
			}
		}
	}

	/**Closes the window.
	 * @return true if the window was closed, false if it wasn't.*/
	public static boolean closeWindow(IFXWindow window) {
		for(FXStageWrapper s : subWindows){
			if(s.getWindow() == window){
				window.closing();
				s.getStage().close();
				return true;
			}
		}
		return false;
	}
}
