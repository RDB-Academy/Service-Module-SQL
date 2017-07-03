package controllers.api;

import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.mvc.Result;
import play.test.WithApplication;
import play.test.WithServer;
import services.SessionService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;

/**
 * Created by fabiomazzone on 03.07.17.
 */
public class TaskTrialControllerTest extends WithApplication {
    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder()
                .configure("play.http.router", "javaguide.tests.Routes")
                .build();
        }

    @Test
    public void testCreateTaskTrial() {
        SessionService
        TaskTrialController taskTrialController = new TaskTrialController();
        Result result = taskTrialController.create();

        assertEquals(OK, result.status());

        //assertEquals("text/html", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        //assertTrue(contentAsString(result).contains("Welcome"));
    }
}
