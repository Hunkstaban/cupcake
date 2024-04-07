package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {

    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException {


        String sql = "SELECT * FROM users WHERE email=? AND password=?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);


            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                int roleId = resultSet.getInt("role_id");
                int balance = resultSet.getInt("balance");

                return new User(userId, email, password, roleId, balance);
            } else throw new DatabaseException("Error getting User from database");

        } catch (SQLException e) {
            throw new DatabaseException("DB error: " + e.getMessage());
        }


    }


    public static User createUser(String email, String password, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO users (email,password) VALUES(?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            preparedStatement.executeUpdate();

            return login(email, password, connectionPool);

        } catch (SQLException e) {

            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "email findes allerede. Prøv igen";
            }
            throw new DatabaseException(msg, e.getMessage());
        }


    }

    public static void updateBalance(User user, int orderTotalPrice, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "UPDATE users SET balance = ? WHERE user_id = ?";

        int currentBalance = user.getBalance();
        if (currentBalance < orderTotalPrice) {
            throw new DatabaseException("Insufficient funds to complete transaction");
        } else {
            int newBalance = currentBalance - orderTotalPrice;
            user.setBalance(newBalance);
            try (
                    Connection connection = connectionPool.getConnection();
                    PreparedStatement ps = connection.prepareStatement(sql)
            ) {
                ps.setInt(1, newBalance);
                ps.setInt(2, user.getUserID());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected != 1) {
                    throw new DatabaseException("Error updating balance for user " + user.getUserID());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }








    }

}// ---------------------- end class ------------------------------------
