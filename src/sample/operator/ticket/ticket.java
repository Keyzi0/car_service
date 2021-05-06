package sample.operator.ticket;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.*;
import sample.store.Store;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ticket {

    @FXML
    private TextField ownerName;

    @FXML
    private TextField carModel;

    @FXML
    private ComboBox<ComboItem> defectCombo;

    @FXML
    private ComboBox<ComboItem> mechanicCombo;

    @FXML
    private ComboBox<ComboItem> statusCombo;

    @FXML
    private TextField price;

    @FXML
    private Button saveButton;

    Store store;
    CarOwnerModel selectedOwner;
    CarModel selectedCar;

    @FXML
    void initialize () throws SQLException, ClassNotFoundException {
        store = Store.getStore();
        saveButton.setOnAction(actionEvent -> {
            try {
                addTicket();
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        defectCombo.setConverter(ComboItem.converter);
        statusCombo.setConverter(ComboItem.converter);
        mechanicCombo.setConverter(ComboItem.converter);
        fillCombo(defectCombo, "SELECT * FROM defect");
        fillCombo(statusCombo, "SELECT * FROM status");
        fillCombo(mechanicCombo, "SELECT id, name FROM mechanic");
    }

    public void setParams(CarOwnerModel owner, CarModel car) {
        this.selectedOwner = owner;
        this.selectedCar = car;
        ownerName.setText(owner.getName().trim());
        carModel.setText(car.getModel().trim());
        System.out.println("Ticket setParams ");
    }

    private void addTicket() throws SQLException, ClassNotFoundException {
        if ( defectCombo.getValue() == null || mechanicCombo.getValue() == null || defectCombo.getValue() == null) {
            return;
        }
        TicketModel newTicket = new TicketModel();
        newTicket.setCarOwnerID(selectedOwner.getId());
        newTicket.setCarID(selectedCar.getId());
        newTicket.setDefectID(defectCombo.getValue().getID());
        newTicket.setMechanicID(mechanicCombo.getValue().getID());
        newTicket.setDefectID(defectCombo.getValue().getID());
        store.create(TicketModel.getInsertSQL(), TicketModel.getSQLParams(newTicket));
    }

    private void fillCombo(ComboBox combo, String SQL) throws SQLException, ClassNotFoundException {
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        ResultSet rs = store.execQuery(SQL);
        while (rs.next()) {
            list.add(ComboItem.getItemFromResultSet(rs));
        }
        combo.setItems(list);
    }
}
