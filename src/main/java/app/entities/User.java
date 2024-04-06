package app.entities;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userID;
    private String email;
    private String password;
    private int roleID;
    private int balance;
    private List<OrderDetail> cartList = new ArrayList<>();

    public User(int userID, String email, String password, int roleID, int balance) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
        this.balance = balance;
    }

    public User(int userID) {
        this.userID = userID;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // potentially needed
    public User(String email, String password, int roleID) {
        this.email = email;
        this.password = password;
        this.roleID = roleID;
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleID() {
        return roleID;
    }

    public List<OrderDetail> getCartList() {
        return cartList;
    }

    public void addToCart(int baseID, int toppingID, String baseName, String toppingName, int amount, int totalPrice) {
        cartList.add(new OrderDetail(baseID, toppingID, baseName, toppingName, amount, totalPrice));
    }

    public void removeFromCart(int index) {

        cartList.remove(index);
    }
}
