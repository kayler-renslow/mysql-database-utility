package com.kaylerrenslow.mysqlDatabaseTool.fx.contextMenu;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.db.editorWindow.DBDataEditorWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTable;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.event.ActionEvent;

/**
 * @author Kayler
 * This class provides a context menu for the database table data
 * Created on 12/15/15.
 */
public class CM_DBTableView extends javafx.scene.control.ContextMenu implements IFXMenuEventHandle {

    private FXMenuItem edit = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_EDIT);
	private FXMenuItem duplicate = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_DUPLICATE);
	private FXMenuItem removeRow = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_REMOVE_ROW);


    private final DBTable dbtv;

    public CM_DBTableView(DBTable dbtv){
        FXMenuUtil.addItems(this, this, edit, duplicate, removeRow);
        this.dbtv = dbtv;
    }



    @Override
    public void handle(int index, ActionEvent event) {
        if(edit.matchesIndex(index)){
			if(this.dbtv.getSelectedRowData() == null){
				return;
			}
            WebsiteDatabaseTool.createNewWindow(new DBDataEditorWindow(this.dbtv, this.dbtv.getSelectedRowIndex(), this.dbtv.getSelectedRowData()));
        }else if(removeRow.matchesIndex(index)){
			this.dbtv.removeSelectedRow();
		}else if(duplicate.matchesIndex(index)){
			this.dbtv.duplicateRow(this.dbtv.getSelectedRowIndex());
		}
    }

}