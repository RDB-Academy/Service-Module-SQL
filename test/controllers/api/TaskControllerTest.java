package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.db.evolutions.Evolutions;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

/**
 * @author fabiomazzone
 */
public class TaskControllerTest {
    protected Application application;

    @Before
    public void startApp() throws Exception {
        Helpers.in
        application = new GuiceApplicationBuilder()
                .in(Mode.TEST).build();
        Helpers.start(application);
    }

    @Test
    public void TaskControllerCreate() throws Exception {

        JsonNode jsonNode = (new ObjectMapper()).readTree("{ \"someName\": \"sameValue\" }");
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .bodyJson(jsonNode)
                .uri(controllers.api.routes.TaskController.create().url());
        Result result = route(request);

        assertTrue(result.status() == OK);
    }

    @After
    public void stopApp() throws Exception {
        Helpers.stop(application);
    }}
