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

                return new User(userId, email, password, roleId);
            } else throw new DatabaseException("fejl i login. check din syntax");

        } catch (SQLException e) {
            throw new DatabaseException("noget gik galt med databasen " + e.getMessage());
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


}// ---------------------- end class ------------------------------------
