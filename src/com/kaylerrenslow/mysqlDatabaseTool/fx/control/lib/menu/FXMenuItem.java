package com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu;

import javafx.scene.control.MenuItem;

/**
 * @author Kayler
 * Wrapper class for a normal JavaFX MenuItem. This class contains one additional method, getInsertionIndex(), which returns the index at which it was added from FXMenuUtil
 * Created on 12/7/15
 */
public class FXMenuItem extends MenuItem {
    private int insertionIndex;
    public FXMenuItem(String text) {
        super(text);
    }

    void setInsertionIndex(int index){
        this.insertionIndex = index;
    }

	/**Returns the index at which it was added in an FXMenu*/
    public int getInsertionIndex(){
        return this.insertionIndex;
    }

}
