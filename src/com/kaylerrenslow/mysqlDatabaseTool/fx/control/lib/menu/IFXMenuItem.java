package com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu;

/**
 * @author Kayler
 *         Created on 02/13/2016.
 */
public interface IFXMenuItem<E>{
	void setInsertionIndex(int index);

	/**
	 * Returns the index at which it was added in an FXMenu
	 */
	int getInsertionIndex();

	/**
	 * Returns true if index is the same as this instance's insertion index
	 */
	boolean matchesIndex(int index);

	/**Return the menu item*/
	E getMenuItem();
}
