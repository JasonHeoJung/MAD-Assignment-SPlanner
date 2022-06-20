package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddTaskFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private EditText taskName;
    private EditText date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        taskName = view.findViewById(R.id.taskName);
        date = view.findViewById(R.id.dueDate);
        Fragment fragment = new ToDoListFragment();

        Button addTask = view.findViewById(R.id.addTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = taskName.getText().toString();
                String dueDate = date.getText().toString();

                DatabaseReference tasksRef = reference.child(auth.getCurrentUser().getUid()).child("tasks").push();
                tasksRef.setValue(new Task(name, dueDate));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        Button cancelAddTask = view.findViewById(R.id.cancelAddTask);
        cancelAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
    }
}