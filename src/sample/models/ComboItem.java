package sample.models;

import javafx.util.StringConverter;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComboItem {
    private int id;
    private String name;

    public static final String ID = "id";
    public static final String NAME = "name";

    public ComboItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() { return id; }
    public String getName() { return name; }

    public static StringConverter converter = new StringConverter<ComboItem>() {
        @Override
        public String toString(ComboItem object) {
            return object.getName();
        }
        @Override
        public ComboItem fromString(String string) {
            return null;
        }
    };

    public static ComboItem getItemFromResultSet(ResultSet rs) throws SQLException {
        ComboItem item = new ComboItem(
                rs.getInt(ID),
                rs.getString(NAME)
        );
        return item;
    }
}
