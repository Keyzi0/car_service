package sample.operator;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Config;
import sample.models.CarModel;
import sample.models.CarOwnerModel;
import sample.models.ComboItem;
import sample.models.DBComboBox;
import sample.operator.ticket.ticket;
import sample.store.Store;

public class operator extends Config {

    @FXML
    private Tab carNavTab;

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
    private TextField addCarOwnerName;

    @FXML
    private TextField addCarOwnerPassport;

    @FXML
    private TextField addCarOwnerAddress;

    @FXML
    private TextField addCarOwnerAge;

    @FXML
    private Button addCarOwnerButton;

    @FXML
    private TextField addCarModel;

    @FXML
    private TextField addCarColor;

    @FXML
    private TextField addCarYear;

    @FXML
    private TextField addCarSign;

    @FXML
    private Button addCarButton;

    @FXML
    private TextField addCarClientName;

    @FXML
    private DBComboBox taskOwnerCombo;

    @FXML
    private DBComboBox taskMechanicCombo;

    @FXML
    private DBComboBox taskStatusCombo;

    @FXML
    private DBComboBox taskDefectCombo;

    @FXML
    private Button taskFilterResetButton;

    Store store;
    CarOwnerModel selectedCarOwner;
    CarModel selectedCar;

    @FXML
    void initialize () throws SQLException, ClassNotFoundException {
        store = Store.getStore();
        addCarOwnerButton.setOnAction(actionEvent -> {
            try {
                addNewClient();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        addCarButton.setOnAction(actionEvent -> {
            try {
                addNewCar();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

//        taskOwnerCombo.setConverter(ComboItem.converter);
//        taskMechanicCombo.setConverter(ComboItem.converter);
//        taskStatusCombo.setConverter(ComboItem.converter);
//        taskDefectCombo.setConverter(ComboItem.converter);
        taskOwnerCombo.fillFromBD(store, "SELECT id, name FROM car_owner");
        taskMechanicCombo.fillFromBD(store, "SELECT id, name FROM mechanic");
        taskStatusCombo.fillFromBD(store, "SELECT * FROM status");
        taskDefectCombo.fillFromBD(store, "SELECT * FROM defect");
        // устанавливаем тип и значение которое должно хранится в колонке
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
                    try {
                        fillCarTable(rowData.getId());
                        addCarClientName.setText(rowData.getName());
                        if (rowData.getName() != null && rowData.getName() != "") {
                            addCarButton.setDisable(false);
                        }
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
                    System.out.println("Double click on: "+rowData.getModel());
                    selectedCar = rowData;
                    try {
                        showAddTicketModal(event);
                    } catch (IOException  e) {
                        e.printStackTrace();
                    }
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

    private void addNewClient() throws SQLException, ClassNotFoundException {
        if (addCarOwnerName.getText().isEmpty()) {
            return;
        }
        CarOwnerModel newClient = new CarOwnerModel();
        newClient.setName(addCarOwnerName.getText().trim());
        try {
            newClient.setAge(Integer.parseInt(addCarOwnerAge.getText().trim()));
        } catch (Exception e) {
            newClient.setAge(0);
        }
        newClient.setPassport(addCarOwnerPassport.getText().trim());
        newClient.setAddress(addCarOwnerAddress.getText().trim());
        try {
            store.create(
                    CarOwnerModel.getInsertSQL(),
                    CarOwnerModel.getSQLParams(newClient)
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        fillCarOwnerTable();
    }

    private void addNewCar() throws SQLException, ClassNotFoundException {
        if (addCarModel.getText().isEmpty()) {
            return;
        }
        CarModel newCar = new CarModel();
        newCar.setModel(addCarModel.getText().trim());
        try {
            newCar.setYear(Integer.parseInt(addCarYear.getText().trim()));
        } catch (Exception e) {
            newCar.setYear(0);
        }
        newCar.setColor(addCarColor.getText().trim());
        newCar.setSign(addCarSign.getText().trim());
        newCar.setOwnerID(selectedCarOwner.getId());
        try {
            store.create(
                    CarModel.getInsertSQL(),
                    CarModel.getSQLParams(newCar)
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        fillCarTable(selectedCarOwner.getId());
    }

    private void showAddTicketModal(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ticket.class.getResource("ticket.fxml")));
        Parent root = loader.load();
        ticket ticketController = loader.getController();
        ticketController.setParams(selectedCarOwner, selectedCar);
        stage.setScene(new Scene(root));
        stage.setTitle("Add ticket");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
    }

}
