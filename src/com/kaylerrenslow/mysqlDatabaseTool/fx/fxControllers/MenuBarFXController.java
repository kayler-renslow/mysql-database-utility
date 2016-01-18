package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControllers;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.MainMenu_Data;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/**
 * @author Kayler
 * Controller class that handles all creation and insertion of menus in the main menu bar
 * Created on 12/7/15.
 */
public class MenuBarFXController {
    private MenuBar mb;
    private DatabaseFXController dc;
    private Menu[] menus;

    public MenuBarFXController(MenuBar mb, DatabaseFXController dc) {
        this.dc = dc;
        this.mb = mb;
        menus = new Menu[]{new MainMenu_Data(dc)};
        this.mb.getMenus().addAll(menus);

    }
}
