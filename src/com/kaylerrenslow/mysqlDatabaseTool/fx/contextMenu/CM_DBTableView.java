package com.kaylerrenslow.mysqlDatabaseTool.fx.contextMenu;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.DBTable;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editorWindow.DBDataEditorWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.event.ActionEvent;

/**
 * @author Kayler
 * Created on 12/15/15.
 */
public class CM_DBTableView extends javafx.scene.control.ContextMenu implements IFXMenuEventHandle {

    private FXMenuItem edit = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_EDIT);
    private final DBTable dbtv;

    public CM_DBTableView(DBTable dbtv){
        FXMenuUtil.addItems(this, this, edit);
        this.dbtv = dbtv;
    }

    @Override
    public void handle(int index, ActionEvent event) {
        if(index == edit.getInsertionIndex()){
			if(this.dbtv.tv.getSelectionModel().getSelectedItem() == null){
				return;
			}
            WebsiteDatabaseTool.createNewWindow(new DBDataEditorWindow(this.dbtv, this.dbtv.tv.getSelectionModel().getSelectedIndex(), this.dbtv.tv.getSelectionModel().getSelectedItem()));
        }
    }

}