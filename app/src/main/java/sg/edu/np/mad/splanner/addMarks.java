package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class addMarks extends Fragment {

    private Fragment fragment;
    private Button addSubject;
    private Button cancelAddSubject;
    private EditText subjectName;
    private EditText grade;
    private score s;
    private MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_marks, container, false);
        mainActivity = (MainActivity) getActivity();
        addSubject = v.findViewById(R.id.addSubject);
        cancelAddSubject = v.findViewById(R.id.cancelAddSubject);
        subjectName = v.findViewById(R.id.subjectName);
        grade = v.findViewById(R.id.grade);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new TrackerFragment();
                String subjName = subjectName.getText().toString();
                String grade1 = grade.getText().toString();
                s = new score(subjName, grade1);
                mainActivity.scoreList1.setScoreList(s);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        cancelAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new TrackerFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}