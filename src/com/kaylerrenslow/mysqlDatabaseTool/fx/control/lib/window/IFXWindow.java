package com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.window;

import javafx.scene.layout.Region;

/**
 * @author Kayler
 * Created on 01/21/2016.
 */
public interface IFXWindow{
	/**Get the root node for the javafx scene/window*/
	Region getRoot();

	/**Get the initial width of the window*/
	int getInitWidth();

	/**Get the initial height of the window*/
	int getInitHeight();

	/**Get the title of the window*/
	String getTitle();
}
