package ru.game.http;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import ru.game.database.DatabaseService;
import ru.game.wrapper.GeoUtils;

public class RestApiRouter {

    private static final String DB_QUEUE = "database.queue";
    private static final String PREFIX = "/api/:levelid/";

    private DatabaseService dbService;


    public void init(Vertx vertx, Router router) {

        dbService = DatabaseService.createProxy(vertx, DB_QUEUE);

        router.get("/api/levels").handler(this::getAllLevels);
        router.get(PREFIX + "info").handler(this::getLevelInfo);
        router.get(PREFIX + "area").handler(this::getLevelArea);
        router.get(PREFIX + "tip").handler(this::getTips);
        router.get(PREFIX + "tip/:tipId").handler(this::getTipByNumber);
        router.get(PREFIX + "initialtip").handler(this::getInitialTip);
        router.get(PREFIX + "treasure").handler(this::getTreasure);

    }

    /**
     * Возвращает список из всех уровней
     *
     * @param context
     */
    private void getAllLevels(RoutingContext context) {
        dbService.fetchAllLevels(reply -> {
            if (reply.succeeded()) {
                JsonArray levels = GeoUtils.formatLevelsInfo(reply.result());
                context.response().end(levels.toString());
            } else {
                context.fail(reply.cause());
            }
        });
    }

    /**
     * Возвращает полную информацию об уровне
     * - Имя и описание
     * - Расположение арены
     * - Расположение подсказок
     * - Расположение сокровища
     *
     * @param context
     */
    private void getLevelInfo(RoutingContext context) {
        Integer levelId = Integer.parseInt(context.pathParam("levelid"));
        dbService.fetchLevelInfo(levelId, reply -> {
            if (reply.succeeded()) {
                JsonObject level = reply.result();
                context.response().end(GeoUtils.levelToGeoJson(level));
            } else {
                context.fail(reply.cause());
            }
        });
    }

    /**
     * Возвращает расположение арены уровня
     *
     * @param context
     */
    private void getLevelArea(RoutingContext context) {
        Integer levelId = Integer.parseInt(context.pathParam("levelid"));
        dbService.fetchLevelArea(levelId, reply -> {
            if (reply.succeeded()) {
                JsonObject area = reply.result();
                context.response().end(GeoUtils.areaToGeoJson(area));
            } else {
                context.fail(reply.cause());
            }
        });
    }

    /**
     * Возвращает расположение всех подсказок для уровня
     *
     * @param context
     */
    private void getTips(RoutingContext context) {
        Integer levelId = Integer.parseInt(context.pathParam("levelid"));
        dbService.fetchAllTips(levelId, reply -> {
            if (reply.succeeded()) {
                JsonArray tips = reply.result();
                context.response().end(GeoUtils.tipsToGeoJson(tips));
            } else {
                context.fail(reply.cause());
            }
        });
    }

    /**
     * Возвращает расположение первой подсказки
     *
     * @param context
     */
    private void getInitialTip(RoutingContext context) {
        Integer levelId = Integer.parseInt(context.pathParam("levelid"));
        dbService.fetchAllTips(levelId, reply -> {
            if (reply.succeeded()) {
                JsonArray tips = reply.result();
                if (tips.size() < 1) {
                    context.fail(404);
                } else {
                    context.response().end(GeoUtils.tipToGeoJson(tips.getJsonArray(0)));
                }
            } else {
                context.fail(reply.cause());
            }
        });
    }

    /**
     * Возвращает расположение указанной подсказки
     *
     * @param context
     */
    private void getTipByNumber(RoutingContext context) {
        Integer levelId = Integer.parseInt(context.pathParam("levelid"));
        Integer tipNum = Integer.parseInt(context.pathParam("tipId"));
        dbService.fetchAllTips(levelId, reply -> {
            if (reply.succeeded()) {
                JsonArray tips = reply.result();
                if (tips.size() < tipNum) {
                    context.fail(404);
                } else {
                    context.response().end(GeoUtils.tipToGeoJson(tips.getJsonArray(tipNum)));
                }
            } else {
                context.fail(reply.cause());
            }
        });
    }

    /**
     * Возвращает расположение сокровища
     *
     * @param context
     */
    private void getTreasure(RoutingContext context) {
        Integer levelId = Integer.parseInt(context.pathParam("levelid"));
        dbService.fetchLevelTreasure(levelId, reply -> {
            if (reply.succeeded()) {
                JsonObject treasure = reply.result();
                context.response().end(treasure.toString());
            } else {
                context.fail(reply.cause());
            }
        });
    }
}
