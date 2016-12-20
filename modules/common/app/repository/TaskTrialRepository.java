package repository;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Singleton;
import models.TaskTrial;
import play.Logger;
import play.libs.Json;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskTrialRepository {
    private Model.Finder<Long, TaskTrial> find = new Model.Finder<>(TaskTrial.class);

    public List<TaskTrial> getAll() {
        return this.find.all();
    }

    public TaskTrial getById(Long id) {
        return this.find.byId(id);
    }

    public void save(TaskTrial taskTrial) {
        taskTrial.save();
    }

    /**
     * Did Not Save !!!11elf11!!1
     * @param taskTrial
     * @param jsonNode
     * @return
     */
    public TaskTrial refreshWithJson(TaskTrial taskTrial, JsonNode jsonNode) {
        TaskTrial taskTrial1 = Json.fromJson(jsonNode, TaskTrial.class);
        if(taskTrial1.getUserStatement() != null && !taskTrial1.getUserStatement().isEmpty()) {
            taskTrial.setUserStatement(
                    taskTrial1.getUserStatement()
                            .replaceAll("\n", " ")
                            .replaceAll("\t", " ")
                            .replaceAll("    ", " ")
                            .replaceAll("\\s+", " ")
                            .trim()
            );
        }
        taskTrial.setFinished(taskTrial1.isFinished());
        taskTrial.setSubmitDate(LocalDateTime.now());
        return taskTrial;
    }
}
