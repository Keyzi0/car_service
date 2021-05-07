package sample.operator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Config;
import sample.models.*;
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

        taskFilterResetButton.setOnAction(actionEvent -> {
            taskOwnerCombo.reset();
            taskMechanicCombo.reset();
            taskStatusCombo.reset();
            taskDefectCombo.reset();
        });

        taskRefreshButton.setOnAction(actionEvent -> {
            try {
                fillTicketTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

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
                        ticket controller = showTicketModal(event);
                        controller.setParams(selectedCarOwner.getId(),selectedCarOwner.getName(), selectedCar.getId(), selectedCar.getModel());
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

    private ticket showTicketModal(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ticket.class.getResource("ticket.fxml")));
        Parent root = loader.load();
        ticket ticketController = loader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle("Add ticket");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node)event.getSource()).getScene().getWindow());
        stage.show();
        return  ticketController;
    }

    private void fillTicketTable() throws SQLException, ClassNotFoundException {
        ticketTable.getItems().clear();
        ticketTable.setRowFactory( tv -> {
            TableRow<TicketViewModel> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    TicketViewModel rowData = row.getItem();
                    try {
                        ticket controller = showTicketModal(event);
                        controller.setParams(
                                rowData.getCar_owner_id(),
                                rowData.getCar_owner(),
                                rowData.getCar_id(),
                                rowData.getCar()
                        );
                        controller.setDetails(
                                rowData.getId(),
                                rowData.getDefect_id(),
                                rowData.getMechanic_id(),
                                rowData.getStatus_id(),
                                rowData.getPrice()
                        );
                    } catch (IOException  e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        taskIdColumn.setCellValueFactory(new PropertyValueFactory<TicketViewModel, Integer>("id"));
        taskOwnerColumn.setCellValueFactory(new PropertyValueFactory<TicketViewModel, String>("car_owner"));
        taskCarColumn.setCellValueFactory(new PropertyValueFactory<TicketViewModel, String>("car"));
        taskMechanicColumn.setCellValueFactory(new PropertyValueFactory<TicketViewModel, String>("mechanic"));
        taskDefectColumn.setCellValueFactory(new PropertyValueFactory<TicketViewModel, String>("defect"));
        taskStatusColumn.setCellValueFactory(new PropertyValueFactory<TicketViewModel, String>("status"));
        taskIncomeDateColumn.setCellValueFactory(new PropertyValueFactory<TicketViewModel, Date>("income_date"));



        String sql = "SELECT * FROM ticketView";

        String filters = "";

        filters = addFilterFromCombo(filters, taskOwnerCombo, "car_owner_id");
        filters = addFilterFromCombo(filters, taskMechanicCombo, "mechanic_id");
        filters = addFilterFromCombo(filters, taskDefectCombo, "defect_id");
        filters = addFilterFromCombo(filters, taskStatusCombo, "status_id");

        sql = sql + filters;

        ResultSet rs = store.execQuery(sql);
        while (rs.next()) {
            ticketList.add(TicketViewModel.getItemFromResultSet(rs));
        }
        ticketTable.setItems(ticketList);
    }

    private String addFilterFromCombo(String filter, DBComboBox combo, String SQLFieldName) {
        if (combo.getValue() == null) {
            return filter;
        }
        if (filter.length()>0) {
            filter = filter + " and ";
        } else {
            filter = " where ";
        }
        filter = filter + SQLFieldName + " = " + combo.getValue().getID();
        return filter;
    }

}
