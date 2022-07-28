package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

import sg.edu.np.mad.splanner.databinding.ActivityMainBinding;

public class AddDetailsActivity extends AppCompatActivity {



    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private ActivityMainBinding binding;
    int hour, min;
    int dayofweek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);


        createNotificationChannel();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");


        EditText etModule = findViewById(R.id.editmodname);
        EditText etClass = findViewById(R.id.editclassname);
        EditText etTime = findViewById(R.id.edittime);
        Button et = findViewById(R.id.timebtn);

        et.setOnClickListener(new View.OnClickListener() {

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



        Button addEventBtn = findViewById(R.id.Save);
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
                    calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR, hour);
                    calendar.set(Calendar.MINUTE, min);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);



                    scheduleRef.setValue(new Event(moduleName, className, time));
                    setAlarm();

                    finish();
                }


            }


        });


        Button cancelBtn = findViewById(R.id.cancelbtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void setAlarm() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,alarmreciever.class);
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();


    }
    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            CharSequence name = "Splannerreminderchannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Splanner", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

}