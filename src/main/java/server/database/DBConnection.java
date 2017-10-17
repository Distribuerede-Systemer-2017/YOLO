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

    public ArrayList<Order> getOrders() {

        ResultSet resultSet = null;
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Item> itemsInOrder = new ArrayList<>();

        try {
            PreparedStatement getOrders = connection.prepareStatement("SELECT Order.order_id, Items.item_id, Items.ItemName from ((Orders " +
                                                                "INNER JOIN Order_has_Items ON Orders.order_id = Order_has_Items.Orders_orderId)" +
                                                                "INNER JOIN Items ON Items.item_id = Order_has_Items.Items_itemId)");

            resultSet = getOrders.executeQuery();

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

    public boolean addOrder(User user, ArrayList<Item> items) throws Exception{

        try{
            PreparedStatement addOrder = connection.prepareStatement("INSERT into orders (userId) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            addOrder.setInt(1, user.getUserId());
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

    public User authorizeUser(String username, String password) throws Exception {
        ResultSet resultSet = null;
        User user = null;

        try {
            PreparedStatement authorizeUser = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");

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
