package repository;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Singleton;
import models.Task;
import models.TaskTrials;
import play.Configuration;
import play.Logger;
import play.libs.Json;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskTrialRepository {
    private final TaskRepository    taskRepository;
    private final Configuration configuration;
    private final Random            random;

    private Model.Finder<Long, TaskTrials> find = new Model.Finder<>(TaskTrials.class);

    @Inject
    public TaskTrialRepository(
            TaskRepository taskRepository,
            Configuration configuration) {

        this.taskRepository = taskRepository;
        this.configuration = configuration;

        this.random = new Random();
    }


    public List<TaskTrials> getAll() {
        return this.find.all();
    }

    public TaskTrials getById(Long id) {
        return this.find.byId(id);
    }

    public TaskTrials create() {
        TaskTrials taskTrials;
        Task task;

        taskTrials = new TaskTrials();

        task        = taskRepository.getRandomTask();

        taskTrials.setTask(task);

        taskTrials.databaseInformation.setSeed(Math.abs(this.random.nextLong()));
        taskTrials.databaseInformation.setDriver(this.getDatabaseDriver());
        taskTrials.databaseInformation.setPath(this.getDatabasePath());
        taskTrials.databaseInformation.setName(this.getDatabaseName(taskTrials));
        taskTrials.databaseInformation.setUrl(this.getDatabaseUrl(taskTrials));

        return taskTrials;
    }

    public void save(TaskTrials taskTrials) {
        taskTrials.save();
    }

    private String getDatabaseDriver() {
        return this.configuration.getString("sqlParser.driver");
    }

    private String getDatabasePath() {
        return this.configuration.getString("sqlParser.path");
    }

    private String getDatabaseName(TaskTrials taskTrials) {
        return taskTrials.getCreatedAt()
                + "-"
                + taskTrials.getTask().getSchemaDef().getId()
                + "-"
                + taskTrials.getTaskId()
                + "-"
                + taskTrials.databaseInformation.getSeed();
    }

    private String getDatabaseUrl(TaskTrials taskTrials) {
        String databaseUrl = this.configuration.getString("sqlParser.urlPrefix")
                + taskTrials.databaseInformation.getPath()
                + taskTrials.databaseInformation.getName();
        Logger.debug(databaseUrl);
        return databaseUrl;
    }




    /**
     * Did Not Save !!!11elf11!!1
     * @param taskTrials
     * @param jsonNode
     * @return
     */
    public TaskTrials refreshWithJson(TaskTrials taskTrials, JsonNode jsonNode) {
        if (taskTrials.getIsFinished()) {
            return taskTrials;
        }

        TaskTrials taskTrials1 = Json.fromJson(jsonNode, TaskTrials.class);
        if(taskTrials1.getUserStatement() != null && !taskTrials1.getUserStatement().isEmpty()) {
            taskTrials.setUserStatement(
                    taskTrials1.getUserStatement()
                            .replaceAll("\n", " ")
                            .replaceAll("\t", " ")
                            .replaceAll("    ", " ")
                            .replaceAll("\\s+", " ")
                            .trim()
            );
        }
        taskTrials.setIsFinished(taskTrials1.getIsFinished());
        taskTrials.stats.setSubmitDate(LocalDateTime.now());
        return taskTrials;
    }
}
