package ru.game.database;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.jdbc.JDBCClient;

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
        dbClient.query(sqlQueries.get(SqlQuery.GET_ALL_LEVELS), res->{
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
}
