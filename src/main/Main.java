package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private static VBox root = null;
    private static Scene scene;
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        root = FXMLLoader.load(getClass().getResource("/main/mainWindow.fxml"));
        scene = new Scene(root, Program.WINDOW_WIDTH, Program.WINDOW_HEIGHT);
        Main.stage.setTitle(Lang.PROGRAM_WINDOW_TITLE);
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    public static Stage getRootStage(){
        return stage;
    }

}
