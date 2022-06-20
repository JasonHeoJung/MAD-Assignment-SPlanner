package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListViewHolder> {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    ArrayList<String> taskIds;
    ArrayList<Task> tasks;
    FragmentActivity fragmentActivity;

    public ToDoListAdapter(ArrayList<String> taskIds, ArrayList<Task> tasks, FragmentActivity fragmentActivity) {
        this.taskIds = taskIds;
        this.tasks = tasks;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_to_do_list_recycler_view, parent, false);
        return new ToDoListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = tasks.get(position).getTaskName();
        String dd = tasks.get(position).getDueDate();

        holder.taskName.setText(name);
        holder.dueDate.setText(dd);
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(auth.getCurrentUser().getUid()).child("tasks").child(taskIds.get(position)).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                Fragment fragment = new ToDoListFragment();
                                fragmentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskIds.size();
    }
}