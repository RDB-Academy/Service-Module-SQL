package repository;

import com.avaje.ebean.Model;
import com.google.inject.Singleton;
import models.Task;
import models.TaskTrial;
import play.Configuration;
import play.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskTrialRepository {
    private final Configuration configuration;
    private final Random            random;

    private Model.Finder<Long, TaskTrial> find = new Model.Finder<>(TaskTrial.class);

    @Inject
    public TaskTrialRepository(Configuration configuration) {
        this.configuration = configuration;

        this.random = new Random();
    }


    public List<TaskTrial> getAll() {
        return this.find.all();
    }

    public TaskTrial getById(Long id) {
        return this.find.byId(id);
    }

    public TaskTrial create(Task task) {
        TaskTrial taskTrial;

        taskTrial   = new TaskTrial();

        taskTrial.setTask(task);

        taskTrial.databaseInformation.setSeed(Math.abs(this.random.nextLong()));
        taskTrial.databaseInformation.setDriver(this.getDatabaseDriver());
        taskTrial.databaseInformation.setPath(this.getDatabasePath());
        taskTrial.databaseInformation.setName(this.getDatabaseName(taskTrial));
        taskTrial.databaseInformation.setUrl(this.getDatabaseUrl(taskTrial));

        return taskTrial;
    }

    public void save(TaskTrial taskTrial) {
        taskTrial.save();
    }

    private String getDatabaseDriver() {
        return this.configuration.getString("sqlParser.driver");
    }

    private String getDatabasePath() {
        return this.configuration.getString("sqlParser.path");
    }

    private String getDatabaseName(TaskTrial taskTrial) {
        return taskTrial.getCreatedAt()
                + "-"
                + taskTrial.getTask().getSchemaDef().getId()
                + "-"
                + taskTrial.getTaskId()
                + "-"
                + taskTrial.databaseInformation.getSeed();
    }

    private String getDatabaseUrl(TaskTrial taskTrial) {
        String databaseUrl = this.configuration.getString("sqlParser.urlPrefix")
                + taskTrial.databaseInformation.getPath()
                + taskTrial.databaseInformation.getName();

        Logger.debug(databaseUrl);
        return databaseUrl;
    }
}
