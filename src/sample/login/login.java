package sample.login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.IOException;

public class login {
    private static final String ADMIN = "Администратор";
    private static final String OPERATOR = "Оператор";

    @FXML
    private ComboBox<String> loginCombobox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    void initialize () {
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        ADMIN,
                        OPERATOR
                );

        loginCombobox.setItems(options);

        loginButton.setOnAction(actionEvent -> {
            System.out.println("login");
            loginButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            if (this.loginCombobox.getValue() == ADMIN) {
                loader.setLocation(getClass().getResource("/sample/admin/admin.fxml"));
            } else {
                loader.setLocation(getClass().getResource("/sample/operator/operator.fxml"));
            };

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
    }

}
