package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.store.Store;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class DBComboBox extends ComboBox<ComboItem> {
    public DBComboBox() {
        super();
        this.setConverter(ComboItem.converter);
    }

    public void fillFromBD(Store store, String SQL) throws SQLException, ClassNotFoundException {
        ObservableList<ComboItem> list = FXCollections.observableArrayList();
        ResultSet rs = store.execQuery(SQL);
        while (rs.next()) {
            list.add(ComboItem.getItemFromResultSet(rs));
        }
        this.setItems(list);
    }

    public void reset() {
        this.getSelectionModel().clearSelection();
        this.valueProperty().set(null);
    }

    public void setByID(int id) {
//        tabs.forEach((tab) -> {
//            System.out.println("Stuff with "+tab);
//        });
        AtomicInteger idx = new AtomicInteger();
        this.getItems().forEach((elem) -> {
            if (elem.getID() == id) {
                this.getSelectionModel().select(idx.get());
            }
            idx.set(idx.get() + 1);
        });
    }
}
