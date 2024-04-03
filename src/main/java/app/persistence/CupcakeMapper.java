package app.persistence;

import app.entities.Base;
import app.entities.Cupcake;
import app.entities.Topping;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CupcakeMapper {

    public static Cupcake addCupcake(int toppingID, int baseID, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "SELECT * FROM toppings, bases WHERE toppings.topping_id = ? AND bases.base_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, toppingID);
            ps.setInt(2, baseID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String toppingName = rs.getString("topping_name");
                String baseName = rs.getString("base_name");
                int price = rs.getInt("topping_price") + rs.getInt("base_price");

                return new Cupcake(baseID, toppingID, baseName, toppingName, price);
            } else {
                throw new DatabaseException("Error on addCupcake");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Topping> getToppings(ConnectionPool connectionPool) {

        String sql = "SELECT * FROM toppings";

        ArrayList<Topping> toppings = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int toppingID = rs.getInt("topping_id");
                String toppingName = rs.getString("topping_name");
                int toppingPrice = rs.getInt("topping_price");
                toppings.add(new Topping(toppingID, toppingName, toppingPrice));
            }
            return toppings;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Base> getBases (ConnectionPool connectionPool) {

        String sql = "SELECT * FROM base";

        ArrayList<Base> bases = new ArrayList<>();

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int baseID = rs.getInt("base_id");
                String baseName = rs.getString("base_name");
                int basePrice = rs.getInt("base_price");
                bases.add(new Base(baseID, baseName, basePrice));
            }
            return bases;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
