package sg.edu.np.mad.splanner;

public class Task {
    String taskName;
    String dueDate;
    Boolean status;

    public Task() {
    }

    public Task(String taskName, String dueDate, Boolean status) {
        this.taskName = taskName;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
