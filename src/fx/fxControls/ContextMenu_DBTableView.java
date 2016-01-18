package fx.fxControls;

import fx.fxControls.lib.fxMenu.FXMenu;
import fx.fxControls.lib.fxMenu.FXMenuItem;
import fx.fxControls.lib.fxMenu.IFXMenuEventHandle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import main.Lang;/**/

/**
 * Created by kayler on 12/15/15.
 */
public class ContextMenu_DBTableView extends javafx.scene.control.ContextMenu implements IFXMenuEventHandle {

    private FXMenuItem edit = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_EDIT);
    private TableView<ObservableList> tv;

    public ContextMenu_DBTableView(TableView tv){
        FXMenu.addItems(this, this, edit);
        this.tv = tv;
    }

    @Override
    public void handle(int index, ActionEvent event) {
        if(index == edit.getInsertionIndex()){
            System.out.println(this.tv.getSelectionModel().getSelectedItem().get(0));
            System.out.println("contextMenu db table view: edit clicked");
        }
    }
}
