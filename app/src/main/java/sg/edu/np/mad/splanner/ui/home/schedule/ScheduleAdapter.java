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

    public ScheduleAdapter(ArrayList<String> scheduleIds, ArrayList<Event> schedule) {
        this.scheduleIds = scheduleIds;
        this.schedule = schedule;
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
        holder.event_location.setText(location);
    }

    @Override
    public int getItemCount() {
        return scheduleIds.size();
    }
}