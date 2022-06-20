package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class TrackerRecyclerView extends RecyclerView.Adapter<TrackerRecyclerView.myViewHolder> {
    ArrayList<Score> scoreList;

    public TrackerRecyclerView(ArrayList<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView subjName;
        private TextView grade;
        private TextView title;
        private TextView title2;

        public myViewHolder(View v) {
            super (v);

            subjName = v.findViewById(R.id.sName);
            grade = v.findViewById(R.id.grade1);
            title = v.findViewById(R.id.a);
            title2 = v.findViewById(R.id.b);
        }
    }

    @NonNull
    @Override
    public TrackerRecyclerView.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tracker_recycler_view, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerRecyclerView.myViewHolder holder, int position) {
        String name = scoreList.get(position).getSubject();
        String g1 = scoreList.get(position).getGrade();

        if (position >= 1) {
            holder.title.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
        }

        holder.subjName.setText(name);
        holder.grade.setText(g1);
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }
}