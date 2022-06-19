package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDetailsActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        EditText etModule = findViewById(R.id.editmodname);
        EditText etClass = findViewById(R.id.editclassname);
        EditText etTime = findViewById(R.id.edittime);

        Button addEventBtn = findViewById(R.id.add);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moduleName = etModule.getText().toString();
                String className = etClass.getText().toString();
                String time = etTime.getText().toString();

                DatabaseReference scheduleRef = reference.child(auth.getCurrentUser().getUid()).child("schedule").child(getIntent().getStringExtra("day")).push();
                scheduleRef.setValue(new Event(moduleName, className, time));

                finish();
            }
        });

        Button cancelBtn = findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}