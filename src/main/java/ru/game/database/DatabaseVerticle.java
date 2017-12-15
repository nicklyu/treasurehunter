package ru.game.database;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.serviceproxy.ProxyHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class DatabaseVerticle extends AbstractVerticle {

    private static final String SQL_QUERIES_FILE_PATH = "database.queries.file.path";
    private static final String JDBC_URL = "database.jdbc.url";
    private static final String JDBC_DRIVER_CLASS = "database.jdbc.driver";
    private static final String JDBC_MAX_POOL_SIZE = "database.jdbc.maxpoolsize";
    private static final String DB_USERNAME = "database.username";
    private static final String DB_PASSWORD = "database.password";
    private static final String DB_QUEUE = "database.queue";

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        System.out.println(config().toString());
        HashMap<SqlQuery, String> sqlQueries = loadSqlQueries();

        JDBCClient jdbcClient = JDBCClient.createShared(vertx, new JsonObject()
                .put("url", config().getString(JDBC_URL))
                .put("username", config().getString(DB_USERNAME))
                .put("password", config().getString(DB_PASSWORD))
                .put("driver_class", config().getString(JDBC_DRIVER_CLASS))
                .put("max_pool_size", config().getInteger(JDBC_MAX_POOL_SIZE)));

        DatabaseService.create(jdbcClient, sqlQueries, ready -> {
            if (ready.succeeded()) {
                ProxyHelper.registerService(DatabaseService.class, vertx, ready.result(), DB_QUEUE);
                startFuture.complete();
            } else {
                startFuture.fail(ready.cause());
            }
        });

    }

    private HashMap<SqlQuery, String> loadSqlQueries() throws IOException {
        Properties properties;
        try (InputStream inputStream = getClass().getResourceAsStream(config().getString(SQL_QUERIES_FILE_PATH))) {

            properties = new Properties();
            properties.load(inputStream);
        }
        HashMap<SqlQuery, String> sqlQueries = new HashMap<>();
        //Заполнение запросами

        sqlQueries.put(SqlQuery.ALL_LEVELS, properties.getProperty("get-all-levels"));
        sqlQueries.put(SqlQuery.LEVEL, properties.getProperty("get-level"));
        sqlQueries.put(SqlQuery.LEVEL_AREA, properties.getProperty("get-level-area"));
        sqlQueries.put(SqlQuery.LEVEL_TREASURE, properties.getProperty("get-level-treasure"));
        sqlQueries.put(SqlQuery.TIPS, properties.getProperty("get-tips"));
        sqlQueries.put(SqlQuery.ADMIN_LEVEL, properties.getProperty("get-admin-info"));

        return sqlQueries;


    }
}
