package Model;

import java.sql.Date;
import java.util.List;

public class Task {

    private String description;
    private int priority;
    private Date deadline ;
    private Employee receptionist;

    private Janitor janitor;

    private boolean completed;

    public Task(String description, int priority, Date deadline, Employee receptionist) {
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.receptionist = receptionist;
    }

    public Janitor getJanitor() {
        return janitor;
    }

    public void setJanitors(Janitor janitor) {
        this.janitor = janitor;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Employee getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(Employee receptionist) {
        this.receptionist = receptionist;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
