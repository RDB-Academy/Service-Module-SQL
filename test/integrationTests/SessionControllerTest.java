package integrationTests;


import org.junit.Test;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;
import play.mvc.Http;
import play.test.WithServer;

import java.util.concurrent.CompletionStage;

import static junit.framework.TestCase.assertEquals;

public class SessionControllerTest extends WithServer{

    @Test
    public void testWithServer() throws Exception {
        String url = "http://localhost:" + this.testServer.port() + "/";
        try (WSClient ws = play.test.WSTestClient.newClient(this.testServer.port())) {
            CompletionStage<WSResponse> stage = ws.url(url).get();
            WSResponse response = stage.toCompletableFuture().get();
            assertEquals(Http.Status.NOT_FOUND, response.getStatus());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
