package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderDetailMapper;
import io.javalin.*;
import io.javalin.http.Context;

public class OrderController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        //app.post("movetask", ctx -> moveTask(ctx, connectionPool));
        app.post("addToCart", ctx -> addToCart(ctx, connectionPool));
    }


    private static void addToCart(Context ctx, ConnectionPool connectionPool) {
        int baseID = Integer.parseInt(ctx.formParam("base"));
        int toppingID = Integer.parseInt(ctx.formParam("topping"));
        int amount = Integer.parseInt(ctx.formParam("amount"));

        User user = ctx.sessionAttribute("currentUser");
        OrderDetailMapper.addToCart(baseID, toppingID, amount, user, connectionPool);


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
