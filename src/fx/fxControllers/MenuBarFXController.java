package fx.fxControllers;

import fx.fxControls.MainMenu_Data;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

/**
 * Created by kayler on 12/7/15.
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
