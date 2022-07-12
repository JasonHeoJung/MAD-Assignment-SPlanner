package sg.edu.np.mad.splanner;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecordViewHolder extends RecyclerView.ViewHolder {
    TextView recordName;
    TextView recordComment;
    TextView timeTaken;
    Button delete;

    public RecordViewHolder(final View view){
        super(view);
        recordName = view.findViewById(R.id.recordName);
        recordComment = view.findViewById(R.id.recordComment);
        timeTaken = view.findViewById(R.id.timeTaken);
        delete = view.findViewById(R.id.delete);
    }
}
