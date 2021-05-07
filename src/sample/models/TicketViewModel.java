package sample.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TicketViewModel {
    public static final String TABLE_NAME = "ticket";
    public static final String ID = "id";
    public static final String CAR_OWNER_ID = "car_owner_id";
    public static final String CAR_OWNER = "car_owner";
    public static final String CAR_ID = "car_id";
    public static final String CAR = "car";
    public static final String MECHANIC_ID = "mechanic_id";
    public static final String MECHANIC = "mechanic";
    public static final String DEFECT_ID = "defect_id";
    public static final String DEFECT = "defect";
    public static final String STATUS_ID = "status_id";
    public static final String STATUS = "status";
    public static final String PRICE = "price";
    public static final String INCOME_DATE = "income_date";


    private int id;
    int car_owner_id;
    private String car_owner;
    private int car_id;
    private String car;
    private int mechanic_id;
    private String mechanic;
    private int defect_id;
    private String defect;
    private int status_id;
    private String status;
    private int price;
    private Date income_date;

    public TicketViewModel(
            int id,
            int car_owner_id,
            String car_owner,
            int car_id,
            String car,
            int mechanic_id,
            String mechanic,
            int defect_id,
            String defect,
            int status_id,
            String status,
            int price,
            Date income_date
    ) {
        this.id = id;
        this.car_owner_id = car_owner_id;
        this.car_owner = car_owner;
        this.car_id = car_id;
        this.car = car;
        this.mechanic_id = mechanic_id;
        this.mechanic = mechanic;
        this.defect_id = defect_id;
        this.defect = defect;
        this.status_id = status_id;
        this.status = status;
        this.price = price;
        this.income_date = income_date;
    }

    public TicketViewModel() {
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public int getCar_owner_id() {
        return car_owner_id;
    }

    public String getCar_owner() {
        return car_owner;
    }

    public void setCar_owner(String car_owner) {
        this.car_owner = car_owner;
    }

    public int getCar_id() {
        return car_id;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public int getMechanic_id() {
        return mechanic_id;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public int getDefect_id() { return defect_id; }

    public String getDefect() { return defect; }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public int getStatus_id() {
        return status_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public Date getIncome_date() {
        return income_date;
    }

    public static TicketViewModel getItemFromResultSet(ResultSet rs) throws SQLException {
        String owner = rs.getString(CAR_OWNER);
        TicketViewModel item = new TicketViewModel(
                rs.getInt(ID),
                rs.getInt(CAR_OWNER_ID),
                rs.getString(CAR_OWNER),
                rs.getInt(CAR_ID),
                rs.getString(CAR),
                rs.getInt(MECHANIC_ID),
                rs.getString(MECHANIC),
                rs.getInt(DEFECT_ID),
                rs.getString(DEFECT),
                rs.getInt(STATUS_ID),
                rs.getString(STATUS),
                rs.getInt(PRICE),
                rs.getDate(INCOME_DATE)
        );
        return item;
    }

}
