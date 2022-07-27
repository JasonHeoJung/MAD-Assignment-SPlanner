package sg.edu.np.mad.splanner.ui.examtracker;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sg.edu.np.mad.splanner.AddMarksFragment;
import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Score;
import sg.edu.np.mad.splanner.TrackerAdapter;
import sg.edu.np.mad.splanner.databinding.FragmentExamtrackerBinding;

public class ExamtrackerFragment extends Fragment {

    private FragmentExamtrackerBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ArrayList<String> scoreIds;
    private ArrayList<Score> scores;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentExamtrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        recyclerView = view.findViewById(R.id.resultList);

//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        Button addMarks = view.findViewById(R.id.addMarks);
        addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment = new AddMarksFragment();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
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
//                if (scores.size() != 0) {
//                    for (int i = 0; i < scores.size(); i++) {
//                        String score = scores.get(i).grade;
//                        switch (score.toUpperCase()) {
//                            case "F":
//                            case "D":
//                            case "C":
//                            case "B":
//                            case "A":
//                                b.setText(scores.get(i).subject.toUpperCase());
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//                    a.setVisibility(View.VISIBLE);
//                    b.setVisibility(View.VISIBLE);
//                }
//                else {
//                    a.setVisibility(View.GONE);
//                    b.setVisibility(View.GONE);
//                }
                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setAdapter() {
        TrackerAdapter adapter = new TrackerAdapter(scoreIds, scores, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}