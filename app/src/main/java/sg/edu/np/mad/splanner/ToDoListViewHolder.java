package sg.edu.np.mad.splanner;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ToDoListViewHolder extends RecyclerView.ViewHolder {
    TextView taskName;
    TextView dueDate;
    TextView title;
    TextView title2;
    Button complete;

    public ToDoListViewHolder(View v) {
        super(v);
        taskName = v.findViewById(R.id.Task);
        dueDate = v.findViewById(R.id.DueDate);
        title = v.findViewById(R.id.textView11);
        title2 = v.findViewById(R.id.textView12);
        complete = v.findViewById(R.id.status);
    }
}