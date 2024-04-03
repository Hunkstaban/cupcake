package app.entities;

public class Topping {

    private int toppingID;
    private String toppingName;
    private int toppingPrice;

    public Topping(int toppingID, String toppingName, int toppingPrice) {
        this.toppingID = toppingID;
        this.toppingName = toppingName;
        this.toppingPrice = toppingPrice;
    }

    public int getToppingID() {
        return toppingID;
    }

    public String getToppingName() {
        return toppingName;
    }

    public int getToppingPrice() {
        return toppingPrice;
    }
}
