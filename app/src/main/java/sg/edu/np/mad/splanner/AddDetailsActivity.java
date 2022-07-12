package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

import java.util.Calendar;
import java.util.Locale;

public class AddDetailsActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    int hour, min;
    int dayofweek;
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
                    Toast.makeText(getApplicationContext(), "You have not filled up one or more values", Toast.LENGTH_LONG).show();
                }
                else{
                    DatabaseReference scheduleRef = reference.child(auth.getCurrentUser().getUid()).child("schedule").child(getIntent().getStringExtra("day")).push();
                    if(getIntent().getStringExtra("day") == "Monday"){
                        dayofweek= Calendar.MONDAY;
                    }
                    else if(getIntent().getStringExtra("day") == "Tuesday"){
                        dayofweek = Calendar.TUESDAY;
                    }
                    else if(getIntent().getStringExtra("day") == "Wednesday"){
                        dayofweek = Calendar.WEDNESDAY;
                    }
                    else if(getIntent().getStringExtra("day") == "Thursday"){
                        dayofweek = Calendar.THURSDAY;
                    }
                    else{
                        dayofweek = Calendar.FRIDAY;
                    }


                    scheduleRef.setValue(new Event(moduleName, className, time));
                    Intent intent = new Intent(AddDetailsActivity.this, Notification.class);
                    intent.putExtra("NotificationID", 1);
                    intent.putExtra("mod", moduleName);
                    intent.putExtra("class", className);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(AddDetailsActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                    switch(view.getId()){
                        case R.id.addsched:
                            int h = hour;
                            int m = min;

                            Calendar starttime = Calendar.getInstance();
                            starttime.set(Calendar.DAY_OF_WEEK, dayofweek);
                            starttime.set(Calendar.HOUR_OF_DAY, h);
                            starttime.set(Calendar.MINUTE, m);
                            starttime.set(Calendar.SECOND, 0);
                            long alarmStartTime = starttime.getTimeInMillis();

                            alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                    }
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