package sample.analytics;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MechanicWorkPrice {
    public static final String NAME = "name";
    public static final String WORK_PRICE = "work_price";

    private String name;
    private String work_price;

    public MechanicWorkPrice(
            String name,
            String work_price
    ) {
        this.name = name;
        this.work_price = work_price;
    }

    public static MechanicWorkPrice getItemResultSet(ResultSet rs) throws SQLException {
        MechanicWorkPrice item = new MechanicWorkPrice(
                rs.getString(NAME),
                rs.getString(WORK_PRICE)
        );
        return item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWork_price() {
        return work_price;
    }

    public void setWork_price(String work_price) {
        this.work_price = work_price;
    }

    public static String getSQL() {
        return "SELECT m.name, SUM(price) \"work_price\" FROM \n" +
                "mechanic m\n" +
                "LEFT JOIN ticket t ON m.`id`=t.`mechanic_id` \n" +
                "GROUP BY m.name ORDER BY work_price DESC";
    }
}
