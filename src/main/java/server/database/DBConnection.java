package server.database;

import server.models.Item;
import server.models.Order;
import server.models.Token;
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
            PreparedStatement addUser = connection.prepareStatement("INSERT into Users (username, password) VALUES (?, ?)");

            addUser.setString(1, user.getUsername());
            addUser.setString(2, user.getPassword());

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
                if(resultSet.getInt("isReady") != 1) {
                    order.setIsReady(false);
                } else {
                    order.setIsReady(true);
                }
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
                if(resultset.getInt("isReady") != 1){
                    order.setIsReady(false);
                } else {
                    order.setIsReady(true);
                }
                order.setUser_userId(resultset.getInt("user_userid"));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean addOrder(int userId, ArrayList<Item> items){
        Timestamp orderTimestamp = new Timestamp(System.currentTimeMillis());
        try{
            PreparedStatement addOrder = connection.prepareStatement("INSERT into Orders (user_userid, orderTime) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            addOrder.setInt(1, userId);
            addOrder.setTimestamp(2, orderTimestamp);
            addOrder.executeUpdate();
            ResultSet rs = addOrder.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);


            PreparedStatement addItemsToOrder;
            for (int i = 0; i < items.size(); i++) {
                addItemsToOrder = connection.prepareStatement("INSERT into Order_has_Items (Orders_orderId, Items_itemId) VALUES (?, ?)");
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

    public User authorizeUser(User controlUser){
        ResultSet resultSet = null;
        User newUser = null;
        try {
            PreparedStatement authorizeUser = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");

            authorizeUser.setString(1, controlUser.getUsername());
            authorizeUser.setString(2, controlUser.getPassword());

            resultSet = authorizeUser.executeQuery();

            while (resultSet.next()) {
                newUser = new User();
                newUser.setUserId(resultSet.getInt("user_id"));
                newUser.setUsername(resultSet.getString("username"));
                newUser.setPassword(resultSet.getString("password"));
                newUser.setPersonel(resultSet.getBoolean("isPersonel"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                resultSet.close();
            } catch (SQLException se){
                se.printStackTrace();
            }
        }
        return newUser;
    }

    public boolean makeReady(int orderId){
        try{
            PreparedStatement makeReady = connection.prepareStatement("UPDATE order SET isReady = 1 WHERE orderID = ?");
            makeReady.setInt(1, orderId);
            int rowsAffected = makeReady.executeUpdate();

            if(rowsAffected == 1){
                return true;
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String createToken(User user, String token){
        try{
            PreparedStatement createToken = connection.prepareStatement("INSERT into Token (tokenString, Token_userId) VALUES (?, ?)");
            createToken.setString(1, token);
            createToken.setInt(3, user.getUserId());
            int rowsAffected = createToken.executeUpdate();
            if (rowsAffected == 1){
                return token;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteToken(int id){
        try{
            PreparedStatement deleteToken = connection.prepareStatement("DELETE * FROM Token WHERE Users_userId = ?");
            deleteToken.setInt(1, id);
            int rowsAffected = deleteToken.executeUpdate();
            if (rowsAffected > 0){
                return true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public Token tokenExists(String token){
        ResultSet rs;
        Token foundToken = null;
        try{
            PreparedStatement tokenExists = connection.prepareStatement("SELECT * FROM Token WHERE tokenString = ?");
            tokenExists.setString(1, token);
            rs = tokenExists.executeQuery();
            rs.next();
            foundToken.setUserId(rs.getInt("Users_user_id"));
            foundToken.setTokenDate(rs.getDate("tokenDate"));
            foundToken.setTokenId(rs.getInt("tokenId"));
            foundToken.setTokenString(rs.getString("tokenString"));
            return foundToken;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return foundToken;
    }
}
