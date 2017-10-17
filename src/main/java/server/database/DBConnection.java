package server.database;

import server.models.Item;
import server.models.Order;
import server.models.User;

import java.sql.*;
import java.util.ArrayList;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Begin SQL statements

    public boolean addUser(User user) throws Exception {
        try {
            PreparedStatement addUser = connection.prepareStatement("INSERT into users (username, password, email) VALUES (?, ?, ?)");

            addUser.setString(1, user.getUsername());
            addUser.setString(2, user.getPassword());
            addUser.setString(3, user.getEmail());

            int rowsAffected = addUser.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<Item> getItems() {

            ResultSet resultSet = null;
            ArrayList<Item> items = new ArrayList<>();

            try {
                PreparedStatement getItems = connection.prepareStatement("SELECT * FROM items");
                resultSet = getItems.executeQuery();

                while (resultSet.next()) {
                    Item item = new Item();
                    item.setItemId(resultSet.getInt("id"));
                    item.setItemName(resultSet.getString("name"));
                    item.setItemDescription(resultSet.getString("description"));
                    item.setItemPrice(resultSet.getInt("price"));

                    items.add(item);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    resultSet.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    close();
                }
            }
            return items;
        }

    public ArrayList<Order> findOrderById(int userId) throws Exception{
        ResultSet resultset = null;
        ArrayList<Order> orders = null;

        try{
            PreparedStatement findOrderById = connection.prepareStatement("SELECT * FROM orders WHERE user_userid = ?");
            findOrderById.setInt(1, userId);

            resultset = findOrderById.executeQuery();
            while(resultset.next()){
                Order order = new Order();
                order.setOrderId(resultset.getInt("order_id"));
                order.setOrderTime(resultset.getTimestamp("orderTime"));
                if(resultset.getInt("isReady") != 1){order.isReady(false);} else {order.isReady(true);}
                order.setUser_userId(resultset.getInt("user_userid"));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
