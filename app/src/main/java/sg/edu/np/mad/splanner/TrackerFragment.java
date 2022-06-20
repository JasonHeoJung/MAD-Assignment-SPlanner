package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TrackerFragment extends Fragment {
    Fragment fragment;
    MainActivity mainActivity;
    private RecyclerView recyclerView;
    private TextView a;
    private TextView b;
    private String score;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tracker, container, false);
        a = v.findViewById(R.id.title1);
        b = v.findViewById(R.id.weakest);
        mainActivity = (MainActivity) getActivity();
        if (mainActivity.scoreList1.getScoreList().size() == 0) {
            a.setVisibility(View.GONE);
            b.setVisibility(View.GONE);
        }
        else {
            for (int i = 0; i < mainActivity.scoreList1.getScoreList().size(); i++) {
                score = mainActivity.scoreList1.getScoreList().get(i).grade;
                if (score.toLowerCase().equals("f")) {
                    b.setText(mainActivity.scoreList1.getScoreList().get(i).subject);
                }
                else if (score.toLowerCase().equals("d")) {
                    b.setText(mainActivity.scoreList1.getScoreList().get(i).subject);
                }
                else if (score.toLowerCase().equals("c")) {
                    b.setText(mainActivity.scoreList1.getScoreList().get(i).subject);
                }
                else if (score.toLowerCase().equals("b")) {
                    b.setText(mainActivity.scoreList1.getScoreList().get(i).subject);
                }
                else if (score.toLowerCase().equals("a")) {
                    b.setText(mainActivity.scoreList1.getScoreList().get(i).subject);
                }
            }
            a.setVisibility(View.VISIBLE);
            b.setVisibility(View.VISIBLE);

        }
        recyclerView = v.findViewById(R.id.resultList);
        Button addMarks = v.findViewById(R.id.addMarks);
        addMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AddMarksFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        setAdapter();
        // Inflate the layout for this fragment
        return v;
    }

    public void setAdapter() {
        TrackerRecyclerView adapter = new TrackerRecyclerView(mainActivity.scoreList1.getScoreList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}