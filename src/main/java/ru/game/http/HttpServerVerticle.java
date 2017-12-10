package ru.game.http;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

public class HttpServerVerticle extends AbstractVerticle{

    private static final String HTTP_SERVER_PORT = "http.server.port";
    private final FreeMarkerTemplateEngine templateEngine = FreeMarkerTemplateEngine.create();


    @Override
    public void start(Future<Void> startFuture) {
        int port;
        if(System.getenv("PORT") == null){
            port = config().getInteger(HTTP_SERVER_PORT);
        }else {
            port = Integer.parseInt(System.getenv("PORT"));
        }

        HttpServer server = vertx.createHttpServer();

        Router router = Router.router(vertx);
        router.get("/app/*").handler(StaticHandler.create().setCachingEnabled(false));
        router.get("/").handler(context ->context.reroute("/app/main.html"));

        router.get("/admin").handler(this::adminPageHandler);

        RestApiRouter restApiRouter = new RestApiRouter();
        restApiRouter.init(vertx, router);

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

    private void adminPageHandler(RoutingContext context){
        //TODO [Front] Админская панель
        //Список существующих уровней и их редактирование
        context.response().end();
    }
}
