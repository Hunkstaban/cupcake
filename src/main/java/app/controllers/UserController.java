package app.controllers;

import app.entities.Base;
import app.entities.Order;
import app.entities.Topping;
import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

import static app.persistence.UserMapper.login;

public class UserController {


    public static void addRoute(Javalin app, ConnectionPool connectionPool) {

        app.post("login", ctx -> login(ctx, connectionPool));
        app.get("logout", ctx -> logout(ctx));
        app.get("create", ctx -> createUser(ctx, connectionPool));


    }

    private static void createUser(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            User newUser = UserMapper.createUser(email,password,connectionPool);
            userLogin(ctx, connectionPool, newUser);
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
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
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }

    }

    private static void userLogin(Context ctx, ConnectionPool connectionPool, User user) {
        ctx.sessionAttribute("currentUser", user);
        ctx = CupcakeController.baseToppingAttributes(ctx, connectionPool);
        ctx.render("index.html");
    }

    private static void adminLogin(Context ctx, ConnectionPool connectionPool, User user) {
        ctx.sessionAttribute("currentUser", user);
        // will need attribute from not yet created viewALlOrders method (OrderController/Mapper)
        ctx.render("orders.html");
    }

}// ---------------------------------  end class ------------------------------------
