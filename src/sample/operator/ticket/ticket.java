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
    private DBComboBox defectCombo;

    @FXML
    private DBComboBox mechanicCombo;

    @FXML
    private DBComboBox statusCombo;

    @FXML
    private TextField price;

    @FXML
    private Button saveButton;

    Store store;
    int owner_id;
    String owner_name;
    int car_id;
    String car_model;

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
        defectCombo.fillFromBD(store, "SELECT * FROM defect");
        statusCombo.fillFromBD(store, "SELECT * FROM status");
        mechanicCombo.fillFromBD(store, "SELECT id, name FROM mechanic");
    }

    public void setParams(int owner_id, String owner_name, int car_id, String car_model) {
        this.owner_id = owner_id;
        this.owner_name = owner_name;
        this.car_id = car_id;
        this.car_model = car_model;
        ownerName.setText(owner_name);
        carModel.setText(car_model);
        System.out.println("Ticket setParams");
    }

    public void setSelection(int defect_id, int mechanic_id, int status_id, Integer income_price) {
        defectCombo.setByID(defect_id);
        mechanicCombo.setByID(mechanic_id);
        statusCombo.setByID(status_id);
        price.setText(income_price.toString());
    }

    private void addTicket() throws SQLException, ClassNotFoundException {
        if ( defectCombo.getValue() == null || mechanicCombo.getValue() == null || defectCombo.getValue() == null) {
            return;
        }
        TicketModel newTicket = new TicketModel();
        newTicket.setCarOwnerID(owner_id);
        newTicket.setCarID(car_id);
        newTicket.setDefectID(defectCombo.getValue().getID());
        newTicket.setMechanicID(mechanicCombo.getValue().getID());
        newTicket.setStatusID(statusCombo.getValue().getID());
        newTicket.setPrice(Integer.parseInt(price.getText().isEmpty()?"0":price.getText()));
        store.create(TicketModel.getInsertSQL(), TicketModel.getSQLParams(newTicket));
    }

}
