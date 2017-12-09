package ru.game.app;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import ru.game.database.DatabaseVerticle;
import ru.game.http.HttpServerVerticle;


public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {

        DeploymentOptions dbOptions = new DeploymentOptions();
        vertx.fileSystem().readFile("src/main/resources/config/database/config.json", dbBufferAsyncResult ->{
            if(dbBufferAsyncResult.succeeded()){
                dbOptions.setConfig(new JsonObject(dbBufferAsyncResult.result().toString()));
                vertx.deployVerticle(DatabaseVerticle.class.getName(), dbOptions, dbResult->{
                    if(dbResult.succeeded()){
                        DeploymentOptions options = new DeploymentOptions();
                        vertx.fileSystem().readFile("src/main/resources/config/http/config.json" , bufferAsyncResult -> {
                            if(bufferAsyncResult.succeeded()){
                                options.setConfig(new JsonObject(bufferAsyncResult.result().toString()));
                                vertx.deployVerticle(HttpServerVerticle.class.getName(), options, serverAsyncResult -> {
                                    if(serverAsyncResult.succeeded()){
                                        startFuture.complete();
                                    } else {
                                        startFuture.fail(serverAsyncResult.cause());
                                    }
                                });
                            } else {
                                startFuture.fail(bufferAsyncResult.cause());
                            }
                        });
                    } else {
                        startFuture.fail(dbResult.cause());
                    }
                });
            } else {
                startFuture.fail(dbBufferAsyncResult.cause());
            }
        });
    }
}
