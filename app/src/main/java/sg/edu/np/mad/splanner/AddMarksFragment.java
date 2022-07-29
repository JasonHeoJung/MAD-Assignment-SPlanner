package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddMarksFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private EditText subjectName;
    String item = "A+";
    String[] items = {"A+", "A", "B+", "B", "C+", "C", "D+", "D", "F"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

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
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);

        adapterItems = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.dropdown_item, items);

        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = adapterView.getItemAtPosition(i).toString();
            }
        });

        Fragment fragment = new TrackerFragment();

        Button addSubject = view.findViewById(R.id.addSubject);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjName = subjectName.getText().toString();

                DatabaseReference scoresRef = reference.child(auth.getCurrentUser().getUid()).child("scores").push();
                scoresRef.setValue(new Score(subjName, item));

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