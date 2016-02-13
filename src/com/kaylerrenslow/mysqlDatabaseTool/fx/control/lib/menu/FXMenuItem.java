package com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu;

import javafx.scene.control.MenuItem;

/**
 * @author Kayler
 * Wrapper class for a normal JavaFX MenuItem. This class contains one additional method, getInsertionIndex(), which returns the index at which it was added from FXMenuUtil
 * Created on 12/7/15
 */
public class FXMenuItem extends MenuItem implements IFXMenuItem<MenuItem> {
    private final FXMenuItemGuts guts = new FXMenuItemGuts();
    public FXMenuItem(String text) {
        super(text);
    }

	@Override
    public void setInsertionIndex(int index){
        this.guts.setInsertionIndex(index);
    }

	@Override
    public int getInsertionIndex(){
        return this.guts.getInsertionIndex();
    }

	@Override
	public boolean matchesIndex(int index){
		return this.guts.matchesIndex(index);
	}

	@Override
	public MenuItem getMenuItem() {
		return this;
	}

}
