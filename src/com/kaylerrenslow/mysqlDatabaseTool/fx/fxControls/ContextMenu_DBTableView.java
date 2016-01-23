package com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls;

import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.fxMenu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.fxMenu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.fxControls.lib.fxMenu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;

/**
 * @author Kayler
 * Created on 12/15/15.
 */
public class ContextMenu_DBTableView extends javafx.scene.control.ContextMenu implements IFXMenuEventHandle {

    private FXMenuItem edit = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_EDIT);
    private TableView<ObservableList> tv;

    public ContextMenu_DBTableView(TableView tv){
        FXMenuUtil.addItems(this, this, edit);
        this.tv = tv;
    }

    @Override
    public void handle(int index, ActionEvent event) {
        if(index == edit.getInsertionIndex()){
//            System.out.println(this.tv.getSelectionModel().getSelectedItem().get(0));
            System.out.println("contextMenu db table view: edit clicked");
            WebsiteDatabaseTool.createNewWindow(new DBDataEditorWindow(tv.getSelectionModel().getSelectedItem()));
        }
    }
}
