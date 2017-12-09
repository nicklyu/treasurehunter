package ru.game.http;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.ServerSocket;

@RunWith(VertxUnitRunner.class)
public class HttpServerVerticleTest{
    private Vertx vertx;
    private Integer port;

    @Before
    public void setup(TestContext context) throws IOException {
        vertx = Vertx.vertx();

        ServerSocket serverSocket = new ServerSocket(0);
        port = serverSocket.getLocalPort();
        serverSocket.close();

        DeploymentOptions options = new DeploymentOptions()
                .setConfig(new JsonObject().put("http.server.port", port));

        vertx.deployVerticle(HttpServerVerticle.class.getName(), options, context.asyncAssertSuccess());
    }

    @After
    public void teardown(TestContext context){
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void explorePageTest(TestContext context){
        Async async = context.async();
        vertx.createHttpClient().getNow(port, "localhost", "/", httpClientResponse -> {
            context.assertEquals(httpClientResponse.statusCode(), 200);
            async.complete();
        });
    }

    @Test
    public void adminPageTest(TestContext context){
        Async async = context.async();
        vertx.createHttpClient().getNow(port, "localhost", "/admin", httpClientResponse -> {
            context.assertEquals(httpClientResponse.statusCode(), 200);
            async.complete();
        });
    }
}
