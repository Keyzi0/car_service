package sample.store;

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
        dbConnection.prepareStatement("SELECT 1");
    }

    public ResultSet execQuery(String SQL) throws SQLException, ClassNotFoundException {
        chkDBConnection();
        PreparedStatement prSt = dbConnection.prepareStatement(SQL);
        ResultSet rs = prSt.executeQuery();
        return rs;
    }

    public ResultSet execQuery(String SQL, Object[] params) throws SQLException, ClassNotFoundException {
        chkDBConnection();
        PreparedStatement prSt = dbConnection.prepareStatement(SQL);
        for (int i = 1; i <= params.length; i++) {
            prSt.setObject(i, params[i-1]);
        }
        ResultSet rs = prSt.executeQuery();
        return rs;
    }

    public void create(String SQL, Object[] params) throws SQLException, ClassNotFoundException {
        chkDBConnection();
        PreparedStatement prSt = dbConnection.prepareStatement(SQL);
        int i = 1;
        for (Object val:params) {
            prSt.setObject(i, val);
            i++;
        }
        prSt.executeUpdate();
    }



}
