package app.controllers;

import app.entities.Order;
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
        app.get("opret", ctx -> opret(ctx, connectionPool));


    }

    private static void opret(Context ctx, ConnectionPool connectionPool) {
        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {
            UserMapper.createUser(email,password,connectionPool);
            // TODO Kan man ikke bare kalde "login" methoden?
            User user = UserMapper.login(email,password,connectionPool);
            ctx.sessionAttribute("currentUser", user);
            ctx.render("index.html");
        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("opret.html");
        }
    }

    // .invalidate = we don't need it anymore. and want to close it.
    private static void logout(Context ctx) {
        ctx.req().getSession().invalidate();
        ctx.render("index.html");
    }

    private static void login(Context ctx, ConnectionPool connectionPool) {

        String email = ctx.formParam("email");
        String password = ctx.formParam("password");

        try {


            User user = UserMapper.login(email, password, connectionPool);
            ctx.sessionAttribute("currentUser", user);
            ctx.render("index.html");
//            List<Order> cartList = CupcakeMapper.getCart() // TODO what we do here + make getcart method ??
//            ctx.attribute("cart : ", cartList);


        } catch (DatabaseException e) {
            ctx.attribute("message", e.getMessage());
            ctx.render("index.html");
        }
    }


}// ---------------------------------  end class ------------------------------------
