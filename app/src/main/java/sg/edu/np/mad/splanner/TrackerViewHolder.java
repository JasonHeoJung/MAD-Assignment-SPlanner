package sg.edu.np.mad.splanner;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TrackerViewHolder extends RecyclerView.ViewHolder {
    TextView subjName;
    TextView grade;
    TextView title;
    TextView title2;
    Button remove;

    public TrackerViewHolder(View v) {
        super(v);
        subjName = v.findViewById(R.id.sName);
        grade = v.findViewById(R.id.grade1);
        title = v.findViewById(R.id.a);
        title2 = v.findViewById(R.id.b);
        remove = v.findViewById(R.id.deleteGrade);
    }
}
