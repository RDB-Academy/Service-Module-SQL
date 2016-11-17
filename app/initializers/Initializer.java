package initializers;

import play.Environment;
import play.Logger;
import repository.TaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Initializer implements InitializerBase {
    @Inject
    public Initializer(Environment environment, TaskRepository taskRepository) {
        if (environment.isDev()) {
            Logger.info("Initialize Dev Environment");
            Tasks.initDev();
            SchemaDefs.initDev(taskRepository);
        } else if(environment.isTest()) {
            Logger.info("Initialize Test Environment");
            Tasks.initDev();
            SchemaDefs.initDev(taskRepository);
        } else {
            Logger.info("Initialize Prod Environment");
        }
    }
}
