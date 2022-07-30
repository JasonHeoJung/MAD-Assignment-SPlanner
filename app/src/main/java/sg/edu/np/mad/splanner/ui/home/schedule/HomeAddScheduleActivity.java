package sg.edu.np.mad.splanner.ui.home.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sg.edu.np.mad.splanner.Event;
import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.databinding.FragmentHomeAddScheduleBinding;
import sg.edu.np.mad.splanner.ui.home.HomeFragment;

public class HomeAddScheduleActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private EditText event_title, event_location, event_timing;
    private AutoCompleteTextView event_day;
    private Button addEvent;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_schedule);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText("Select Class time")
                .build();

        picker.addOnPositiveButtonClickListener(dialog -> {
            int newHour = picker.getHour();
            int newMinute = picker.getMinute();
            event_timing.setText(String.format("%02d:%02d", newHour, newMinute));
        });

        event_title = findViewById(R.id.etEventTitle);

        event_day = findViewById(R.id.etEventDay);

        String[] days = getResources().getStringArray(R.array.days);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, days);
        event_day.setAdapter(arrayAdapter);
        event_day.setInputType(InputType.TYPE_NULL);
        event_day.setKeyListener(null);

        event_location = findViewById(R.id.etEventLocation);
        event_timing = findViewById(R.id.etEventTime);

        event_timing.setInputType(InputType.TYPE_NULL);
        event_timing.setKeyListener(null);

        event_timing.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                picker.show(getSupportFragmentManager(), "tag");
            }
            return false;
        });

        event_title.addTextChangedListener(tw);
        event_day.addTextChangedListener(tw);
        event_location.addTextChangedListener(tw);
        event_timing.addTextChangedListener(tw);

        addEvent = findViewById(R.id.add_event_btn2);
        addEvent.setOnClickListener(view -> {
            String name = event_title.getText().toString();
            String day = event_day.getText().toString();
            String location = event_location.getText().toString();
            String startTime = event_timing.getText().toString();

            DatabaseReference scheduleRef = reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("schedule").child(day).push();
            scheduleRef.setValue(new Event(name, location, startTime));

            finish();
        });

        ImageView cancelBtn = findViewById(R.id.home_add_schedule_close_img2);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String title = event_title.getText().toString();
            String day = event_day.getText().toString();
            String location = event_location.getText().toString();
            String timing = event_timing.getText().toString();

            boolean valid = false;

            if (title.trim().length() == 0) {
                event_title.setError("Module is Required");
            }
            else if (day.trim().length() == 0) {
                event_day.setError("Day is Required");
            }
            else if (location.trim().length() == 0) {
                event_location.setError("Location is Required");
            }
            else if (timing.trim().length() == 0) {
                event_timing.setError("Start time is Required");
            }
            else {
                valid = true;
            }

            addEvent.setEnabled(valid);
        }

        @Override
        public void afterTextChanged(Editable s) {
            String day = event_day.getText().toString();
            String timing = event_timing.getText().toString();

            if (day.trim().length() != 0) {
                event_day.setError(null);
            }
            if (timing.trim().length() != 0) {
                event_timing.setError(null);
            }
        }
    };
}