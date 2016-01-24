package com.kaylerrenslow.mysqlDatabaseTool.fx.control.factory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author Kayler
 * Created on 12/9/15.
 */
public class TableRowFactory implements Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>> {
    private final int tableColumn;

    public TableRowFactory(int tableColumn) {
        this.tableColumn = tableColumn;
    }

    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
        return new SimpleStringProperty(param.getValue().get(tableColumn).toString());
    }
}
