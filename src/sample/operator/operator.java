package sample.operator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.util.Callback;
import sample.Config;

public class operator extends Config {

    @FXML
    private Tab carNavTab;

    @FXML
    private TableView<ObservableList> carOwnerTable;

    @FXML
    void initialize () throws SQLException, ClassNotFoundException {
        //TABLE VIEW AND DATA
        getDbConnection();
        buildData();
    }

    private ObservableList<ObservableList> data;

    Connection dbConnection;

    private void getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
    }

    //CONNECTION DATABASE
    public void buildData(){
        Connection c ;
        data = FXCollections.observableArrayList();
        try{
            c = dbConnection;
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from car_owner";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(
                        (Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>) param ->{
                            if (param.getValue().get(j) == null) {
                                return new SimpleStringProperty("");
                            }
                            carOwnerTable.refresh();
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                );
                carOwnerTable.getColumns().addAll(col);
                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            carOwnerTable.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
}
