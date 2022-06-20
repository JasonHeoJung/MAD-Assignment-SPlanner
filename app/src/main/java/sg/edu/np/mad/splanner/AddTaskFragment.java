package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskFragment extends Fragment {

    MainActivity mainActivity;
    private Fragment fragment;
    private EditText taskName;
    private Button addTask;
    private Button cancelAddTask;
    private EditText date;
    private Bundle b;
    private Task t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_task, container, false);
        mainActivity = (MainActivity) getActivity();
        addTask = v.findViewById(R.id.addTask);
        cancelAddTask = v.findViewById(R.id.cancelAddTask);
        taskName = v.findViewById(R.id.taskName);
        date = v.findViewById(R.id.dueDate);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new ToDoListFragment();
                String name = taskName.getText().toString();
                String dueDate = date.getText().toString();
                t = new Task(name, dueDate);
//                b = new Bundle();
//                b.putBoolean("status", true);
//                fragment.setArguments(b);
                mainActivity.taskList1.setTaskList(t);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        cancelAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new ToDoListFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}