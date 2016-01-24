package com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu;

import javafx.event.ActionEvent;

/**
 * @author Kayler
 * Interface that is used as an event handler for when an FXMenuItem that was added in a menu from FXMenuUtil
 * Created on 12/11/15.
 */
public interface IFXMenuEventHandle {


    /**This is called whenever a menu item was clicked. The index corresponds to which item was clicked.
     * Index is determined by the order of menu items passed in the constructor.
     * @param index index of the menu item that was clicked (starts at 0)
     * @param event ActionEvent that was created when the menu item at the index was clicked
     * */
    void handle(int index, ActionEvent event);

}
