package server.models;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.util.ArrayList;

/**
 * Created by Hanna og 17.10.17
 */

public class Order {

    private int orderId;
    private DateTimeAtCreation orderTime;
    private boolean isReady;  //Skal det gjøres noe med denne?
    private int User_userId;
    private ArrayList<Order> orders;

    public Order (int orderId, DateTimeAtCreation orderTime, boolean isReady, int User_userId){
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.isReady = isReady;
        this.User_userId = User_userId;

        this.orders = new ArrayList<Order>();

    }

     public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public DateTimeAtCreation getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(DateTimeAtCreation orderTime) {
        this.orderTime = orderTime;
    }
    public boolean isReady() {
        return isReady;
    }

    public void setisReady(boolean isReady) {
        this.isReady = isReady;
    }

    public int getUser_userId() {
        return User_userId;
    }

    public void setUser_userId(int user_userId) {
        User_userId = user_userId;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

}
