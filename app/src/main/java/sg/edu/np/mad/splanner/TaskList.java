package sg.edu.np.mad.splanner;

import java.util.ArrayList;

public class TaskList {
    ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(Task t) {
        this.taskList.add(t);
    }
}
