package sample.operator;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;

import java.sql.SQLException;

import sample.Config;
import sample.store.Store;

public class operator extends Config {

    @FXML
    private Tab carNavTab;

    @FXML
    private TableView<ObservableList> carOwnerTable;

    @FXML
    void initialize () throws SQLException, ClassNotFoundException {
        //TABLE VIEW AND DATA
        Store store = Store.getStore();
        store.loadTableData(carOwnerTable,"SELECT * FROM car_owner");
    }

}
