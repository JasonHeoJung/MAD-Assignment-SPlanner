package sg.edu.np.mad.splanner;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    TextView classtxt;
    TextView modtxt;
    TextView timetxt;

    public ScheduleViewHolder(final View view) {
        super(view);
        classtxt = view.findViewById(R.id.classname);
        modtxt = view.findViewById(R.id.modtitle);
        timetxt = view.findViewById(R.id.timetext);
    }
}
