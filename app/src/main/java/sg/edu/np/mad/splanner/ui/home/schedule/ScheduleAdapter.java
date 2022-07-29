package sg.edu.np.mad.splanner.ui.home.schedule;

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

import sg.edu.np.mad.splanner.Event;
import sg.edu.np.mad.splanner.R;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    ArrayList<String> scheduleIds;
    ArrayList<Event> schedule;
    FragmentActivity fragmentActivity;

    public ScheduleAdapter(ArrayList<String> scheduleIds, ArrayList<Event> schedule, FragmentActivity fragmentActivity) {
        this.scheduleIds = scheduleIds;
        this.schedule = schedule;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_schedule, parent, false);

        return new ScheduleViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = schedule.get(position).getModule();
        String time = schedule.get(position).getTiming();
        String location = schedule.get(position).getLocation();


        holder.event_title.setText(name);
        holder.event_time.setText(time);
        holder.event_time.setText(location);

//        holder.task_status.setChecked(tasks.get(position).getStatus());
//        holder.task_view.setOnClickListener(v -> {
//            holder.task_status.toggle();
//            reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("tasks").child(taskIds.get(position)).child("status").setValue(holder.task_status.isChecked());
//
//            ProgressBar task_progress_bar = fragmentActivity.findViewById(R.id.task_progress_bar);
//            TextView task_progress_text = fragmentActivity.findViewById(R.id.task_progress_text);
//
//            int change = holder.task_status.isChecked() ? 1 : -1;
//
//            task_progress_text.setText(String.format("%s out of %d tasks done", String.valueOf(Math.round(task_progress_bar.getProgress() / (double)100 * taskIds.size() + change)), taskIds.size()));
//            task_progress_bar.setProgress((int) Math.round((task_progress_bar.getProgress() / (double)100 * taskIds.size() + change) * 100 / taskIds.size()));
//        });
//        holder.task_status.setOnClickListener(v -> {
//            reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("tasks").child(taskIds.get(position)).child("status").setValue(holder.task_status.isChecked());
//
//            ProgressBar task_progress_bar = fragmentActivity.findViewById(R.id.task_progress_bar);
//            TextView task_progress_text = fragmentActivity.findViewById(R.id.task_progress_text);
//
//            int change = holder.task_status.isChecked() ? 1 : -1;
//
//            task_progress_text.setText(String.format("%s out of %d tasks done", String.valueOf(Math.round(task_progress_bar.getProgress() / (double)100 * taskIds.size() + change)), taskIds.size()));
//            task_progress_bar.setProgress((int) Math.round((task_progress_bar.getProgress() / (double)100 * taskIds.size() + change) * 100 / taskIds.size()));
//        });
    }

    @Override
    public int getItemCount() {
        return scheduleIds.size();
    }
}