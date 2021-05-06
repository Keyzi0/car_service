package sample.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TicketModel {
    public static final String TABLE_NAME = "ticket";
    public static final String ID = "id";
    public static final String CAR_OWNER_ID = "car_owner_id";
    public static final String CAR_ID = "car_id";
    public static final String MECHANIC_ID = "mechanic_id";
    public static final String DEFECT_ID = "defect_id";
    public static final String PRICE = "price";
    public static final String INCOME_DATE = "income_date";
    public static final String FINISH_DATE = "finish_date";

    private int id;
    private int car_owner_id;
    private int car_id;
    private int mechanic_id;
    private int defect_id;
    private int price;
    private Date income_date;
    private Date finish_date;

    public TicketModel(
            int id,
            int car_owner_id,
            int car_id,
            int mechanic_id,
            int defect_id,
            int price,
            Date income_date,
            Date finish_date
    ) {
        this.id = id;
        this.car_owner_id = car_owner_id;
        this.car_id = car_id;
        this.mechanic_id = mechanic_id;
        this.defect_id = defect_id;
        this.price = price;
        this.income_date = income_date;
        this.finish_date = finish_date;
    }

    public TicketModel() {
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getCarOwnerID() {
        return car_owner_id;
    }

    public void setCarOwnerID(int car_owner_id) {
        this.car_owner_id = car_owner_id;
    }

    public int getCarID() {
        return car_id;
    }

    public void setCarID(int car_id) {
        this.car_id = car_id;
    }

    public int getMechanicID() {
        return mechanic_id;
    }

    public void setMechanicID(int mechanic_id) {
        this.mechanic_id = mechanic_id;
    }

    public int getDefectID() {
        return defect_id;
    }

    public void setDefectID(int defect_id) {
        this.defect_id = defect_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getIncomeDate() {
        return income_date;
    }

    public Date getFinishDate() {
        return finish_date;
    }


    public static TicketModel getTicketFromResultSet(ResultSet rs) throws SQLException {
        TicketModel item = new TicketModel(
                rs.getInt(ID),
                rs.getInt(CAR_OWNER_ID),
                rs.getInt(CAR_ID),
                rs.getInt(MECHANIC_ID),
                rs.getInt(DEFECT_ID),
                rs.getInt(PRICE),
                rs.getDate(INCOME_DATE),
                rs.getDate(FINISH_DATE)
        );
        return item;
    }

    public static String getInsertSQL() {
        return "INSERT INTO " + TABLE_NAME + "(" + CAR_OWNER_ID + "," + CAR_ID + "," + MECHANIC_ID + "," + DEFECT_ID + "," + PRICE + "," + INCOME_DATE + ")" + "VALUES(?,?,?,?,?,SYSDATE())";
    }

    public static Object[] getSQLParams(TicketModel item) {
        Object[] params = {item.getCarOwnerID(), item.getCarID(), item.getMechanicID(), item.getDefectID(), item.getPrice()};
        return params;
    }
}
