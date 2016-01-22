package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.window.IFXWindow;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Created by Kayler on 01/21/2016.
 */
public class DBDataEditorWindow extends VBox implements IFXWindow{

	public DBDataEditorWindow() {
		initialize();
	}

	private void initialize() {

	}

	@Override
	public Region getRoot() {
		return this;
	}

	@Override
	public int getInitWidth() {
		return 640;
	}

	@Override
	public int getInitHeight() {
		return 320;
	}
}
