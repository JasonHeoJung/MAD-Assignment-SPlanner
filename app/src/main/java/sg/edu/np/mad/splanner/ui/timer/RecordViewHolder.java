package sg.edu.np.mad.splanner.ui.timer;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import sg.edu.np.mad.splanner.R;

public class RecordViewHolder extends RecyclerView.ViewHolder {
    TextView recordName;
    TextView recordComment;
    TextView timeSet;
    TextView timeTaken;
    Button delete;

    public RecordViewHolder(final View view){
        super(view);
        recordName = view.findViewById(R.id.recordName);
        recordComment = view.findViewById(R.id.recordComment);
        timeSet = view.findViewById(R.id.timeSet);
        timeTaken = view.findViewById(R.id.timeTaken);
        delete = view.findViewById(R.id.delete);
    }
}
