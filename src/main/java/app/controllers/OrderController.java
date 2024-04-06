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

        // Update User balance and check if they have enough money to complete the transaction
        int currentUserBalance = user.getBalance();

        try {
            int orderID = OrderDetailMapper.newOrder(user, connectionPool);
            OrderDetailMapper.insertOrderDetails(user, orderID, connectionPool);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }


    }


}
