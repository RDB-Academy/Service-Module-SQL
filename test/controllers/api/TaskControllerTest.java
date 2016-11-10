package controllers.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.Configuration;
import play.Mode;
import play.db.Database;
import play.db.Databases;
import play.db.evolutions.Evolutions;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

import static junit.framework.TestCase.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.route;

public class TaskControllerTest {

    protected Application application;
    Database database;

    @Before
    public void startApp() throws Exception {
        Config config = ConfigFactory.load("test");
        Configuration configuration = new Configuration(config);
        String dbDriver = configuration.getString("db.default.driver");
        String dbUrl = configuration.getString("db.default.url");

        database = Databases.createFrom(dbDriver, dbUrl);
        Evolutions.cleanupEvolutions(database);
        Evolutions.applyEvolutions(database);

        application = new GuiceApplicationBuilder()
                .in(Mode.TEST)
                .loadConfig(configuration)
                .build();
        Helpers.start(application);
    }

    @Test
    public void TaskControllerCreate() throws Exception {

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
        database.shutdown();
    }

}