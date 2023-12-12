package Control;

import Database.ConnectionEnvironment;
import Database.TaskDAO;
import Model.Employee;
import Model.Janitor;
import Model.Task;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TaskController {

    private Task currentTask;

    private EmployeeController employeeController;
    private TaskDAO taskDAO;

    private ConnectionEnvironment env;

    public TaskController(ConnectionEnvironment env) {
        this.env = env;

        employeeController = new EmployeeController(env);
        taskDAO = new TaskDAO(env);
    }

    public Task createTask(String description, int priority, Employee receptionist, Date deadline) {
        currentTask = new Task(0, description, priority, deadline, receptionist, null);
        return currentTask;
    }

    public List<Janitor> getAllJanitors() {
        List<Janitor> janitors = null;

        janitors = employeeController.getAllJanitors();

        return janitors;

    }

    public void assignJanitorToTask(Janitor janitor) {
        currentTask.setJanitors(janitor);
    }

    public boolean finishTask() {

        boolean result = false;

        if (currentTask != null && currentTask.getDescription() != null && currentTask.getPriority() > 0 && currentTask.getDeadline() != null && currentTask.getReceptionist() != null && currentTask.getJanitor() != null) {
            result = taskDAO.saveTask(currentTask);
        }

        return result;
    }

    public List<Task> getUncompletedTasks(int janitorId) {
        return taskDAO.getUncompletedTasks(janitorId, true);
    }

    public boolean finishTask(Task task) {
        return taskDAO.finishTask(task.getId());
    }

}
