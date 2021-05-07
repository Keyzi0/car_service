package sample.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.Config;
import sample.models.CarModel;
import sample.models.CarOwnerModel;
import sample.models.TicketViewModel;
import sample.store.Store;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class admin extends Config {

    @FXML
    private Tab carNavTab;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<CarOwnerModel> carOwnerTable;

    @FXML
    private TableColumn<CarOwnerModel, String> nameColumn;

    @FXML
    private TableColumn<CarOwnerModel, String> passportColumn;

    @FXML
    private TableColumn<CarOwnerModel, String> addressColumn;

    private ObservableList<CarOwnerModel> carOwnerList = FXCollections.observableArrayList();

    @FXML
    private TableView<CarModel> carTable;

    @FXML
    private TableColumn<CarModel, String> modelColumn;

    @FXML
    private TableColumn<CarModel, String> colorColumn;

    @FXML
    private TableColumn<CarModel, Integer> yearColumn;

    @FXML
    private TableColumn<CarModel, String> signColumn;

    private ObservableList<CarModel> carList = FXCollections.observableArrayList();

    @FXML
    private TextField editCarOwnerName;

    @FXML
    private TextField editCarOwnerPassport;

    @FXML
    private TextField editCarOwnerAddress;

    @FXML
    private TextField editCarOwnerAge;

    @FXML
    private Button editCarOwnerButton;

    @FXML
    private Button carOwnerDelete;

    @FXML
    private TextField editCarModel;

    @FXML
    private TextField editCarColor;

    @FXML
    private TextField editCarYear;

    @FXML
    private TextField editCarSign;

    @FXML
    private Button editCarButton;

    @FXML
    private TextField editCarClientName;

    @FXML
    private Button taskFilterResetButton;

    @FXML
    private Button taskRefreshButton;

    @FXML
    private TableView<TicketViewModel> ticketTable;

    @FXML
    private TableColumn<TicketViewModel, Integer> taskIdColumn;

    @FXML
    private TableColumn<TicketViewModel, String> taskOwnerColumn;

    @FXML
    private TableColumn<TicketViewModel, String> taskCarColumn;

    @FXML
    private TableColumn<TicketViewModel, String> taskMechanicColumn;

    @FXML
    private TableColumn<TicketViewModel, String> taskDefectColumn;

    @FXML
    private TableColumn<TicketViewModel, String> taskStatusColumn;

    @FXML
    private TableColumn<TicketViewModel, Date> taskIncomeDateColumn;

    private ObservableList<TicketViewModel> ticketList = FXCollections.observableArrayList();

    Store store;
    CarOwnerModel selectedCarOwner;
    CarModel selectedCar;

    @FXML
    void initialize () throws SQLException, ClassNotFoundException {
        store = Store.getStore();
        editCarOwnerButton.setOnAction(actionEvent -> {
            CarOwnerModel owner = new CarOwnerModel();
            owner.setId(selectedCarOwner.getId());
            owner.setName(editCarOwnerName.getText());
            owner.setAddress(editCarOwnerAddress.getText());
            owner.setAge(Integer.parseInt(editCarOwnerAge.getText().isEmpty()?"0":editCarOwnerAge.getText()));
            owner.setPassport(editCarOwnerPassport.getText());
            try {
                store.createOrUpdate(CarOwnerModel.getUpdateSQL(), CarOwnerModel.getSQLUpdateParams(owner));
                carOwnerTable.getItems().clear();
                fillCarOwnerTable();
                carTable.getItems().clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        editCarButton.setOnAction(actionEvent -> {
            CarModel car = new CarModel();
            car.setId(selectedCar.getId());
            car.setOwnerID(selectedCarOwner.getId());
            car.setModel(editCarModel.getText());
            car.setColor(editCarColor.getText());
            car.setYear(Integer.parseInt(editCarYear.getText().isEmpty()?"0":editCarYear.getText()));
            car.setSign(editCarSign.getText());
            try {
                store.createOrUpdate(CarModel.getUpdateSQL(), CarModel.getSQLUpdateParams(car));
                carTable.getItems().clear();
                fillCarTable(selectedCarOwner.getId());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

        logoutButton.setOnAction(actionEvent -> {
            logoutButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/login/login.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        carOwnerDelete.setOnAction(actionEvent -> {
            Object[] params = { selectedCarOwner.getId() };
            try {
                store.createOrUpdate(CarOwnerModel.getFullDeleteSQL(), params);
                carOwnerTable.getItems().clear();
                carTable.getItems().clear();
                selectedCarOwner = null;
                selectedCar = null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        fillCarOwnerTable();
    }

    private void fillCarOwnerTable() throws SQLException, ClassNotFoundException {
        carOwnerTable.getItems().clear();
        carOwnerTable.setRowFactory( tv -> {
            TableRow<CarOwnerModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    CarOwnerModel rowData = row.getItem();
                    System.out.println("Double click on: "+rowData.getName());
                    selectedCarOwner = rowData;
                    editCarOwnerName.setText(rowData.getName());
                    editCarOwnerAddress.setText(rowData.getAddress());
                    editCarOwnerAge.setText(String.valueOf(rowData.getAge()));
                    editCarOwnerPassport.setText(rowData.getPassport());
                    try {
                        fillCarTable(rowData.getId());
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        nameColumn.setCellValueFactory(new PropertyValueFactory<CarOwnerModel, String>("name"));
        passportColumn.setCellValueFactory(new PropertyValueFactory<CarOwnerModel, String>("passport"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<CarOwnerModel, String>("address"));

        Store store = Store.getStore();
        ResultSet rs = store.execQuery("SELECT * FROM car_owner ORDER BY id");
        while (rs.next()) {
            carOwnerList.add(CarOwnerModel.getItemFromResultSet(rs));
        }
        carOwnerTable.setItems(carOwnerList);
    }

    private void fillCarTable(int owner_id) throws SQLException, ClassNotFoundException {
        carTable.getItems().clear();
        carTable.setRowFactory( tv -> {
            TableRow<CarModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    CarModel rowData = row.getItem();
                    selectedCar = rowData;
                    editCarModel.setText(rowData.getModel());
                    editCarColor.setText(rowData.getColor());
                    editCarYear.setText(String.valueOf(rowData.getYear()));
                    editCarSign.setText(rowData.getSign());
                }
            });
            return row ;
        });
        modelColumn.setCellValueFactory(new PropertyValueFactory<CarModel, String>("model"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<CarModel, String>("color"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<CarModel, Integer>("year"));
        signColumn.setCellValueFactory(new PropertyValueFactory<CarModel, String>("sign"));

        String sql = "SELECT * FROM car Where owner_id = ? ORDER BY id";
        Object[] params = {owner_id};
        ResultSet rs = store.execQuery(sql, params);
        while (rs.next()) {
            carList.add(CarModel.getItemResultSet(rs));
        }
        carTable.setItems(carList);
    }

}
