package fx.fxControls;

import fx.fxControllers.DatabaseFXController;
import fx.fxControls.lib.fxMenu.FXMenu;
import fx.fxControls.lib.fxMenu.FXMenuItem;
import fx.fxControls.lib.fxMenu.IFXMenuEventHandle;
import javafx.event.ActionEvent;
import main.Lang;

/**
 * Created by kayler on 12/7/15.
 */
public class MainMenu_Data extends javafx.scene.control.Menu implements IFXMenuEventHandle {

    private DatabaseFXController dc;
    private FXMenuItem menuNewEntry = new FXMenuItem(Lang.MENUB_DATA_NEW_ENTRY);
    private FXMenuItem menuSyncData = new FXMenuItem(Lang.MENUB_DATA_SYNC_DATA);

    public MainMenu_Data(DatabaseFXController dc) {
        super(Lang.MENUB_DATA_TITLE);
        FXMenu.addItems(this, this, menuNewEntry, menuSyncData);
        this.dc = dc;
    }

    @Override
    public void handle(int index, ActionEvent event) {
        if(index == menuNewEntry.getInsertionIndex()){
            dc.createNewEntry();
        }else if(index == menuSyncData.getInsertionIndex()){
            System.out.println("MainMenu_Data>> sync data pressed");
        }else{
            return;
        }
    }
}
