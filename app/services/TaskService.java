package services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Task;
import play.data.Form;
import play.data.FormFactory;
import play.libs.Json;
import repositories.TaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.time.format.DateTimeFormatter;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskService extends Service
{
    private final TaskRepository taskRepository;
    private final FormFactory formFactory;

    @Inject
    public TaskService(
            TaskRepository taskRepository,
            FormFactory formFactory)
    {

        this.taskRepository = taskRepository;
        this.formFactory = formFactory;
    }

    public Form<Task> create()
    {
        Form<Task> taskForm = this.getForm().bindFromRequest();

        if (taskForm.hasErrors())
        {
            return taskForm;
        }

        Task task = taskForm.get();

        if (taskRepository.getById(task.getId()) != null)
        {
            taskForm.withError("id", "task already exists");
            return taskForm;
        }

        this.taskRepository.save(task);

        return taskForm.fill(task);

    }

    public Task read(Long id)
    {
        return this.taskRepository.getById(id);
    }


    private Form<Task> getForm()
    {
        return this.formFactory.form(Task.class);
    }

    public ObjectNode transform(Task task)
    {
        ObjectNode taskNode = Json.newObject();

        taskNode.put("id", task.getId());
        taskNode.put("name", task.getName());

        taskNode.put("schemaDefId", task.getSchemaDefId());
        taskNode.put("schemaDefName", task.getSchemaDef().getName());

        taskNode.put("text", task.getText());
        taskNode.put("referenceStatement", task.getReferenceStatement());
        taskNode.put("difficulty", task.getDifficulty());

        taskNode.put("createdAt", task.getCreatedAt().format(DateTimeFormatter.ISO_DATE_TIME));
        taskNode.put("modifiedAt", task.getModifiedAt().format(DateTimeFormatter.ISO_DATE_TIME));

        return taskNode;
    }
}