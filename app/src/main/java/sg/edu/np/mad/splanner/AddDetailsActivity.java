package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class AddDetailsActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    int hour, min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);


        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        EditText etModule = findViewById(R.id.editmodname);
        EditText etClass = findViewById(R.id.editclassname);
        EditText etTime = findViewById(R.id.edittime);
        etTime.setInputType(InputType.TYPE_NULL);
        etTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedhour, int selectedMinute) {
                        hour = selectedhour;
                        min = selectedMinute;
                        etTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, min));

                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddDetailsActivity.this, onTimeSetListener, hour, min, false);
                timePickerDialog.setTitle("Select Time");

                timePickerDialog.show();
            }
        });



        Button addEventBtn = findViewById(R.id.add);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String moduleName = etModule.getText().toString();
                String className = etClass.getText().toString();
                String time = etTime.getText().toString();
                Toast.makeText(getApplicationContext(), "You have not filled up one or more values", Toast.LENGTH_LONG).show();
                if(moduleName.length() == 0 || className.length() == 0 || time.length() == 0){
                    if(moduleName.length() == 0 ){
                        etModule.setError("Empty");
                    }
                    if(className.length() == 0){
                        etClass.setError("Empty");
                    }
                    if(time.length() == 0){
                        etTime.setError("Empty");
                    }
                }

                else{
                    DatabaseReference scheduleRef = reference.child(auth.getCurrentUser().getUid()).child("schedule").child(getIntent().getStringExtra("day")).push();
                    scheduleRef.setValue(new Event(moduleName, className, time));
                    finish();
                }


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