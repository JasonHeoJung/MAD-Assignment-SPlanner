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
        recyclerView = view.findViewById(R.id.resultList);
        a = view.findViewById(R.id.weakestSubj);
        b = view.findViewById(R.id.weakestSubjName);


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
                for (DataSnapshot taskSnapshot: snapshot.getChildren()) {
                    scoreIds.add(taskSnapshot.getKey());
                    scores.add(taskSnapshot.getValue(Score.class));
                }
                if (scores.size() != 0) {
                    for (int i = 0; i < scores.size(); i++) {
                        if (scores.get(i).grade.equals("F")) {
                            F.add(scores.get(i));
                        }
                        else if (scores.get(i).grade.equals("D")) {
                            D.add(scores.get(i));
                        }
                        else if (scores.get(i).grade.equals("D+")) {
                            DPlus.add(scores.get(i));
                        }
                        else if (scores.get(i).grade.equals("C")) {
                            C.add(scores.get(i));
                        }
                        else if (scores.get(i).grade.equals("C+")) {
                            CPlus.add(scores.get(i));
                        }
                        else if (scores.get(i).grade.equals("B")) {
                            B.add(scores.get(i));
                        }
                        else if (scores.get(i).grade.equals("B+")) {
                            BPlus.add(scores.get(i));
                        }
                        else if (scores.get(i).grade.equals("A")) {
                            A.add(scores.get(i));
                        }
                        else if (scores.get(i).grade.equals("A+")) {
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
                        if (organized.get(i).grade.equals("F")) {
                            b.setText(organized.get(i).subject);
                            break;
                        }
                        else if (organized.get(i).grade.equals("D")) {
                            b.setText(organized.get(i).subject);
                            break;
                        }
                        else if (organized.get(i).grade.equals("D+")) {
                            b.setText(organized.get(i).subject);
                            break;
                        }
                        else if (organized.get(i).grade.equals("C")) {
                            b.setText(organized.get(i).subject);
                            break;
                        }
                        else if (organized.get(i).grade.equals("C+")) {
                            b.setText(organized.get(i).subject);
                            break;
                        }
                        else if (organized.get(i).grade.equals("B")) {
                            b.setText(organized.get(i).subject);
                            break;
                        }
                        else if (organized.get(i).grade.equals("B+")) {
                            b.setText(organized.get(i).subject);
                            break;
                        }
                        else if (organized.get(i).grade.equals("A")) {
                            b.setText(organized.get(i).subject);
                            break;
                        }
                        else if (organized.get(i).grade.equals("A+")) {
                            b.setText(organized.get(i).subject);
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