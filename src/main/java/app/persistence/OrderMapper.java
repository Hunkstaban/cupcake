package app.persistence;

import app.entities.Order;
import app.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static List<Order> viewAllOrders(User user, ConnectionPool connectionPool) {
        String sql = "SELECT * FROM public.view_all_orders";
        List<Order> orders = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("order_id");
                String email = rs.getString("email");
                String baseName = rs.getString("base_name");
                String toppingName = rs.getString("topping_name");
                int amount = rs.getInt("amount");
                int totalPrice = rs.getInt("total_price");
                String date = rs.getString("date");

                orders.add(new Order(orderID, email, baseName, toppingName, amount, totalPrice, date));
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}