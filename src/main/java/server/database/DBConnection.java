package server.database;

import server.models.Item;
import server.models.Order;
import server.models.Token;
import server.models.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import server.config.Config;
import server.utility.Globals;


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

        Config config = new Config();
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
            Globals.log.writeLog(getClass().getName(), this, "Error connection refused with hostname: " + config.getDatabaseHost(), 2);
            Globals.log.writeLog(getClass().getName(), this, "and databasename: " + config.getDatabaseName(), 2);
            Globals.log.writeLog(getClass().getName(), this, "and port number: " + config.getDatabasePort(), 2);
            e.printStackTrace();
        }

    }

    /**
     * Method responsible for terminating the database connection.
     */
    private static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method reponsible for adding a user to the database.
     *
     * @param user parameter is inserted into the database using PreparedStatements.
     * @return returns whether or not the user was added to the database by using "rowsAffected".
     */
    public boolean addUser(User user) {
        try {
            PreparedStatement addUser = connection.prepareStatement("INSERT INTO Users (username, password) VALUES (?, ?)");

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

    /**
     * Method is responsible for retrieving all orders from the database using PreparedStatements.
     *
     * @return Returns an ArrayList of Orders from the database.
     */
    public ArrayList<Order> getOrders() {

        /**
         * Creates ResultSet to filter through all Orders.
         * Creates ArrayList which will ultimately be the end product.
         */
        ResultSet resultSet = null;
        ArrayList<Order> orders = new ArrayList<>();

        try {
            PreparedStatement getOrders = connection.prepareStatement(
                    "SELECT o.order_id,o.orderTime,o.isReady,o.user_userid, i.item_id, i.ItemName, i.itemDescription, i.itemPrice FROM ((Orders o\n" +
                            "INNER JOIN Order_has_Items oi ON o.order_id = oi.Orders_orderId)\n" +
                            "INNER JOIN Items i ON i.item_id = oi.Items_itemId)");

            resultSet = getOrders.executeQuery();

            /**
             * While loop that uses resultSet.next to go through each individual Order and item and add item to order ArrayLists and order to orders ArrayList.
             */
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setOrderTime(resultSet.getTimestamp("orderTime"));
                if (resultSet.getInt("isReady") != 1) {
                    order.setIsReady(false);
                } else {
                    order.setIsReady(true);
                }

                order.setUser_userId(resultSet.getInt("user_userid"));

                Item item = new Item();
                item.setItemId(resultSet.getInt("item_id"));
                item.setItemName(resultSet.getString("itemName"));
                item.setItemDescription(resultSet.getString("itemDescription"));
                item.setItemPrice(resultSet.getInt("itemPrice"));

                Boolean addToOrders = true;
                if (orders.isEmpty()) {
                    order.setItems(item);
                } else {

                    for (int i = 0; i <= orders.size(); i++) {
                        if (order.getOrderId() == orders.get(i).getOrderId()) {
                            orders.get(i).setItems(item);
                            addToOrders = false;
                            break;
                        } else {
                            order.setItems(item);
                            break;
                        }
                    }
                }
                if (addToOrders)
                    orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Methods responsible for retrieving all items from the database and adding them to an ArrayList.
     *
     * @return Returns an ArrayList of items from the database.
     */
    public ArrayList<Item> getItems() {

        ResultSet resultSet = null;
        ArrayList<Item> items = new ArrayList<>();

        /**
         * Tries to access the items database using a PreparedStatement.
         * Uses .excecuteQuery() to go through each item.
         */
        try {
            PreparedStatement getItems = connection.prepareStatement("SELECT * FROM Items");
            resultSet = getItems.executeQuery();

            /**
             * While loop that uses a resultSet to go through each item and add it to the ArrayList.
             */
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

    /**
     * Method used to find orders from individual users using ID as a primary key.
     *
     * @param userId Parameter used to determine which users orders are to be retrieved.
     * @return Returns an ArrayList of orders from the specified user.
     */
    public ArrayList<Order> findOrderById(int userId) {

        /**
         * Creates ResultSet to filter through all Orders.
         * Creates ArrayList which will ultimately be the end product.
         */
        ResultSet resultSet = null;
        ArrayList<Order> orders = new ArrayList<>();

        try {
            PreparedStatement findOrderById = connection.prepareStatement(
                    "SELECT o.order_id,o.orderTime,o.isReady,o.user_userid, i.item_id, i.ItemName, i.itemDescription, i.itemPrice FROM ((Orders o\n" +
                            "INNER JOIN Order_has_Items oi ON o.order_id = oi.Orders_orderId)\n" +
                            "INNER JOIN Items i ON i.item_id = oi.Items_itemId)" +
                            "WHERE o.user_userid = ?");

            findOrderById.setInt(1, userId);
            resultSet = findOrderById.executeQuery();

            /**
             * While loop that uses resultSet.next to go through each individual Order and item and add item to order ArrayLists and order to orders ArrayList.
             */
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("order_id"));
                order.setOrderTime(resultSet.getTimestamp("orderTime"));
                if (resultSet.getInt("isReady") != 1) {
                    order.setIsReady(false);
                } else {
                    order.setIsReady(true);
                }
                order.setUser_userId(resultSet.getInt("user_userid"));

                Item item = new Item();
                item.setItemId(resultSet.getInt("item_id"));
                item.setItemName(resultSet.getString("itemName"));
                item.setItemDescription(resultSet.getString("itemDescription"));
                item.setItemPrice(resultSet.getInt("itemPrice"));

                Boolean addToOrders = true;
                if (orders.isEmpty()) {
                    order.setItems(item);
                } else {

                    for (int i = 0; i <= orders.size(); i++) {
                        if (order.getOrderId() == orders.get(i).getOrderId()) {
                            orders.get(i).setItems(item);
                            addToOrders = false;
                            break;
                        } else {
                            order.setItems(item);
                            break;
                        }
                    }
                }
                if (addToOrders)
                    orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Method responsible for adding a new order to the database.
     *
     * @param userId Parameter determining which user issued the specific order.
     * @param items  Parameter determining which item was ordered.
     * @return Returns whether or not the order was added to the database.
     */
    public boolean addOrder(int userId, ArrayList<Item> items) {
        Timestamp orderTimestamp = new Timestamp(System.currentTimeMillis());
        try {
            PreparedStatement addOrder = connection.prepareStatement("INSERT INTO Orders (user_userid, orderTime) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            addOrder.setInt(1, userId);
            addOrder.setTimestamp(2, orderTimestamp);
            addOrder.executeUpdate();
            ResultSet rs = addOrder.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            //Missing comment

            PreparedStatement addItemsToOrder;
            for (int i = 0; i < items.size(); i++) {
                addItemsToOrder = connection.prepareStatement("INSERT INTO Order_has_Items (Orders_orderId, Items_itemId) VALUES (?, ?)");
                addItemsToOrder.setInt(1, orderId);
                addItemsToOrder.setInt(2, items.get(i).getItemId());
                addItemsToOrder.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method responsible for authorizing a user logging by checking if a user with the given Username & Password exists.
     *
     * @param controlUser User object containing the given username & password.
     * @return Returns the user object if found in the database.
     */
    public User authorizeUser(User controlUser) {
        ResultSet resultSet = null;
        User newUser = null;

        /**
         * Using PreparedStatement to check if any user has the given username & password.
         */
        try {
            PreparedStatement authorizeUser = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?");

            authorizeUser.setString(1, controlUser.getUsername());
            authorizeUser.setString(2, controlUser.getPassword());

            resultSet = authorizeUser.executeQuery();

            //Mangler kommentar?
            while (resultSet.next()) {
                newUser = new User();
                newUser.setUserId(resultSet.getInt("user_id"));
                newUser.setUsername(resultSet.getString("username"));
                newUser.setPassword(resultSet.getString("password"));
                if (resultSet.getInt("isPersonel") == 1) {
                    newUser.setPersonel(true);
                } else {
                    newUser.setPersonel(false);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return newUser;
    }

    /**
     * Method used to change the status of an Order to ready from not ready.
     *
     * @param orderId Parameter specifying which order is to be made ready.
     * @return Returns whether the task was completed or not.
     */
    public boolean makeReady(int orderId) {

        /**
         * Uses PreparedStatement to update a specific order in the database using the given ID and setting isReady to 1 (true).
         */
        try {
            PreparedStatement makeReady = connection.prepareStatement("UPDATE Orders SET isReady = 1 WHERE order_id = ?");
            makeReady.setInt(1, orderId);
            int rowsAffected = makeReady.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String createToken(User user, String token) {
        try {
            PreparedStatement createToken = connection.prepareStatement("INSERT INTO Token (tokenString, Users_user_id) VALUES (?, ?)");
            createToken.setString(1, token);
            createToken.setInt(2, user.getUserId());
            int rowsAffected = createToken.executeUpdate();
            if (rowsAffected == 1) {
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Return empty
        return "";
    }

    public boolean deleteToken(int id) {
        try {
            PreparedStatement deleteToken = connection.prepareStatement("DELETE FROM Token WHERE Users_userId = ?");
            deleteToken.setInt(1, id);
            int rowsAffected = deleteToken.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean tokenExists(String token) {
        ResultSet rs;
        String serverToken = "";
        try {
            PreparedStatement tokenExists = connection.prepareStatement("SELECT * FROM Token WHERE tokenString = ?");
            tokenExists.setString(1, token);
            rs = tokenExists.executeQuery();

            Boolean check = rs.next();

            return check;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
