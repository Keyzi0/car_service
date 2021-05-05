package sample.store;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import sample.Config;

import java.sql.*;

public class Store extends Config {
    Connection dbConnection;

    public static Store getStore() throws SQLException, ClassNotFoundException {
        Store item = new Store();
        item.getDbConnection();
        return  item;
    }

    private void getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    private void chkDBConnection() throws SQLException, ClassNotFoundException {
        if (dbConnection.isClosed()) {
            this.getDbConnection();
        }
    }

    public ResultSet execQuery(String SQL) throws SQLException, ClassNotFoundException {
        chkDBConnection();
        PreparedStatement prSt = dbConnection.prepareStatement(SQL);
        ResultSet rs = prSt.executeQuery();
        return rs;
    }
}
