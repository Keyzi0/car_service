package sample.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarOwnerModel {
    public static final String TABLE_NAME = "car_owner";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ADDRESS = "address";
    public static final String AGE = "age";
    public static final String PASSPORT = "passport";

    private int id;
    private String name;
    private String passport;
    private String address;
    private int age;

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

    public CarOwnerModel() {
    }

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

    public static CarOwnerModel getItemFromResultSet(ResultSet rs) throws SQLException {
        CarOwnerModel item = new CarOwnerModel();
        item.id = rs.getInt(ID);
        item.address = rs.getString(ADDRESS);
        item.age = rs.getInt(AGE);
        item.name = rs.getString(NAME);
        item.passport = rs.getString(PASSPORT);
        return item;
    }

    public static String getInsertSQL() {
        return "INSERT INTO " + TABLE_NAME + "(" + NAME + "," + PASSPORT + "," + AGE + "," + ADDRESS + ")" + "VALUES(?,?,?,?)";
    }

    public static Object[] getSQLParams(CarOwnerModel item) {
        Object[] params = {item.getName(), item.getPassport(), item.getAge(), item.getAddress()};
        return params;
    }

    public static String getUpdateSQL() {
        return "UPDATE  " + TABLE_NAME + " SET " + NAME + " = ?," + PASSPORT + " = ?," + AGE+ "  = ?," + ADDRESS + "  = ? WHERE id = ?";
    }

    public static Object[] getSQLUpdateParams(CarOwnerModel item) {
        Object[] params = { item.getName(), item.getPassport(), item.getAge(), item.getAddress(), item.getId()};
        return params;
    }

    public static String getFullDeleteSQL() {
        return " DELETE co, c, t " +
                " FROM car_owner co " +
                " LEFT JOIN car c ON co.id = c.owner_id " +
                " LEFT JOIN ticket t ON co.id = t.car_owner_id " +
                " WHERE co.id=?";
    }
}
