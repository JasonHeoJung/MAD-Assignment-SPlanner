package sg.edu.np.mad.splanner.ui.home.schedule;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import sg.edu.np.mad.splanner.R;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    TextView event_title;
    TextView event_time;
    TextView event_location;

    public ScheduleViewHolder(View v) {
        super(v);
        event_title = v.findViewById(R.id.event_title);
        event_time = v.findViewById(R.id.event_time);
        event_location = v.findViewById(R.id.event_location);
    }
}
