package app.entities;

public class Cupcake {
    private int baseID;
    private int toppingID;
    private String baseName;
    private String toppingName;
    private int price;

    public Cupcake(int baseID, int toppingID, String baseName, String toppingName, int price) {
        this.baseID = baseID;
        this.toppingID = toppingID;
        this.baseName = baseName;
        this.toppingName = toppingName;
        this.price = price;
    }

    public Cupcake(String baseName, String toppingName, int price) {
        this.baseName = baseName;
        this.toppingName = toppingName;
        this.price = price;
    }

    public int getBaseID() {
        return baseID;
    }

    public int getToppingID() {
        return toppingID;
    }

    public String getBaseName() {
        return baseName;
    }

    public String getToppingName() {
        return toppingName;
    }

    public int getPrice() {
        return price;
    }
}
