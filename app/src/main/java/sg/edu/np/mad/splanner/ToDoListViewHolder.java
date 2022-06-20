package sg.edu.np.mad.splanner;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ToDoListViewHolder extends RecyclerView.ViewHolder {
    TextView taskName;
    TextView dueDate;
    Button complete;

    public ToDoListViewHolder(View v) {
        super(v);
        taskName = v.findViewById(R.id.Task);
        dueDate = v.findViewById(R.id.DueDate);
        complete = v.findViewById(R.id.status);
    }
}