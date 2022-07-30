package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRecordActivity extends AppCompatActivity {
    private EditText addName;
    private EditText addComment;
    private Button addRecordbtn;
    private Button cancelBtn;
    private TextView timeTaken;
    private TextView timeSet;

    private String getName;
    private String getComment;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        addName = findViewById(R.id.addName);
        addComment = findViewById(R.id.addComment);
        addRecordbtn = findViewById(R.id.addRecord);
        cancelBtn = findViewById(R.id.cancelbtn);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        Intent receiving = getIntent();
        Integer timeValue = receiving.getIntExtra("timeSet", 0);
        Integer timeUsed = Integer.valueOf(receiving.getStringExtra("timeTaken"));
        timeTaken = findViewById(R.id.timeTaken);
        timeSet = findViewById(R.id.timeSet);
        timeSet.setText(timeValue + " min");

        if (timeUsed < 60){
            timeTaken.setText(timeUsed + " s");
        }
        else {
            Integer minutes = timeUsed/60;
            Integer seconds = timeUsed%60;
            timeTaken.setText(minutes + " min " + seconds + " s");
        }



        addRecordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getName = addName.getText().toString();
                getComment = addComment.getText().toString();
                if(getName.length() == 0 || getComment.length() == 0){
                    Toast.makeText(getApplicationContext(), "You have not fill up one or more values", Toast.LENGTH_LONG).show();
                    if(getName.length() == 0){
                        addName.setError("Empty");
                    }
                    if(getComment.length() == 0){
                        addComment.setError("Empty");
                    }
                }
                else {
                    Toast.makeText(AddRecordActivity.this, "Time Recorded", Toast.LENGTH_SHORT).show();
                    DatabaseReference tasksRef = reference.child(auth.getCurrentUser().getUid()).child("records").push();
                    tasksRef.setValue(new Record(getName, getComment, String.valueOf(timeValue), String.valueOf(timeUsed)));
                    finish();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}