package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrackerFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ArrayList<String> scoreIds;
    private ArrayList<Score> scores;
    private TextView a, b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tracker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        a = view.findViewById(R.id.title1);
        b = view.findViewById(R.id.weakest);
        recyclerView = view.findViewById(R.id.resultList);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        Button addMarks = view.findViewById(R.id.addMarks);
        addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new AddMarksFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        reference.child(auth.getCurrentUser().getUid()).child("scores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreIds = new ArrayList<>();
                scores = new ArrayList<>();
                for (DataSnapshot taskSnapshot: snapshot.getChildren()) {
                    scoreIds.add(taskSnapshot.getKey());
                    scores.add(taskSnapshot.getValue(Score.class));
                }
                if (scores.size() != 0) {
                    for (int i = 0; i < scores.size(); i++) {
                        String score = scores.get(i).grade;
                        switch (score.toUpperCase()) {
                            case "F":
                            case "D":
                            case "C":
                            case "B":
                            case "A":
                                b.setText(scores.get(i).subject.toUpperCase());
                                break;
                            default:
                                break;
                        }
                    }
                    a.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                }
                else {
                    a.setVisibility(View.GONE);
                    b.setVisibility(View.GONE);
                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setAdapter() {
        TrackerAdapter adapter = new TrackerAdapter(scoreIds, scores, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}