package com.kaylerrenslow.mysqlDatabaseTool.main;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.FXStageWrapper;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window.IFXWindow;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;

/**
 * @author Kayler
 * Where execution of the program begins. Creates the JavaFX Stage by loading it from the .fxml*/
public class WebsiteDatabaseTool extends Application implements EventHandler<WindowEvent>{

    private static VBox root = null;
    private static Scene scene;
    public static Stage stage;

    private static ArrayList<FXStageWrapper> subWindows = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        WebsiteDatabaseTool.stage = stage;
        root = FXMLLoader.load(getClass().getResource("/com/kaylerrenslow/mysqlDatabaseTool/main/mainWindow.fxml"));
        scene = new Scene(root, Program.WINDOW_WIDTH, Program.WINDOW_HEIGHT);
        WebsiteDatabaseTool.stage.setTitle(Lang.PROGRAM_WINDOW_TITLE);
        WebsiteDatabaseTool.stage.setScene(scene);
        WebsiteDatabaseTool.stage.show();
		stage.setOnCloseRequest(this);
    }


    public static void createNewWindow(IFXWindow window){
        Stage stage = new Stage();
        stage.setScene(new Scene(window.getRoot()));
        stage.setWidth(window.getInitWidth());
        stage.setHeight(window.getInitHeight());
        subWindows.add(new FXStageWrapper(stage, window));
        stage.setTitle(window.getTitle());
        stage.show();
    }

	@Override
	public void handle(WindowEvent event) {
		if(event.getEventType().equals(WindowEvent.WINDOW_CLOSE_REQUEST)){
			Program.DATABASE_CONNECTION.disconnect();
			for(int i = 0; i < WebsiteDatabaseTool.subWindows.size(); i++){
				if(WebsiteDatabaseTool.subWindows.get(i) != null){
					WebsiteDatabaseTool.subWindows.get(i).getStage().close();
				}
			}
		}
	}
}
