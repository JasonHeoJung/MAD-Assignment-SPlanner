package sg.edu.np.mad.splanner.ui.home.task;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import sg.edu.np.mad.splanner.R;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    MaterialCardView task_view;
    TextView task_title;
    TextView task_due;
    CheckBox task_status;

    public TaskViewHolder(View v) {
        super(v);
        task_view = v.findViewById(R.id.taskMaterialCardView);
        task_title = v.findViewById(R.id.task_title);
        task_due = v.findViewById(R.id.task_due);
        task_status = v.findViewById(R.id.task_status);
    }
}
