package server.database;

import server.models.Order;
import server.models.User;

import java.sql.*;

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

    //Begin SQL statements

    public boolean addUser(User user) throws Exception{
        try{
            PreparedStatement addUser = connection.prepareStatement("INSERT into users (username, password, email) VALUES (?, ?, ?)");

            addUser.setString(1, user.getUsername());
            addUser.setString(2, user.getPassword());
            addUser.setString(3, user.getEmail());

            int rowsAffected = addUser.executeUpdate();
            if(rowsAffected == 1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addOrder(Order order, User user) throws Exception{
        try{
            PreparedStatement addOrder = connection.prepareStatement("INSERT into orders (orderTime, userId) VALUES (?, ?)");
            Date date = order.getOrderTime();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
