package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListViewHolder> {
    ArrayList<Task> taskList;

    public ToDoListAdapter(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_to_do_list_recycler_view, parent, false);
        return new ToDoListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, int position) {
        String name = taskList.get(position).getTaskName();
        String dd = taskList.get(position).getDueDate();

        holder.taskName.setText(name);
        holder.dueDate.setText(dd);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}