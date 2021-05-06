package sample.operator.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.models.CarModel;
import sample.models.CarOwnerModel;
import sample.store.Store;

public class ticket {

    @FXML
    private TextField ownerName;

    @FXML
    private TextField carModel;

    @FXML
    private ComboBox<?> defectCombo;

    @FXML
    private ComboBox<?> mechanicCombo;

    @FXML
    private ComboBox<?> statusCombo;

    @FXML
    private TextField price;

    @FXML
    private Button saveButton;

    Store store;
    CarOwnerModel owner;
    CarModel car;

    @FXML
    void initialize () {
        saveButton.setOnAction(actionEvent -> {
            addTicket();
        });
    }

    public void setParams(CarOwnerModel owner, CarModel car) {
        this.owner = owner;
        this.car = car;
        ownerName.setText(owner.getName().trim());
        carModel.setText(car.getModel().trim());
        System.out.println("Ticket setParams ");
    }

    private void addTicket() {

    }
}
