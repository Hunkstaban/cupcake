package app.controllers;

import app.entities.User;
import app.persistence.ConnectionPool;
import app.persistence.OrderDetailMapper;
import io.javalin.*;
import io.javalin.http.Context;

public class OrderController {


    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        //app.post("movetask", ctx -> moveTask(ctx, connectionPool));
        app.post("addToCart", ctx -> addToCart(ctx, connectionPool));
    }

    public static void addToCart(Context ctx, ConnectionPool connectionPool) {
        int baseID = Integer.parseInt(ctx.formParam("baseID"));
        int toppingID = Integer.parseInt(ctx.formParam("toppingID"));
        int amount = Integer.parseInt(ctx.formParam("amount"));

        User user = ctx.sessionAttribute("currentUser");
        OrderDetailMapper.addToCart(baseID, toppingID, amount, user, connectionPool);


    }

    public static void newOrder(Context ctx, ConnectionPool connectionPool) {

    }


}
