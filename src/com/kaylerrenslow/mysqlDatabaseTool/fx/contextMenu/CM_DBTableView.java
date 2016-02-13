package com.kaylerrenslow.mysqlDatabaseTool.fx.contextMenu;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.fx.controllers.QueryFXController;
import com.kaylerrenslow.mysqlDatabaseTool.fx.db.DBTable;
import com.kaylerrenslow.mysqlDatabaseTool.fx.window.DBDataEditorWindow;
import com.kaylerrenslow.mysqlDatabaseTool.fx.window.ViewEditsWindow;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.Program;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.event.ActionEvent;

/**
 * @author Kayler
 * This class provides a context menu for the database table data
 * Created on 12/15/15.
 */
public class CM_DBTableView extends javafx.scene.control.ContextMenu implements IFXMenuEventHandle {

	private final QueryFXController qc;
	private FXMenuItem edit = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_EDIT);
	private FXMenuItem duplicate = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_DUPLICATE);
	private FXMenuItem removeRow = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_REMOVE_ROW);
	private FXMenuItem newEntry = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_DATA_NEW_ENTRY);
	private FXMenuItem sync = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_SYNC_DATA);
	private FXMenuItem viewEdits = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_VIEW_EDITS);

    private final DBTable dbTable;

    public CM_DBTableView(DBTable dbTable, QueryFXController qc){
		this.qc = qc;
        FXMenuUtil.addItems(this, this, edit, duplicate, newEntry, sync, viewEdits, removeRow);
        this.dbTable = dbTable;
    }

    @Override
    public void handle(int index, ActionEvent event) {
        if(edit.matchesIndex(index)){
			if(this.dbTable.getSelectedRowData() == null){
				return;
			}
            WebsiteDatabaseTool.createNewWindow(new DBDataEditorWindow(this.dbTable, this.dbTable.getSelectedRowIndex(), this.dbTable.getSelectedRowData()));
        }else if(duplicate.matchesIndex(index)){
			WebsiteDatabaseTool.createNewWindow(new DBDataEditorWindow(this.dbTable, this.dbTable.getSelectedRowIndex(), this.dbTable.duplicateRow(this.dbTable.getSelectedRowIndex()), true));
		}else if(removeRow.matchesIndex(index)){
			this.dbTable.removeSelectedRow();
		}else if(newEntry.matchesIndex(index)){
			addEmptyRow();
		}else if(sync.matchesIndex(index)){
			this.dbTable.synchronizeToDatabase();
		}else if(viewEdits.matchesIndex(index)){
			WebsiteDatabaseTool.createNewWindow(new ViewEditsWindow(this.dbTable, this.qc));
		}
    }


	/**
	 * Add a new and empty entry to the database table
	 */
	private void addEmptyRow() {
		if (Program.DATABASE_CONNECTION.isConnected()){
			if (this.dbTable.hasColumns()){
				WebsiteDatabaseTool.createNewWindow(new DBDataEditorWindow(this.dbTable, this.dbTable.getRowSize(), this.dbTable.getNewRowData(), true));
			}else{
				this.qc.connControl.getDatabaseFXController().setConsoleText(Lang.NOTIF_TITLE_NEW_ENTRY_ERROR + "\n" + Lang.NOTIF_BODY_NO_COLUMNS);
			}
		}else {
			this.qc.connControl.getDatabaseFXController().setConsoleText(Lang.NOTIF_TITLE_NEW_ENTRY_ERROR + "\n" + Lang.NOTIF_BODY_NOT_CONNECTED);
		}
	}

}