package sample.store;

import sample.Config;
import java.sql.*;

public class Store extends Config {
    // метод полученияэкземпляра класса store
    public static Store getStore() throws SQLException, ClassNotFoundException {
        Store item = new Store();
        item.getDbConnection();
        return  item;
    }

    // подключаемся к базе данных
    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    //метод для получения данных из БД по запросу без параметров
    public ResultSet execQuery(String SQL) throws SQLException, ClassNotFoundException {
        PreparedStatement prSt = getDbConnection().prepareStatement(SQL);
        ResultSet rs = prSt.executeQuery();
        return rs;
    }

    //метод для получения данных из БД по запросу с параметрами
    public ResultSet execQuery(String SQL, Object[] params) throws SQLException, ClassNotFoundException {
        PreparedStatement prSt = getDbConnection().prepareStatement(SQL);
        for (int i = 1; i <= params.length; i++) {
            prSt.setObject(i, params[i-1]);
        }
        ResultSet rs = prSt.executeQuery();
        return rs;
    }

    // метод для изменения данных в БД (создание, обновление, удаление)
    public void createOrUpdate(String SQL, Object[] params) throws SQLException, ClassNotFoundException {
        PreparedStatement prSt = getDbConnection().prepareStatement(SQL);
        int i = 1;
        for (Object val:params) {
            prSt.setObject(i, val);
            i++;
        }
        prSt.executeUpdate();
    }

}
