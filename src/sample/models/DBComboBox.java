package sample.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import sample.store.Store;

import java.sql.ResultSet;
import java.sql.SQLException;

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
}
