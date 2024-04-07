package app.controllers;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class LoginController {
    public static void addRoute(Javalin app, ConnectionPool connectionPool) {
        app.get("loginPage", ctx -> loginPage(ctx, connectionPool));
        app.get("createPage", ctx -> createUserPage(ctx, connectionPool));
    }

    private static void createUserPage(Context ctx, ConnectionPool connectionPool) {
        ctx.render("create.html");
    }

    private static void loginPage (Context ctx, ConnectionPool connectionPool)     {
        ctx.render("login.html");
    }
}
