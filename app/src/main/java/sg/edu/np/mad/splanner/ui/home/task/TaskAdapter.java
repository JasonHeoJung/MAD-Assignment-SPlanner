package sg.edu.np.mad.splanner.ui.home.task;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Task;

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
        holder.task_status.setChecked(tasks.get(position).getStatus());
        holder.task_view.setOnClickListener(v -> {
            holder.task_status.toggle();
            reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("tasks").child(taskIds.get(position)).child("status").setValue(holder.task_status.isChecked());

            ProgressBar task_progress_bar = fragmentActivity.findViewById(R.id.task_progress_bar);
            TextView task_progress_text = fragmentActivity.findViewById(R.id.task_progress_text);

            int change = holder.task_status.isChecked() ? 1 : -1;

            task_progress_text.setText(String.format("%s out of %d tasks done", String.valueOf(Math.round(task_progress_bar.getProgress() / (double)100 * taskIds.size() + change)), taskIds.size()));
            task_progress_bar.setProgress((int) Math.round((task_progress_bar.getProgress() / (double)100 * taskIds.size() + change) * 100 / taskIds.size()));
        });
        holder.task_status.setOnClickListener(v -> {
            reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("tasks").child(taskIds.get(position)).child("status").setValue(holder.task_status.isChecked());

            ProgressBar task_progress_bar = fragmentActivity.findViewById(R.id.task_progress_bar);
            TextView task_progress_text = fragmentActivity.findViewById(R.id.task_progress_text);

            int change = holder.task_status.isChecked() ? 1 : -1;

            task_progress_text.setText(String.format("%s out of %d tasks done", String.valueOf(Math.round(task_progress_bar.getProgress() / (double)100 * taskIds.size() + change)), taskIds.size()));
            task_progress_bar.setProgress((int) Math.round((task_progress_bar.getProgress() / (double)100 * taskIds.size() + change) * 100 / taskIds.size()));
        });
    }

    @Override
    public int getItemCount() {
        return taskIds.size();
    }
}
