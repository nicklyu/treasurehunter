package ru.game.app;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import ru.game.http.HttpServerVerticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {


        DeploymentOptions options = new DeploymentOptions();
        vertx.fileSystem().readFile("src/main/resources/config/http/config.json" , bufferAsyncResult -> {
            if(bufferAsyncResult.succeeded()){
                options.setConfig(new JsonObject(bufferAsyncResult.result().toString()));
                vertx.deployVerticle("ru.game.http.HttpServerVerticle", options, stringAsyncResult -> {
                    if(stringAsyncResult.succeeded()){
                        startFuture.complete();
                    } else {
                        startFuture.fail(stringAsyncResult.cause());
                    }
                });
            } else {
                startFuture.fail(bufferAsyncResult.cause());
            }
        });
    }
}
