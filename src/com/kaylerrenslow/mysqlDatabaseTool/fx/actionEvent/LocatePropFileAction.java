package com.kaylerrenslow.mysqlDatabaseTool.fx.actionEvent;

import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.DatabaseFXController;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.MySQLDatabaseUtility;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * @author Kayler
 *         This class is used whenever the locate connection properties button is pressed. It creates the file chooser and then updates the DatabaseFXController's connection properties file location when a file is picked.
 *         Created on 11/20/15.
 */
public class LocatePropFileAction implements EventHandler<ActionEvent>{
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
		File selFile = chooser.showOpenDialog(MySQLDatabaseUtility.stage);
		if (selFile == null){
			return;
		}
		this.dc.setPropertiesFileLocation(selFile);
		attemptSetConnectionPropertiesFile(selFile);
	}

	/**
	 * Attempts to set the connection properties file without opening a file chooser. Returns true if the file is valid/exists, false if it isn't
	 */
	public static boolean attemptSetConnectionPropertiesFile(String file) {
		File f = new File(file);
		if (f.exists()){
			attemptSetConnectionPropertiesFile(f);
			return true;
		}
		return false;
	}

	private static void attemptSetConnectionPropertiesFile(File f) {
		Program.DATABASE_CONNECTION.setConnectionPropertiesFile(f);
	}
}
