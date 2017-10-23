package server.models;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Hanna og 17.10.17
 */

public class Order {

    private int orderId;
    private Timestamp orderTime;
    private boolean isReady;
    private int User_userId;
    private ArrayList<Item> items;

    public Order() {
        this.items = new ArrayList<>();
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setIsReady(boolean status) {
        this.isReady = status;
    }

    public int getUser_userId() {
        return User_userId;
    }

    public void setUser_userId(int user_userId) {
        User_userId = user_userId;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(Item item) {
        items.add(item);
    }

}
