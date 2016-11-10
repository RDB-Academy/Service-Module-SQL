package controllers.api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Before;
import org.junit.Test;
import play.Logger;
import play.libs.ws.WSResponse;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WS;
import play.mvc.Result;
import play.test.WithServer;

import java.util.concurrent.CompletionStage;

import static org.junit.Assert.assertEquals;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * @author fabiomazzone
 */
public class TaskControllerTest extends WithServer {
    private WSClient wsClient;

    @Before
    public void setUp() throws Exception {
        wsClient = WS.newClient(this.testServer.port());
    }

    @Test
    public void TaskControllerCreate() throws Exception {
        ObjectNode task = Json.newObject();
        task.put("text", "Find Fabio");
        task.put("referenceStatement", "SELECT * FROM Person WHERE firstname = \"Fabio\";");
        CompletionStage<WSResponse> completionStage =
                wsClient.url(routes.TaskController.create().url()).post(task);
        WSResponse response = completionStage.toCompletableFuture().get();
    }

    @Test
    public void TaskControllerView() throws Exception {
        CompletionStage<WSResponse> completionStage =
                wsClient.url(routes.TaskController.view().url()).get();
        WSResponse response = completionStage.toCompletableFuture().get();

        assertEquals("TaskController#View Should return True", response.getStatus(), OK);
        Logger.info(response.getBody());
    }

    //@Test
    public void TaskControllerShow() throws Exception {
        Result result = route(routes.TaskController.show(1L));

    }
}
