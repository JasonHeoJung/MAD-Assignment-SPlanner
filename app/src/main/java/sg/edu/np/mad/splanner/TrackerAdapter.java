package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TrackerAdapter extends RecyclerView.Adapter<TrackerViewHolder> {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    ArrayList<String> scoreIds;
    ArrayList<Score> scores;
    FragmentActivity fragmentActivity;

    public TrackerAdapter(ArrayList<String> scoreIds, ArrayList<Score> scores, FragmentActivity fragmentActivity) {
        this.scoreIds = scoreIds;
        this.scores = scores;
        this.fragmentActivity = fragmentActivity;
    }

    @NonNull
    @Override
    public TrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        auth = FirebaseAuth.getInstance();
//        reference = FirebaseDatabase.getInstance().getReference("users");

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tracker_recycler_view, parent, false);
        return new TrackerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerViewHolder holder, int position) {
        String name = scores.get(position).getSubject();
        String g1 = scores.get(position).getGrade();

        holder.subjName.setText(name);
        holder.grade.setText(g1);
    }

    @Override
    public int getItemCount() {
        return scoreIds.size();
    }
}