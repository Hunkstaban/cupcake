package app.persistence;

import app.entities.Topping;
import app.entities.Base;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CupcakeMapper {


    public static Topping getToppingByID(int toppingID, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "SELECT * FROM topping WHERE topping_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String toppingName = rs.getString("topping_name");
                int toppingPrice = rs.getInt("topping_price");
                return new Topping(toppingID, toppingName, toppingPrice);
            } else {
                throw new DatabaseException("Error topping not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static Base getBaseByID(int baseID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM base WHERE base_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String baseName = rs.getString("base_name");
                int basePrice = rs.getInt("base_price");
                return new Base(baseID, baseName, basePrice);
            } else {
                throw new DatabaseException("Error topping not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Topping> getAllToppings(ConnectionPool connectionPool) {
        String sql = "SELECT * FROM toppings";
        List<Topping> toppingList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int toppingID = rs.getInt("topping_id");
                String toppingName = rs.getString("topping_name");
                int toppingPrice = rs.getInt("topping_price");
                toppingList.add(new Topping(toppingID, toppingName, toppingPrice));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return toppingList;

    }

    public static List<Base> getAllBases(ConnectionPool connectionPool) {
        String sql = "SELECT * FROM bases";
        List<Base> baseList = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int baseID = rs.getInt("base_id");
                String baseName = rs.getString("base_name");
                int basePrice = rs.getInt("base_price");
                baseList.add(new Base(baseID, baseName, basePrice));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return baseList;
    }





}
