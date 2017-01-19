package services;

import models.Task;
import play.data.Form;
import play.data.FormFactory;
import repository.TaskRepository;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author fabiomazzone
 */
@Singleton
public class TaskService extends Service {
    private final TaskRepository taskRepository;
    private final FormFactory formFactory;

    @Inject
    public TaskService(TaskRepository taskRepository, FormFactory formFactory) {

        this.taskRepository = taskRepository;
        this.formFactory = formFactory;
    }

    public Form<Task> createAsForm() {

        return this.formFactory.form(Task.class);
    }

    public Form<Task> create() {
        Form<Task> taskForm = this.getForm().bindFromRequest();

        if (taskForm.hasErrors()) {
            return taskForm;
        }

        Task task = taskForm.get();

        if (taskRepository.getById(task.getId()) != null) {
            taskForm.reject("id", "task already exists");
            return taskForm;
        }

        this.taskRepository.save(task);

        return taskForm.fill(task);

    }

    public Task read(Long id) {
        return this.taskRepository.getById(id);
    }

    public Form<Task> readAsForm(Long id) {
        Task task = this.read(id);

        if (task == null) {
            return null;
        }

        return this.formFactory.form(Task.class).fill(task);
    }

    public List<Task> readAll() {
        return this.taskRepository.getAll();
    }

    public Form<Task> createAsForm(Long id) {
        Task task = this.read(id);

        if (task == null) {
            return null;
        }

        return this.formFactory.form(Task.class).fill(task);
    }

    public Form<Task> update(Long id) {
        Task task = this.read(id);
        Form<Task> taskForm = this.getForm().bindFromRequest();

        if (task == null) {
            taskForm.reject(Service.formErrorNotFound, "Task Not Found");
            return taskForm;
        }

        if (taskForm.hasErrors()) {
            return taskForm;
        }

        if (taskForm.get().getName() == null || taskForm.get().getName().isEmpty()) {
            taskForm.reject("Name", "Task must be named");
            return taskForm;
        }

        task.setName(taskForm.get().getName());

        this.taskRepository.save(task);

        return taskForm;
    }

    public Form<Task> delete(Long id) {

        Task task = this.read(id);
        Form<Task> taskForm = this.getForm();

        if (task == null) {
            taskForm.reject(Service.formErrorNotFound, "Task Not Found");
            return taskForm;
        }

        task.delete();

        return taskForm;
    }

    private Form<Task> getForm() {
        return this.formFactory.form(Task.class);
    }
}