package initializers;

import play.Environment;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Initializer implements InitializerBase {
    @Inject
    public Initializer(Environment environment) {
        if (environment.isDev()) {
            Logger.info("Initialize Dev Environment");
            Tasks.initDev();
        } else if(environment.isTest()) {
            Logger.info("Initialize Test Environment");
            Tasks.initDev();
        } else {
            Logger.info("Initialize Prod Environment");
        }
    }
}
