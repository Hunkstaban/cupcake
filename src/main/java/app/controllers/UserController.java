package app.controllers;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import app.persistence.OrderMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.ArrayList;
import java.util.List;

import static app.persistence.UserMapper.login;

public class UserController {


    public static void addRoute(Javalin app, ConnectionPool connectionPool) {

        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("logout", ctx -> logout(ctx));
        app.post("create", ctx -> createUser(ctx, connectionPool));
        app.get("renderIndex", ctx -> renderIndex(ctx, connectionPool));


    }

    private static void renderIndex(Context ctx, ConnectionPool connectionPool) {

        CupcakeController.baseToppingAttributes(ctx, connectionPool);
        ctx.render("index.html");

    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User newUser = UserMapper.createUser(email,password,connectionPool);
            userLogin(ctx, connectionPool, newUser);
        } catch (DatabaseException e) {
            String msg = "Bruger med denne email eksisterer allerede.";
            ctx.attribute("alreadyExist", msg);
            ctx.render("create.html");
        }
    }



    // .invalidate() = we don't need it anymore. and want to close it.
    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.render("login.html");
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {

        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User user = UserMapper.login(email, password, connectionPool);
            if (user.getRoleID() == 2) {
                adminLogin(ctx, connectionPool, user);
            } else {
                userLogin(ctx, connectionPool, user);
            }

        } catch (DatabaseException e) {
            String msg = "Forket email eller kodeord. Prøv igen";
            ctx.attribute("loginError", msg);
            ctx.render("login.html");
        }

    }

    private static void userLogin(Context ctx, ConnectionPool connectionPool, User user) {
        ctx.sessionAttribute("currentUser", user);
        ctx = CupcakeController.baseToppingAttributes(ctx, connectionPool);
        ctx.render("index.html");
    }

    private static void adminLogin(Context ctx, ConnectionPool connectionPool, User user) {
        ctx.sessionAttribute("currentUser", user);
        List<Order> orderList =  OrderMapper.viewAllOrders(connectionPool);
        ctx.attribute("orderList", orderList);
        ctx.render("orders.html");
    }

}// ---------------------------------  end class ------------------------------------
