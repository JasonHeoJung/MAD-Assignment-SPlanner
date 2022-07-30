package sg.edu.np.mad.splanner.ui.examtracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Score;
import sg.edu.np.mad.splanner.databinding.FragmentAddMarksBinding;

public class AddMarksFragment extends Fragment {

    private FragmentAddMarksBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private EditText subjectName;
    String item = "A+";
    String[] items = {"A+", "A", "B+", "B", "C+", "C", "D+", "D", "F"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddMarksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        subjectName = root.findViewById(R.id.subjectName);
        autoCompleteTextView = root.findViewById(R.id.autoCompleteTextView);

        adapterItems = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.dropdown_item, items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
            }
        });

        Fragment fragment = new ExamtrackerFragment();

        Button addSubject = root.findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjName = subjectName.getText().toString();

                DatabaseReference scoresRef = reference.child(auth.getCurrentUser().getUid()).child("scores").push();
                scoresRef.setValue(new Score(subjName, item));

                getParentFragmentManager().beginTransaction().replace(R.id.ExamtrackerFrame, fragment).commit();
            }
        });

        Button cancelAddSubject = root.findViewById(R.id.cancelAddSubject);
        cancelAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.ExamtrackerFrame, fragment).commit();
            }
        });

        return root;
    }
}