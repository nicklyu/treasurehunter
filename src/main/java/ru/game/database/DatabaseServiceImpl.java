package ru.game.database;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;

import java.util.HashMap;
import java.util.stream.Collectors;

public class DatabaseServiceImpl implements DatabaseService{

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseServiceImpl.class);


    private final HashMap<SqlQuery, String> sqlQueries;
    private final JDBCClient dbClient;

    DatabaseServiceImpl(JDBCClient dbClient, HashMap<SqlQuery, String> sqlQueries, Handler<AsyncResult<DatabaseService>> readyHandler) {
        this.dbClient = dbClient;
        this.sqlQueries = sqlQueries;

        dbClient.getConnection(connection->{
            if(connection.succeeded()){
                LOGGER.debug("Создано соединение с базой данных");
                readyHandler.handle(Future.succeededFuture(this));
            } else {
                LOGGER.error("Невозможно установить соединение с бд",  connection.cause());
                readyHandler.handle(Future.failedFuture(connection.cause()));
            }
        });


    }


    @Override
    public DatabaseService fetchAllLevels(Handler<AsyncResult<JsonArray>> resultHandler) {
        dbClient.query(sqlQueries.get(SqlQuery.ALL_LEVELS), res->{
           if(res.succeeded()) {
               JsonArray levels = new JsonArray(res.result()
               .getResults()
               .stream()
               .collect(Collectors.toList()));
               resultHandler.handle(Future.succeededFuture(levels));
           } else {
               resultHandler.handle(Future.failedFuture(res.cause()));
           }
        });
        return this;
    }

    @Override
    public DatabaseService fetchLevelInfo(int levelId, Handler<AsyncResult<JsonObject>> resultHandler) {
        dbClient.queryWithParams(sqlQueries.get(SqlQuery.LEVEL),new JsonArray().add(levelId), res->{
            if(res.succeeded()){
                ResultSet resultSet = res.result();
                JsonArray levelDtj = resultSet.getResults().get(0);

                JsonObject level = new JsonObject();
                level.put("id", levelDtj.getInteger(0));
                level.put("name", levelDtj.getString(1));
                level.put("description", levelDtj.getString(2));
                level.put("area", levelDtj.getString(3));
                level.put("treasure", levelDtj.getString(4));

                resultHandler.handle(Future.succeededFuture(level));
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
        return this;
    }

    @Override
    public DatabaseService fetchLevelArea(int levelId, Handler<AsyncResult<JsonObject>> resultHandler) {
        dbClient.queryWithParams(sqlQueries.get(SqlQuery.LEVEL_AREA), new JsonArray().add(levelId), res->{
            if(res.succeeded()){
                ResultSet resultSet = res.result();
                JsonArray levelDtj = resultSet.getResults().get(0);

                JsonObject area = new JsonObject();
                area.put("id", levelDtj.getInteger(0));
                area.put("area", levelDtj.getString(1));

                resultHandler.handle(Future.succeededFuture(area));
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
        return this;
    }

    @Override
    public DatabaseService fetchLevelTreasure(int levelId, Handler<AsyncResult<JsonObject>> resultHandler) {
        dbClient.queryWithParams(sqlQueries.get(SqlQuery.LEVEL_TREASURE), new JsonArray().add(levelId), res->{
            if(res.succeeded()){
                ResultSet resultSet = res.result();
                JsonArray treasureDtj = resultSet.getResults().get(0);

                JsonObject treasure = new JsonObject();
                treasure.put("id", treasureDtj.getInteger(0));
                treasure.put("treasure", treasureDtj.getString(1));

                resultHandler.handle(Future.succeededFuture(treasure));
            } else {
                resultHandler.handle(Future.failedFuture(res.cause()));
            }
        });
        return this;
    }
}
