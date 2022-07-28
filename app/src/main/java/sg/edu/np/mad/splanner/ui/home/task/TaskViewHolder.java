package sg.edu.np.mad.splanner.ui.home.task;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import sg.edu.np.mad.splanner.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView task_title;
    TextView task_due;

    public TaskViewHolder(View v) {
        super(v);
        task_title = v.findViewById(R.id.task_title);
        task_due = v.findViewById(R.id.task_due);
    }
}
