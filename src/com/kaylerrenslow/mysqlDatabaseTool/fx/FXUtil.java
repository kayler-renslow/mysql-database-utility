package com.kaylerrenslow.mysqlDatabaseTool.fx;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;

/**
 * @author Kayler
 * Created on 12/2/15.
 */
public class FXUtil {

    /**Adds a context menu item to the control's context menu.
     * If the control doesn't have a context menu, one will be created.
     *
     * @param c control to add a context menu item to
     * @param text text that goes inside the context menu
     *
     * @return MenuItem that was added into the context menu*/
    public static MenuItem addContextItem(Control c, String text){
        MenuItem mi = new MenuItem(text);
        if(c.getContextMenu() == null){
            ContextMenu cm = new ContextMenu(mi);
            return mi;
        }
        c.getContextMenu().getItems().add(mi);
        return mi;
    }

    /**Creates an empty context menu for the given Control c and then returns it.*/
    public static ContextMenu setEmptyContextMenu(Control c){
        ContextMenu cm = new ContextMenu();
        c.setContextMenu(cm);
        return cm;
    }
}
