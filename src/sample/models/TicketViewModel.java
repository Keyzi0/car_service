package sample.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class TicketViewModel {
    public static final String TABLE_NAME = "ticket";
    public static final String ID = "id";
    public static final String CAR_OWNER = "car_owner";
    public static final String CAR = "car";
    public static final String MECHANIC = "mechanic";
    public static final String DEFECT = "defect";
    public static final String STATUS = "status";
    public static final String INCOME_DATE = "income_date";


    private int id;
    private String car_owner;
    private String car;
    private String mechanic;
    private String defect;
    private String status;
    private Date income_date;

    public TicketViewModel(
            int id,
            String car_owner,
            String car,
            String mechanic,
            String defect,
            String status,
            Date income_date
    ) {
        this.id = id;
        this.car_owner = car_owner;
        this.car = car;
        this.mechanic = mechanic;
        this.defect = defect;
        this.status = status;
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

    public String getCar_owner() {
        return car_owner;
    }

    public void setCar_owner(String car_owner) {
        this.car_owner = car_owner;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public String getDefect() { return defect; }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getIncome_date() {
        return income_date;
    }



    public static TicketViewModel getItemFromResultSet(ResultSet rs) throws SQLException {
        String owner = rs.getString(CAR_OWNER);
        TicketViewModel item = new TicketViewModel(
                rs.getInt(ID),
                rs.getString(CAR_OWNER),
                rs.getString(CAR),
                rs.getString(MECHANIC),
                rs.getString(DEFECT),
                rs.getString(STATUS),
                rs.getDate(INCOME_DATE)
        );
        return item;
    }

}
