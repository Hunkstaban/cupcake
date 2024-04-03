package app.entities;

public class User {

    private int userID;
    private String email;
    private String password;
    private int roleID;

    public User(int userID, String email, String password, int roleID) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.roleID = roleID;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleID() {
        return roleID;
    }
}
