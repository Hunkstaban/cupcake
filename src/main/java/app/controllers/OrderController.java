package app.controllers;

import app.entities.OrderDetail;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderDetailMapper;
import app.persistence.UserMapper;
import io.javalin.*;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

public class OrderController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        //app.post("movetask", ctx -> moveTask(ctx, connectionPool));
        app.post("addToCart", ctx -> addToCart(ctx, connectionPool));
        app.get("goToCart", ctx -> goToCart(ctx, connectionPool));
        app.post("removeFromCart", ctx -> removeFromCart(ctx, connectionPool));
        app.post("newOrder", ctx -> newOrder(ctx, connectionPool));
    }

    private static void removeFromCart(Context ctx, ConnectionPool connectionPool) {

        int cartIndex = Integer.parseInt(ctx.formParam("cartIndex"));

        try {

            User user = ctx.sessionAttribute("currentUser");
            user.removeFromCart(cartIndex);
            ctx.redirect("goToCart");

        } catch (Exception e) {
            System.out.println("Error removing item from cart");
        }
    }

    private static void goToCart(Context ctx, ConnectionPool connectionPool) {

        User user = ctx.sessionAttribute("currentUser");
        List<OrderDetail> cartList = user.getCartList();

        //Figure out the total price of the order so it can be sent as an attribute
        int orderTotalPrice = 0;
        for (OrderDetail orderDetail : cartList) {
            orderTotalPrice += orderDetail.getTotalPrice();
        }

        ctx.attribute("cartList", cartList);
        ctx.attribute("orderTotalPrice", orderTotalPrice);
        ctx.render("cart.html");

    }


    private static void addToCart(Context ctx, ConnectionPool connectionPool) {
        int baseID = Integer.parseInt(ctx.formParam("base"));
        int toppingID = Integer.parseInt(ctx.formParam("topping"));
        int amount = Integer.parseInt(ctx.formParam("amount"));

        User user = ctx.sessionAttribute("currentUser");
        OrderDetailMapper.addToCart(baseID, toppingID, amount, user, connectionPool);


        ctx = CupcakeController.baseToppingAttributes(ctx, connectionPool);
        ctx.render("index.html");

    }

    private static void newOrder(Context ctx, ConnectionPool connectionPool) {

        User user = ctx.sessionAttribute("currentUser");

        // Update User balance and check if they have enough money to complete the transaction (using UserMapper)
        int orderTotalPrice = Integer.parseInt(ctx.formParam("orderTotalPrice"));

        try {
            UserMapper.updateBalance(user, orderTotalPrice, connectionPool);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        // If no errors in User balance check, create new order
        try {
            int orderID = OrderDetailMapper.newOrder(user, connectionPool);
            OrderDetailMapper.insertOrderDetails(user, orderID, connectionPool);

            // If order completed, empty currentUser's cart and refresh the page, sending orderID as an attribute
            user.emptyCart();
            ctx.attribute("orderID", orderID);
            ctx.render("cart.html");
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

    }


}
