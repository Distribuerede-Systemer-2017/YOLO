package server.database;

import server.models.Item;
import server.models.Order;
import server.models.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import server.config.Config;

/**
 * Class responsible for establishing connection between the database and the server
 */

public class DBConnection {

    private static Connection connection = null;

    /**
     * Attempts to create the connection to database
     * Gets variables from config file
     */
    public DBConnection() {

        Config config= new Config();
        try {
            config.initConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            try {
                //Calls a new instance of the Class com.mysql.jdbc.Driver.class with no parameters
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            connection = DriverManager.getConnection(("jdbc:mysql://" + config.getDatabaseHost() + ":"
                            + config.getDatabasePort() + "/" + config.getDatabaseName()),
                    config.getDatabaseUser(), config.getDatabasePassword());

        } catch (SQLException e) {
            System.out.println(config.getDatabaseHost());
            System.out.println(config.getDatabaseName());
            System.out.println(config.getDatabasePort());
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

    public boolean addUser(User user) {
        try {
            PreparedStatement addUser = connection.prepareStatement("INSERT into Users (username, password, email) VALUES (?, ?, ?)");

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

    public ArrayList<Order> getOrders() {

        ResultSet resultSet = null;
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Item> itemsInOrder = new ArrayList<>();

        try {
            PreparedStatement getOrders = connection.prepareStatement("SELECT Order.order_id, Items.item_id, Items.ItemName from ((Orders " +
                                                                "INNER JOIN Order_has_Items ON Orders.order_id = Order_has_Items.Orders_orderId)" +
                                                                "INNER JOIN Items ON Items.item_id = Order_has_Items.Items_itemId)");

            resultSet = getOrders.executeQuery();

            //????
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setOrderTime(resultSet.getTimestamp("orderTime"));
                if(resultSet.getInt("isReady") != 1){order.isReady(false);} else {order.isReady(true);}
                order.setUser_userId(resultSet.getInt("user_userid"));


                orders.add(order);
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
        return orders;
    }

    public ArrayList<Item> getItems() {

            ResultSet resultSet = null;
            ArrayList<Item> items = new ArrayList<>();

            try {
                PreparedStatement getItems = connection.prepareStatement("SELECT * FROM Items");
                resultSet = getItems.executeQuery();

                while (resultSet.next()) {
                    Item item = new Item();
                    item.setItemId(resultSet.getInt("item_id"));
                    item.setItemName(resultSet.getString("itemName"));
                    item.setItemDescription(resultSet.getString("itemDescription"));
                    item.setItemPrice(resultSet.getInt("itemPrice"));

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

    public ArrayList<Order> findOrderById(int userId) {
        ResultSet resultset = null;
        ArrayList<Order> orders = null;

        try{
            PreparedStatement findOrderById = connection.prepareStatement("SELECT * FROM Orders WHERE user_userid = ?");
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

    public boolean addOrder(int userId, ArrayList<Item> items){

        try{
            PreparedStatement addOrder = connection.prepareStatement("INSERT into Orders (userId) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            addOrder.setInt(1, userId);
            addOrder.executeUpdate();
            ResultSet rs = addOrder.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);


            PreparedStatement addItemsToOrder;
            for (int i = 0; i < items.size(); i++) {
                addItemsToOrder = connection.prepareStatement("INSERT into order_has_items (Order_orderId, Items_itemsId) VALUES (?, ?)");
                addItemsToOrder.setInt(1, orderId);
                addItemsToOrder.setInt(2, items.get(i).getItemId());
                addItemsToOrder.executeUpdate();
            }
            return true;

        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public User authorizeUser(String username, String password){
        ResultSet resultSet = null;
        User user = null;

        try {
            PreparedStatement authorizeUser = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");

            authorizeUser.setString(1, username);
            authorizeUser.setString(2, password);

            resultSet = authorizeUser.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setPersonel(resultSet.getBoolean("isPersonel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
