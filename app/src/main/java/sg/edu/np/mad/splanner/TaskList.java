package sg.edu.np.mad.splanner;

import java.util.ArrayList;

public class TaskList {
    ArrayList<task> taskList;

    public TaskList(ArrayList<task> taskList) {
        this.taskList = taskList;
    }

    public ArrayList<task> getTaskList() {
        return taskList;
    }

    public void setTaskList(task t) {
        this.taskList.add(t);
    }
}
