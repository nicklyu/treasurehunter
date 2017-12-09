package ru.game.http;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import ru.game.database.DatabaseService;

public class RestApiRouter {

    private static final String DB_QUEUE = "database.queue";
    private static final String PREFIX = "/api/:levelid/";

    private DatabaseService dbService;


    public void init(Vertx vertx, Router router){

        dbService = DatabaseService.createProxy(vertx, DB_QUEUE);

        router.get("/api/levels").handler(this::getAllLevels);
        router.get(PREFIX+"info").handler(this::getLevelInfo);
        router.get(PREFIX+"area").handler(this::getLevelArea);
        router.get(PREFIX+"tip").handler(this::getTips);
        router.get(PREFIX+"tip/:tipid").handler(this::getTipByNumber);
        router.get(PREFIX+"initialtip").handler(this::getInitialTip);
        router.get(PREFIX+"treasure").handler(this::getTreasure);

    }

    private void getAllLevels(RoutingContext context){
        dbService.fetchAllLevels(reply->{
           if(reply.succeeded()){
               JsonArray levels = reply.result();
               context.response().end(levels.toString());
           } else {
               context.fail(reply.cause());
           }
        });
    }
    /**
     * Возвращает полную информацию об уровне
     * - Расположение арены
     * - Расположение подсказок
     * - Расположение сокровища
     * @param context
     */
    private void getLevelInfo(RoutingContext context){
        //TODO
        context.response().end();
    }

    /**
     * Возвращает расположение арены уровня
     * @param context
     */
    private void getLevelArea(RoutingContext context){
        //TODO
        context.response().end();
    }

    /**
     * Возвращает расположение всех подсказок для уровня
     * @param context
     */
    private void getTips(RoutingContext context){
        //TODO
        context.response().end();
    }

    /**
     * Возвращает расположение первой подсказки
     * @param context
     */
    private void getInitialTip(RoutingContext context){
        //TODO
        context.response().end();
    }

    /**
     * Возвращает расположение указанной подсказки
     * @param context
     */
    private  void getTipByNumber(RoutingContext context){
        //TODO
        context.response().end();
    }

    /**
     * Возвращает расположение сокровища
     * @param context
     */
    private void getTreasure(RoutingContext context){
        //TODO
        context.response().end();
    }
}
