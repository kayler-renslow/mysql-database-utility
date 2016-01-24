package com.kaylerrenslow.mysqlDatabaseTool.fx.control;

import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuItem;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.FXMenuUtil;
import com.kaylerrenslow.mysqlDatabaseTool.fx.control.lib.menu.IFXMenuEventHandle;
import com.kaylerrenslow.mysqlDatabaseTool.main.Lang;
import com.kaylerrenslow.mysqlDatabaseTool.main.WebsiteDatabaseTool;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;

import java.util.Iterator;

/**
 * @author Kayler
 * Created on 12/15/15.
 */
public class ContextMenu_DBTableView extends javafx.scene.control.ContextMenu implements IFXMenuEventHandle {

    private FXMenuItem edit = new FXMenuItem(Lang.CONTEXT_MENU_DBTV_EDIT);
    private DBTableView dbtv;
	private String[] columns;

    public ContextMenu_DBTableView(DBTableView dbtv){
        FXMenuUtil.addItems(this, this, edit);
        this.dbtv = dbtv;
    }

    @Override
    public void handle(int index, ActionEvent event) {
        if(index == edit.getInsertionIndex()){
			if(this.dbtv.tv.getSelectionModel().getSelectedItem() == null){
				return;
			}
            WebsiteDatabaseTool.createNewWindow(new DBDataEditorWindow(this.dbtv.getColumnNames(), this.dbtv.getColumnTypes(), this.dbtv.tv.getSelectionModel().getSelectedItem()));
        }
    }

    private void setColumns(){
		this.columns = new String[this.dbtv.tv.getColumns().size()];
        Iterator<TableColumn<ObservableList, ?>> iter = this.dbtv.tv.getColumns().listIterator();
        int i = 0;
        while(iter.hasNext()){
			this.columns[i] = iter.next().getText();
            i++;
        }
    }
}