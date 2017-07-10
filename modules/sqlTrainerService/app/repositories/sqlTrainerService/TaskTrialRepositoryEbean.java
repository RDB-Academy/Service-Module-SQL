package repositories.sqlTrainerService;

import com.google.inject.Singleton;
import com.typesafe.config.Config;
import io.ebean.Finder;
import models.sqlTrainerService.Task;
import models.sqlTrainerService.TaskTrial;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

/**
 * @author Fabio Mazzone
 */
@Singleton
public class TaskTrialRepositoryEbean implements TaskTrialRepository
{
    private final Config config;
    private final Random random;

    private Finder<Long, TaskTrial> find = new Finder<>(TaskTrial.class);

    @Inject
    public TaskTrialRepositoryEbean(Config config)
    {
        this.config = config;

        this.random = new Random();
    }


    public List<TaskTrial> getAll()
    {
        return this.find.all();
    }

    public TaskTrial getById(Long id)
    {
        return this.find.byId(id);
    }

    public TaskTrial create(Task task)
    {
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

    public void save(TaskTrial taskTrial)
    {
        this.find.db().save(taskTrial);
    }

    @Override
    public void delete(TaskTrial taskTrial)
    {
        this.find.db().delete(taskTrial);
    }

    private String getDatabaseDriver() {
        return this.config.getString("sqlParser.driver");
    }

    private String getDatabasePath() {
        return this.config.getString("sqlParser.path");
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
        return this.config.getString("sqlParser.urlPrefix")
                + taskTrial.databaseInformation.getPath()
                + taskTrial.databaseInformation.getName();
    }
}
