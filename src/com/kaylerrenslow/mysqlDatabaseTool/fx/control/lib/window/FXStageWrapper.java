package com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window;

import javafx.stage.Stage;

/**
 * Created by Kayler on 01/21/2016.
 */
public class FXStageWrapper{
	private Stage stage;
	private IFXWindow window;

	public FXStageWrapper(Stage s, IFXWindow window) {
		this.stage = s;
		this.window = window;
	}

	public Stage getStage(){
		return this.stage;
	}

	public IFXWindow getWindow(){
		return this.window;
	}
}
