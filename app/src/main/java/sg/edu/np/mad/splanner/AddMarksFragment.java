package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMarksFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private EditText subjectName;
    private EditText grade;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_marks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        subjectName = view.findViewById(R.id.subjectName);
        grade = view.findViewById(R.id.grade);
        Fragment fragment = new TrackerFragment();

        Button addSubject = view.findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjName = subjectName.getText().toString();
                String grade1 = grade.getText().toString();

                DatabaseReference scoresRef = reference.child(auth.getCurrentUser().getUid()).child("scores").push();
                scoresRef.setValue(new Score(subjName, grade1));

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });

        Button cancelAddSubject = view.findViewById(R.id.cancelAddSubject);
        cancelAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
    }
}