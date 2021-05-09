package sample.models;

import java.sql.ResultSet;
import java.sql.SQLException;
/*
    Модель данных владелец автомобиля.
 */
public class CarOwnerModel {
    // константы имен таблиц базы данных
    public static final String TABLE_NAME = "car_owner";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String AGE = "age";
    public static final String PASSPORT = "passport";

    // свойства модели (тут хранятся данные о владельце авто)
    private int id;
    private String name;
    private String passport;
    private String address;
    private int age;

    // метод для создания объекта класса с предопределенными свойствами
    public CarOwnerModel(
            int id,
            String name,
            String passport,
            String address,
            int age
    ) {
        this.id = id;
        this.name = name;
        this.passport = passport;
        this.address = address;
        this.age = age;
    }

    // метод для создания пустого объекта класса
    public CarOwnerModel() {
    }

    // методы для присвоения/получения значений свойств объекта
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // метод для создания объекта класса из результата запроса в БД
    public static CarOwnerModel getItemFromResultSet(ResultSet rs) throws SQLException {
        CarOwnerModel item = new CarOwnerModel();
        item.id = rs.getInt(ID);
        item.address = rs.getString(ADDRESS);
        item.age = rs.getInt(AGE);
        item.name = rs.getString(NAME);
        item.passport = rs.getString(PASSPORT);
        return item;
    }

    // метод получения SQL запроса на создание новой записи в БД
    public static String getInsertSQL() {
        return "INSERT INTO " + TABLE_NAME + "(" + NAME + "," + PASSPORT + "," + AGE + "," + ADDRESS + ")" + "VALUES(?,?,?,?)";
    }

    // метод создания массива параметров для создания записи в БД на основании существующего объекта
    public static Object[] getSQLParams(CarOwnerModel item) {
        Object[] params = {item.getName(), item.getPassport(), item.getAge(), item.getAddress()};
        return params;
    }

    // метод получения SQL запроса на обновление записи в БД
    public static String getUpdateSQL() {
        return "UPDATE  " + TABLE_NAME + " SET " + NAME + " = ?," + PASSPORT + " = ?," + AGE+ "  = ?," + ADDRESS + "  = ? WHERE id = ?";
    }

    // метод создания массива параметров для обновления записи в БД на основании существующего объекта
    public static Object[] getSQLUpdateParams(CarOwnerModel item) {
        Object[] params = { item.getName(), item.getPassport(), item.getAge(), item.getAddress(), item.getId()};
        return params;
    }

    // метод получения SQL запроса для удаления из БД всех данных связанных с владельцем(включая информацию о самом владельце)
    public static String getFullDeleteSQL() {
        return " DELETE co, c, t " +
                " FROM car_owner co " +
                " LEFT JOIN car c ON co.id = c.owner_id " +
                " LEFT JOIN ticket t ON co.id = t.car_owner_id " +
                " WHERE co.id=?";
    }
}
