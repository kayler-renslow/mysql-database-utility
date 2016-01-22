package com.kaylerrenslow.mysqlDatabaseTool.main;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.window.FXStageWrapper;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.window.IFXWindow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author Kayler
 * Where execution of the program begins. Creates the JavaFX Stage by loading it from the .fxml*/
public class Main extends Application {

    private static VBox root = null;
    private static Scene scene;
    public static Stage stage;

    private ArrayList<FXStageWrapper> subWindows = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        root = FXMLLoader.load(getClass().getResource("/com/kaylerrenslow/mysqlDatabaseTool/main/mainWindow.fxml"));
        scene = new Scene(root, Program.WINDOW_WIDTH, Program.WINDOW_HEIGHT);
        Main.stage.setTitle(Lang.PROGRAM_WINDOW_TITLE);
        Main.stage.setScene(scene);
        Main.stage.show();
    }


    public void createNewWindow(IFXWindow window){
        Stage stage = new Stage();
        stage.setScene(new Scene(window.getRoot()));
        stage.setWidth(window.getInitWidth());
        stage.setHeight(window.getInitHeight());
        subWindows.add(new FXStageWrapper(stage, window));
        stage.show();
    }

}
