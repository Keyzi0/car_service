package sample.operator;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;

import java.sql.SQLException;

import javafx.scene.input.MouseEvent;
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
        carOwnerTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                if (event.isPrimaryButtonDown()) {
                    System.out.println(carOwnerTable.getSelectionModel().getSelectedItem());
                }
            }
        });
    }

}
