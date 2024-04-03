package app.entities;

public class Base {

    private int baseID;
    private String baseName;
    private int basePrice;

    public Base(int baseId, String baseName, int basePrice) {
        this.baseID = baseId;
        this.baseName = baseName;
        this.basePrice = basePrice;
    }

    public int getBaseID() {
        return baseID;
    }

    public String getBaseName() {
        return baseName;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
