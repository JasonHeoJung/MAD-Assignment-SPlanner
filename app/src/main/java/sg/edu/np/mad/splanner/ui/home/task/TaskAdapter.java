package sg.edu.np.mad.splanner.ui.home.task;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Task;
import sg.edu.np.mad.splanner.ToDoListFragment;
import sg.edu.np.mad.splanner.ToDoListViewHolder;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    ArrayList<String> taskIds;
    ArrayList<Task> tasks;
    FragmentActivity fragmentActivity;

    public TaskAdapter(ArrayList<String> taskIds, ArrayList<Task> tasks, FragmentActivity fragmentActivity) {
        this.taskIds = taskIds;
        this.tasks = tasks;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_task, parent, false);

        return new TaskViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = tasks.get(position).getTaskName();
        String dd = tasks.get(position).getDueDate();

        holder.task_title.setText(name);
        holder.task_due.setText(dd);
    }

    @Override
    public int getItemCount() {
        return taskIds.size();
    }
}
