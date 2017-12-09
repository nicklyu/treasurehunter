package ru.game.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HttpServerVerticle extends AbstractVerticle{

    public static final String HTTP_SERVER_PORT = "http.server.port";


    @Override
    public void start(Future<Void> startFuture) throws Exception {
        int port = config().getInteger(HTTP_SERVER_PORT);
        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.get("/").handler(this::baseHandler);

        server
                .requestHandler(router::accept)
                .listen(port, httpServerAsyncResult -> {
                    if(httpServerAsyncResult.succeeded()){
                        startFuture.complete();
                    } else {
                        startFuture.fail(httpServerAsyncResult.cause());
                    }
                });
    }

    private void baseHandler(RoutingContext context){
        context.response().putHeader("content-type", "application/json")
                .end("{\"text\" : \"test\"}");
    }
}
