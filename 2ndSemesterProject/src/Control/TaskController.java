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

    public Task createTask(String description, int priority, Date deadline, Employee receptionist) {
        currentTask = new Task(description, priority, deadline, receptionist);
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
        return taskDAO.saveTask(currentTask);
    }

}
