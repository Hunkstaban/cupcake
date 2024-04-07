package app.controllers;

import app.entities.Base;
import app.entities.Topping;
import app.persistence.ConnectionPool;
import app.persistence.CupcakeMapper;
import io.javalin.http.Context;

import java.util.List;

public class CupcakeController {

    public static Context baseToppingAttributes (Context ctx, ConnectionPool connectionPool) {
        List<Base> baseList = CupcakeMapper.getAllBases(connectionPool);
        ctx.attribute("baseList", baseList);
        List<Topping> toppingList = CupcakeMapper.getAllToppings(connectionPool);
        ctx.attribute("toppingList", toppingList);
        return ctx;
    }
}
