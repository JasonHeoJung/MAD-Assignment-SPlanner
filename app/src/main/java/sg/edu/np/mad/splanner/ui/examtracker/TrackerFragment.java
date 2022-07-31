package sg.edu.np.mad.splanner.ui.examtracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Score;
import sg.edu.np.mad.splanner.databinding.FragmentTrackerBinding;

public class TrackerFragment extends Fragment {

    private FragmentTrackerBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ArrayList<String> scoreIds;
    private ArrayList<Score> scores;
    private TextView a;
    private TextView b;
    private boolean tracker = false;
    private ArrayList<Score> APlus;
    private ArrayList<Score> A;
    private ArrayList<Score> BPlus;
    private ArrayList<Score> B;
    private ArrayList<Score> CPlus;
    private ArrayList<Score> C;
    private ArrayList<Score> DPlus;
    private ArrayList<Score> D;
    private ArrayList<Score> F;
    private ArrayList<Score> organized;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        recyclerView = root.findViewById(R.id.resultList);
        a = root.findViewById(R.id.weakestSubj);
        b = root.findViewById(R.id.weakestSubjName);

        FloatingActionButton addMarks = root.findViewById(R.id.addMarks);
        addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.ExamtrackerFrame, new AddMarksFragment()).commit();
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        reference.child(auth.getCurrentUser().getUid()).child("scores").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scoreIds = new ArrayList<>();
                scores = new ArrayList<>();
                APlus = new ArrayList<>();
                A = new ArrayList<>();
                BPlus = new ArrayList<>();
                B = new ArrayList<>();
                CPlus = new ArrayList<>();
                C = new ArrayList<>();
                DPlus = new ArrayList<>();
                D = new ArrayList<>();
                F = new ArrayList<>();
                organized = new ArrayList<>();
                for (DataSnapshot taskSnapshot : snapshot.getChildren()) {
                    scoreIds.add(taskSnapshot.getKey());
                    scores.add(taskSnapshot.getValue(Score.class));
                }
                if (scores.size() != 0) {
                    for (int i = 0; i < scores.size(); i++) {
                        if (scores.get(i).getGrade().equals("F")) {
                            F.add(scores.get(i));
                        } else if (scores.get(i).getGrade().equals("D")) {
                            D.add(scores.get(i));
                        } else if (scores.get(i).getGrade().equals("D+")) {
                            DPlus.add(scores.get(i));
                        } else if (scores.get(i).getGrade().equals("C")) {
                            C.add(scores.get(i));
                        } else if (scores.get(i).getGrade().equals("C+")) {
                            CPlus.add(scores.get(i));
                        } else if (scores.get(i).getGrade().equals("B")) {
                            B.add(scores.get(i));
                        } else if (scores.get(i).getGrade().equals("B+")) {
                            BPlus.add(scores.get(i));
                        } else if (scores.get(i).getGrade().equals("A")) {
                            A.add(scores.get(i));
                        } else if (scores.get(i).getGrade().equals("A+")) {
                            APlus.add(scores.get(i));
                        }
                    }

                    organized.addAll(F);
                    organized.addAll(D);
                    organized.addAll(DPlus);
                    organized.addAll(C);
                    organized.addAll(CPlus);
                    organized.addAll(B);
                    organized.addAll(BPlus);
                    organized.addAll(A);
                    organized.addAll(APlus);

                    for (int i = 0; i < organized.size(); i++) {
                        if (organized.get(i).getGrade().equals("F")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        } else if (organized.get(i).getGrade().equals("D")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        } else if (organized.get(i).getGrade().equals("D+")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        } else if (organized.get(i).getGrade().equals("C")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        } else if (organized.get(i).getGrade().equals("C+")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        } else if (organized.get(i).getGrade().equals("B")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        } else if (organized.get(i).getGrade().equals("B+")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        } else if (organized.get(i).getGrade().equals("A")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        } else if (organized.get(i).getGrade().equals("A+")) {
                            b.setText(organized.get(i).getSubject());
                            break;
                        }
                    }

                    a.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);
                } else {
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
        TrackerAdapter adapter = new TrackerAdapter(scoreIds, scores, getParentFragmentManager());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}