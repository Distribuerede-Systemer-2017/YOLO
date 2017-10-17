package server.database;

import com.sun.tools.javac.jvm.Items;
import server.models.Item;
import server.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Class responsible for establishing connection between the database and the server
 */

public class DBConnection {

    private static Connection connection = null;

    /**
     * Attempts to create the connection
     */
    public DBConnection() {
        try {
            connection = DriverManager.getConnection(System.getenv("jdbc:mysql://" + "DATABASE_HOST" + ":" + "DATABASE_PORT" + "/" + System.getenv("DATABASE_NAME")),
                    System.getenv("DATABASE_USER"), System.getenv("DATABASE_PASSWORD"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void close() {
        try {
            connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(){

    }

    public boolean addOrder(){

    }

    public void findOrderById(int id){
    }

    public ArrayList<Item> getItems(){

        ArrayList<Item> items =  new ArrayList<>();

        Map<String,String> where = new Map<String, String>;

        ResultSet resultSet = getRecords("items", where);

        //Build arraylist fro resultSet

        try {
            while (resultSet.next()) {
                Item item = new Item();

                item.setItemId();
                item.setItemName();
                item.setItemPrice();
                item.setItemDescription();

                items.add(item);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet getRecords(String tablename, Map<String,String> where){

        String wherestatement = "WHERE key = value AND key2 = value2" ;

        //Go through where variable and add it to string wherestatement


        PreparedStatement sql = null;
        try {
            sql = connection.prepareStatement("SELECT * FROM ? " + wherestatement);
            sql.setString(1, tablename);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet resultSet = sql.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    public void findUserByLogin(String username, String password){


    }

    //Begin SQL statements
    
}
