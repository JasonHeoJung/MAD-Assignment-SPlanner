package sg.edu.np.mad.splanner.ui.examtracker;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Score;

public class TrackerAdapter extends RecyclerView.Adapter<TrackerViewHolder> {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    ArrayList<String> scoreIds;
    ArrayList<Score> scores;
    FragmentManager fragmentManager;

    public TrackerAdapter(ArrayList<String> scoreIds, ArrayList<Score> scores, FragmentManager fragmentManager) {
        this.scoreIds = scoreIds;
        this.scores = scores;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public TrackerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tracker_recycler_view, parent, false);
        return new TrackerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = scores.get(position).getSubject();
        String g1 = scores.get(position).getGrade();

        holder.subjName.setText(name);
        holder.grade.setText(g1);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(auth.getCurrentUser().getUid()).child("scores").child(scoreIds.get(position)).removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                                Fragment fragment = new ExamtrackerFragment();
                                fragmentManager.beginTransaction().replace(R.id.ExamtrackerFrame, fragment).commit();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return scoreIds.size();
    }
}