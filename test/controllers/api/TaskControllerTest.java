package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;

import static junit.framework.TestCase.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

public class TaskControllerTest {

    protected Application application;

    @Before
    public void startApp() throws Exception {
        ClassLoader classLoader = FakeApplication.class.getClassLoader();
        application = new GuiceApplicationBuilder().in(classLoader)
                .in(Mode.TEST).build();
        Helpers.start(application);
    }

    @Test
    public void myPostActionTest() throws Exception {

        JsonNode jsonNode = (new ObjectMapper()).readTree("{ \"someName\": \"sameValue\" }");
        Http.RequestBuilder request = new Http.RequestBuilder().method("POST")
                .bodyJson(jsonNode)
                .uri(routes.TaskController.create().url());
        Result result = route(request);

        assertTrue(result.status() == OK);
    }

    @After
    public void stopApp() throws Exception {
        Helpers.stop(application);
    }

}