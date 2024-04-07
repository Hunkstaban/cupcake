package app.entities;

import java.time.LocalDateTime;

public class Order {
    private int orderID;
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
//    public Order(int userID) {
//        this.userID = userID;
//    }

}
