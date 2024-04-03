package app.persistence;

import app.entities.Topping;
import app.entities.Base;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CupcakeMapper {


    public Topping getToppingByID(int toppingID, ConnectionPool connectionPool) throws DatabaseException {

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

    public Base getBaseByID(int baseID, ConnectionPool connectionPool) throws DatabaseException {
        String sql = "SELECT * FROM topping WHERE base_id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String baseName = rs.getString("topping_name");
                int basePrice = rs.getInt("topping_price");
                return new Base(baseID, baseName, basePrice);
            } else {
                throw new DatabaseException("Error topping not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }



}
