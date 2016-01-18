package fx.fxActionEvent;

import fx.fxControllers.DatabaseFXController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import main.Lang;
import main.Main;

import java.io.File;

/**
 * @author Kayler
 * This class is used whenever the locate connection properties button is pressed. It creates the file chooser and then updates the DatabaseFXController's connection properties file location when a file is picked.
 * Created on 11/20/15.
 */
public class LocatePropFileAction implements EventHandler<ActionEvent> {
    private DatabaseFXController dc;
    private final FileChooser chooser = new FileChooser();

    private static final String DOT_PROPERTIES = "*.properties";
    private static final String ALL_DESCRIPTION = "All Files";
    private static final String ALL = "*";

    public LocatePropFileAction(DatabaseFXController dc) {
        this.dc = dc;
        chooser.setTitle(Lang.CHOOSER_TITLE);
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(DOT_PROPERTIES, DOT_PROPERTIES));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(ALL_DESCRIPTION, ALL));
    }

    @Override
    public void handle(ActionEvent event) {
        File selFile = chooser.showOpenDialog(Main.stage);
        if(selFile == null){
            return;
        }
        this.dc.setPropertiesFileLocation(selFile);
    }
}
