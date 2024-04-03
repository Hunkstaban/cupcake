package app.persistence;

import app.entities.Base;
import app.entities.OrderDetail;
import app.entities.Topping;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.List;

public class OrderDetailMapper {


    public static void addToCart(int baseID, int toppingID, int amount, User user, ConnectionPool connectionPool) {

        try {
            Base base = CupcakeMapper.getBaseByID(baseID, connectionPool);
            Topping topping = CupcakeMapper.getToppingByID(toppingID, connectionPool);
            String baseName = base.getBaseName();
            String toppingName = topping.getToppingName();
            int totalPrice = (topping.getToppingPrice() + base.getBasePrice()) * amount;

            user.addToCart(baseID, toppingID, baseName, toppingName, amount, totalPrice);


        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }


    }

    public static int newOrder(User user, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id) VALUES (?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, user.getUserID());
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected != 1) {
                throw new DatabaseException("Fejl i opdatering af en bestilling");
            }

            // Retrieve the auto-generated keys
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                // Retrieve the generated order_id
                int orderId = generatedKeys.getInt(1);
                return orderId;
            } else {
                throw new DatabaseException("Fejl i opdatering af en task - kunne ikke hente det autogenererede order_id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertOrderDetails(User user, int orderID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "INSERT INTO order_details (base_id, toppping_id, amount, total_price, order_id) VALUES (?,?,?,?,?)";
        List<OrderDetail> userCart = user.getCartList();

        for (OrderDetail orderDetail : userCart) {
            try (
                    Connection connection = connectionPool.getConnection();
                    PreparedStatement ps = connection.prepareStatement(sql)
            ) {
                ps.setInt(1, orderDetail.getBaseID());
                ps.setInt(2, orderDetail.getToppingID());
                ps.setInt(3, orderDetail.getAmount());
                ps.setInt(4, orderDetail.getTotalPrice());
                ps.setInt(5, orderID);

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected != 1) {
                    throw new DatabaseException("Fejl i opdatering af bestillingsoplysninger");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
