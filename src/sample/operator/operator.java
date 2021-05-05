package sample.operator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Config;
import sample.models.CarOwner;
import sample.store.Store;

public class operator extends Config {

    @FXML
    private Tab carNavTab;

    @FXML
    private TableView<CarOwner> carOwnerTable;

    @FXML
    private TableColumn<CarOwner, Integer> idColumn;

    @FXML
    private TableColumn<CarOwner, String> nameColumn;

    @FXML
    private TableColumn<CarOwner, String> passportColumn;

    @FXML
    private TableColumn<CarOwner, String> addressColumn;

    private ObservableList<CarOwner> carOwnerList = FXCollections.observableArrayList();

    @FXML
    void initialize () throws SQLException, ClassNotFoundException {
        //TABLE VIEW AND DATA

        // устанавливаем тип и значение которое должно хранится в колонке
        fillCarOwnerTable();
    }

    private void fillCarOwnerTable() throws SQLException, ClassNotFoundException {
        idColumn.setCellValueFactory(new PropertyValueFactory<CarOwner, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<CarOwner, String>("name"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<CarOwner, String>("passport"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<CarOwner, String>("address"));

        Store store = Store.getStore();
        ResultSet rs = store.execQuery("SELECT * FROM car_owner");
        while (rs.next()) {
            carOwnerList.add(CarOwner.getCarOwnerFromResultSet(rs));
        }
        carOwnerTable.setItems(carOwnerList);
    }

}
