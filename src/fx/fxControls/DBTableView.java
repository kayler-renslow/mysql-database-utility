package fx.fxControls;

import fx.fxControls.factory.TableRowFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import main.Lang;
import main.Util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by kayler on 12/9/15.
 */
public class DBTableView {
    public final TableView<ObservableList> tv;
    private ContextMenu cm;
    private boolean error = false;

    public DBTableView(TableView tv) {
        this.tv = tv;
        this.tv.setEditable(true);
        initializeContextMenu();
    }

    private void initializeContextMenu() {
        cm = new ContextMenu_DBTableView(this.tv);
        this.tv.setContextMenu(cm);
    }

    /**Create a single column with one row on the query result TableView. The row displays the error message.*/
    public void addTableRowError(String title, String errorMsg){
        tv.getColumns().clear();

        //add table column
        TableColumn tcol = new TableColumn<>(title);
        tcol.setCellValueFactory(new TableRowFactory(0));
        tv.getColumns().add(tcol);

        //object that holds all the row data
        ObservableList<ObservableList> allRows = FXCollections.observableArrayList();

        //parsed row data
        ObservableList<String> row = FXCollections.observableArrayList();
        final int LINE_LENGTH = 40;
        String errorMsgPrime = Util.addLinebreaks(errorMsg, LINE_LENGTH);

        row.add(errorMsgPrime);
        allRows.add(row);
        tv.setItems(allRows);

        error = true;

    }

    public void querySuccess(ResultSet rs) throws SQLException{
        error = false;
        tv.getColumns().clear();
        ResultSetMetaData rsmd = rs.getMetaData();

        //add all the table columns
        String colName;
        TableColumn tcol;
        for(int col = 1; col <= rsmd.getColumnCount(); col++){
            colName = rsmd.getColumnName(col);
            tcol = new TableColumn<>(colName);
            tcol.setCellValueFactory(new TableRowFactory(col - 1));
            tv.getColumns().add(tcol);
        }


        //object that holds all the row data
        ObservableList<ObservableList> allRows = FXCollections.observableArrayList();

        addNewRows(rs, rsmd.getColumnCount(), allRows);

        tv.setItems(allRows);
    }

    private void addNewRows(ResultSet rs, int columnCount, ObservableList allRows) throws SQLException{
        ObservableList<String> row;

        //now add all of the row data
        while(rs.next()){
            row = FXCollections.observableArrayList();
            for(int col = 1; col<= columnCount; col++){
                row.add(rs.getString(col));
            }
            allRows.add(row);
        }
    }

    public void addEmptyRow(){
        if(tv.getColumns().size() <= 0 || error){
            addTableRowError(Lang.NOTIF_TITLE_NEW_ENTRY_ERROR, Lang.NOTIF_BODY_NO_COLUMNS);
            return;
        }

        String[] data = new String[this.tv.getColumns().size()];
        for(int i = 0; i < this.tv.getColumns().size(); i++){
            data[i] = "new " + this.tv.getColumns().get(i).getText();
        }
        ObservableList<String> row = FXCollections.observableArrayList();
        row.addAll(data);
        this.tv.getItems().add(row);
    }

    public void beginEdit(int row, int col){
        this.tv.edit(row, this.tv.getColumns().get(col));
    }
}
