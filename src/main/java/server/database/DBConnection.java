package server.database;

import server.models.Item;
import server.models.Order;
import server.models.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import server.config.Config;
import server.utility.Globals;
import server.utility.Log;


/**
 * Class responsible for establishing connection between the database and the server
 */

public class DBConnection {

    private static Connection connection = null;
    private static Log log = new Log();

    /**
     * Attempts to create the connection to database
     * Gets variables from config file
     */
    public DBConnection() {
        log.writeLog("DB", this.getClass(), "WORKS", 2);

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

            connection = DriverManager.getConnection(("jdbc:mysql://" + Globals.config.getDatabaseHost() + ":"
                            + Globals.config.getDatabasePort() + "/" + Globals.config.getDatabaseName()),
                    Globals.config.getDatabaseUser(), Globals.config.getDatabasePassword());

        } catch (SQLException e) {
            System.out.println(Globals.config.getDatabaseHost());
            System.out.println(Globals.config.getDatabaseName());
            System.out.println(Globals.config.getDatabasePort());
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
     * @param user parameter is inserted into the database using PreparedStatements.
     * @return returns whether or not the user was added to the database by using "rowsAffected".
     */
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

    /**
     * Method is responsible for retrieving all orders from the database using PreparedStatements.
     * @return Returns an ArrayList of Orders from the database.
     */
    public ArrayList<Order> getOrders() {

        /**
         * Creates ResultSet to filter through all Orders.
         * Creates ArrayList which will ultimately be the end product.
         */
        ResultSet resultSet = null;
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Item> itemsInOrder = new ArrayList<>();

        try {
            PreparedStatement getOrders = connection.prepareStatement("SELECT Order.order_id, Items.item_id, Items.ItemName from ((Orders " +
                                                                "INNER JOIN Order_has_Items ON Orders.order_id = Order_has_Items.Orders_orderId)" +
                                                                "INNER JOIN Items ON Items.item_id = Order_has_Items.Items_itemId)");

            resultSet = getOrders.executeQuery();

            /**
             * While loop that uses resultSet.next to go through each individual Order and add them to the ArrayList.
             */
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

    /**
     * Methods responsible for retrieving all items from the database and adding them to an ArrayList.
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
     * @param userId Parameter used to determine which users orders are to be retrieved.
     * @return Returns an ArrayList of orders from the specified user.
     */
    public ArrayList<Order> findOrderById(int userId) {
        ResultSet resultset = null;
        ArrayList<Order> orders = null;

        /**
         * Uses PreparedStatement to access the "Orders" database and only selecting the orders where the userid = the parameter.
         */
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

    /**
     * Method responsible for adding a new order to the database.
     * @param userId Parameter determining which user issued the specific order.
     * @param items Parameter determining which item was ordered.
     * @return Returns whether or not the order was added to the database.
     */
    public boolean addOrder(int userId, ArrayList<Item> items){
<<<<<<< HEAD

        //Missing comment
=======
        Timestamp orderTimestamp = new Timestamp(System.currentTimeMillis());
>>>>>>> origin/norwegians
        try{
            PreparedStatement addOrder = connection.prepareStatement("INSERT into Orders (user_userid, orderTime) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            addOrder.setInt(1, userId);
            addOrder.setTimestamp(2, orderTimestamp);
            addOrder.executeUpdate();
            ResultSet rs = addOrder.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            //Missing comment

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

    /**
     * Method responsible for authorizing a user logging by checking if a user with the given Username & Password exists.
     * @param controlUser User object containing the given username & password.
     * @return Returns the user object if found in the database.
     */
    public User authorizeUser(User controlUser){
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

    /**
     * Method used to change the status of an Order to ready from not ready.
     * @param orderId Parameter specifying which order is to be made ready.
     * @return Returns whether the task was completed or not.
     */
    public boolean makeReady(int orderId){

        /**
         * Uses PreparedStatement to update a specific order in the database using the given ID and setting isReady to 1 (true).
         */
        try{
            PreparedStatement makeReady = connection.prepareStatement("UPDATE Orders SET isReady = 1 WHERE order_id = ?");
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
}
