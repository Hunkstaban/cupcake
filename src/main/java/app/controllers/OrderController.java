package app.controllers;

import app.entities.OrderDetail;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderDetailMapper;
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
    }

    private static void removeFromCart(Context ctx, ConnectionPool connectionPool) {

        int cartIndex = Integer.parseInt(ctx.formParam("cartIndex"));

        try {

                User user = ctx.sessionAttribute("currentUser");
                user.removeFromCart(cartIndex);
                goToCart(ctx, connectionPool);

        } catch (Exception e) {
            System.out.println("Error removing item from cart");
        }
    }

    private static void goToCart(Context ctx, ConnectionPool connectionPool) {

        User user = ctx.sessionAttribute("currentUser");
        List<OrderDetail> cartList = user.getCartList();
        ctx.attribute("cartList", cartList);
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
        try {
            int orderID = OrderDetailMapper.newOrder(user, connectionPool);
            OrderDetailMapper.insertOrderDetails(user, orderID, connectionPool);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }


    }


}
