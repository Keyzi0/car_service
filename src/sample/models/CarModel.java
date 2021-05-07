package sample.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarModel {
    public static final String TABLE_NAME = "car";
    public static final String ID = "id";
    public static final String MODEL = "model";
    public static final String COLOR = "color";
    public static final String YEAR = "year";
    public static final String SIGN = "sign";
    public static final String OWNER_ID = "owner_id";

    private int id;
    private String model;
    private String color;
    private int year;
    private String sign;
    private int owner_id;

    public CarModel(
            int id,
            String model,
            String color,
            int year,
            String sign,
            int owner_id
    ) {
        this.id = id;
        this.model = model;
        this.color = color;
        this.year = year;
        this.sign = sign;
        this.owner_id = owner_id;
    }

    public CarModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getOwnerID() {
        return owner_id;
    }

    public void setOwnerID(int owner_id) {
        this.owner_id = owner_id;
    }

    public static CarModel getItemResultSet(ResultSet rs) throws SQLException {
        CarModel item = new CarModel(
                rs.getInt(ID),
                rs.getString(MODEL),
                rs.getString(COLOR),
                rs.getInt(YEAR),
                rs.getString(SIGN),
                rs.getInt(OWNER_ID)
        );
        return item;
    }

    public static String getInsertSQL() {
        return "INSERT INTO " + TABLE_NAME + "(" + MODEL + "," + COLOR + "," + YEAR + "," + SIGN + "," + OWNER_ID + ")" + "VALUES(?,?,?,?,?)";
    }

    public static Object[] getSQLParams(CarModel item) {
        Object[] params = {item.getModel(), item.getColor(), item.getYear(), item.getSign(), item.getOwnerID()};
        return params;
    }

    public static String getUpdateSQL() {
        return "UPDATE  " + TABLE_NAME + " SET " + MODEL + " = ?," + COLOR + " = ?," + YEAR + "  = ?," + SIGN + "  = ? WHERE id = ?";
    }

    public static Object[] getSQLUpdateParams(CarModel item) {
        Object[] params = { item.getModel(), item.getColor(), item.getYear(), item.getSign(), item.getId()};
        return params;
    }
}
