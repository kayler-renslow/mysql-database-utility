package com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu;

import javafx.scene.control.MenuItem;

/**
 * @author Kayler
 *         Created on 02/13/2016.
 */
public class FXMenuItemGuts implements IFXMenuItem{
	private int insertionIndex;

	@Override
	public void setInsertionIndex(int index) {
		this.insertionIndex = index;
	}

	@Override
	public int getInsertionIndex() {
		return this.insertionIndex;
	}

	@Override
	public boolean matchesIndex(int index) {
		return this.insertionIndex == index;
	}

	@Override
	public MenuItem getMenuItem() {
		return null;
	}
}
