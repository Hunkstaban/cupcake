package app.entities;

import java.time.LocalDateTime;

public class Order {
    private int orderID;
    private int userID;
    private LocalDateTime date;

    public Order(int userID) {
        this.userID = userID;
    }

}
