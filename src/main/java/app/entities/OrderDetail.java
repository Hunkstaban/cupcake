package app.entities;

public class OrderDetail {

    private int orderDetailID;
    private int baseID;
    private int toppingID;
    private int amount;
    private int price;
    private int orderID;

    public OrderDetail(int baseID, int toppingID, int amount, int price, int orderID) {
        this.baseID = baseID;
        this.toppingID = toppingID;
        this.amount = amount;
        this.price = price;
        this.orderID = orderID;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public int getBaseID() {
        return baseID;
    }

    public int getToppingID() {
        return toppingID;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public int getOrderID() {
        return orderID;
    }
}
