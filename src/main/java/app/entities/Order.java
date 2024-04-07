package app.entities;

import java.time.LocalDateTime;

public class Order {
    private int orderID;
    private int userID;
    private String email;
    private String baseName;
    private String toppingName;
    private int amount;
    private int totalPrice;
    private String date;

    public Order(int orderID, String email, String baseName, String toppingName, int amount, int totalPrice, String date) {
        this.orderID = orderID;
        this.email = email;
        this.baseName = baseName;
        this.toppingName = toppingName;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.date = date;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getToppingName() {
        return toppingName;
    }

    public int getAmount() {
        return amount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getDate() {
        return date;
    }

    public Order(int orderID, int userID, String date) {
        this.orderID = orderID;
        this.userID = userID;
        this.date = date;
    }
    //    public Order(int userID) {
//        this.userID = userID;
//    }

}
