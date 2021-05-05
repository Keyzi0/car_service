package sample.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarOwner {
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

    public CarOwner(
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

    public CarOwner() {
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
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

    public static CarOwner getCarOwnerFromResultSet(ResultSet rs) throws SQLException {
        CarOwner item = new CarOwner();
        item.id = rs.getInt(ID);
        item.address = rs.getString(ADDRESS);
        item.age = rs.getInt(AGE);
        item.name = rs.getString(NAME);
        item.passport = rs.getString(PASSPORT);
        return item;
    }
}
