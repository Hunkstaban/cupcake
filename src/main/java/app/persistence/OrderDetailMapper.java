package app.persistence;

import app.entities.Base;
import app.entities.Topping;
import app.entities.User;
import app.exceptions.DatabaseException;

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
}
