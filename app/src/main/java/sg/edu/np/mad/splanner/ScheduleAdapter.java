package sg.edu.np.mad.splanner;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {
    private final ArrayList<Event> schedule;
    FragmentActivity fragmentActivity;
    public ScheduleAdapter(ArrayList<Event> schedule, FragmentActivity fragmentActivity){

        this.schedule = schedule;
    }

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lists, parent, false);
        return new ScheduleViewHolder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull final ScheduleViewHolder holder, final int position) {
        holder.modtxt.setText(schedule.get(position).getModule());
        holder.classtxt.setText(schedule.get(position).getLocation());
        holder.timetxt.setText(schedule.get(position).getTiming());



    }

    @Override
    public int getItemCount() {
        return schedule.size();
    }

}

