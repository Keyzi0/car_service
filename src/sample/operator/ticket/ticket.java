package sample.operator.ticket;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sample.models.Car;
import sample.models.CarOwner;

public class ticket {

    @FXML
    private TextField ownerName;

    @FXML
    private TextField carModel;

    @FXML
    private DatePicker income_date;

    @FXML
    private ComboBox<?> defectCombo;

    @FXML
    private ComboBox<?> mechanicCombo;

    @FXML
    private ComboBox<?> statusCombo;

    @FXML
    private TextField price;

    CarOwner owner;
    Car car;

    @FXML
    void initialize () {
//        ownerName.setText(owner.getName().trim());
//        carModel.setText(car.getModel().trim());
    }

    public void setParams(CarOwner owner, Car car) {
        this.owner = owner;
        this.car = car;
    }
}
