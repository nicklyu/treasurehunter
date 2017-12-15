package ru.game.database;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

import java.util.HashMap;

@ProxyGen
public interface DatabaseService {

    @Fluent
    DatabaseService fetchAllLevels(Handler<AsyncResult<JsonArray>> resultHandler);

    @Fluent
    DatabaseService fetchLevelInfo(int levelId, Handler<AsyncResult<JsonObject>> resultHandler);

    @Fluent
    DatabaseService fetchLevelArea(int levelId, Handler<AsyncResult<JsonObject>> resultHandler);

    @Fluent
    DatabaseService fetchLevelTreasure(int levelId, Handler<AsyncResult<JsonObject>> resultHandler);

    @Fluent
    DatabaseService fetchAllTips(int levelId, Handler<AsyncResult<JsonArray>> resultHandler);

    @Fluent
    DatabaseService fetchAdminLevel(int levelId, Handler<AsyncResult<JsonObject>> resultHandler);


    static DatabaseService create(JDBCClient jdbcClient, HashMap<SqlQuery, String> sqlQueries, Handler<AsyncResult<DatabaseService>> readyHandler){
        return new DatabaseServiceImpl(jdbcClient,sqlQueries,readyHandler);
    }
    static DatabaseService createProxy(Vertx vertx, String address) {
        return new DatabaseServiceVertxEBProxy(vertx, address);
    }

}
