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

    public static List<Order> viewAllOrders(ConnectionPool connectionPool) {
        String sql = "SELECT * FROM public.view_all_orders";
        List<Order> orderList = new ArrayList<>();

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

                orderList.add(new Order(orderID, email, baseName, toppingName, amount, totalPrice, date));
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Order> viewAllOrdersTest(ConnectionPool connectionPool) {
        String sql = "SELECT * FROM orders";
        List<Order> orderList = new ArrayList<>();

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderID = rs.getInt("order_id");
                int userID = rs.getInt("user_id");
                String date = rs.getString("date");

                orderList.add(new Order(orderID, userID, date));
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}