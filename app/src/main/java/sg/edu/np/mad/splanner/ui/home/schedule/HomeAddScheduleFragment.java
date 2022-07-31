package sg.edu.np.mad.splanner.ui.home.schedule;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Calendar;

import sg.edu.np.mad.splanner.Event;
import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Task;
import sg.edu.np.mad.splanner.databinding.FragmentHomeAddScheduleBinding;
import sg.edu.np.mad.splanner.ui.home.HomeFragment;

public class HomeAddScheduleFragment extends Fragment {

    private FragmentHomeAddScheduleBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private EditText event_title, event_location, event_timing;
    private AutoCompleteTextView event_day;
    private Button addEvent;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeAddScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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

        event_title = root.findViewById(R.id.etEventTitle);

        event_day = root.findViewById(R.id.etEventDay);

        String[] days = getResources().getStringArray(R.array.days);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), R.layout.dropdown_item, days);
        event_day.setAdapter(arrayAdapter);
        event_day.setInputType(InputType.TYPE_NULL);
        event_day.setKeyListener(null);

        event_location = root.findViewById(R.id.etEventLocation);
        event_timing = root.findViewById(R.id.etEventTime);

        event_timing.setInputType(InputType.TYPE_NULL);
        event_timing.setKeyListener(null);

        event_timing.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                picker.show(getParentFragmentManager(), "tag");
            }
            return false;
        });

        event_title.addTextChangedListener(tw);
        event_day.addTextChangedListener(tw);
        event_location.addTextChangedListener(tw);
        event_timing.addTextChangedListener(tw);

        addEvent = root.findViewById(R.id.add_event_btn);
        addEvent.setOnClickListener(view -> {
            String name = event_title.getText().toString();
            String day = event_day.getText().toString();
            String location = event_location.getText().toString();
            String startTime = event_timing.getText().toString();

            DatabaseReference scheduleRef = reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("schedule").child(day).push();
            scheduleRef.setValue(new Event(name, location, startTime));

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_activity_main, new HomeFragment());
            transaction.commit();
        });

        ImageView cancelBtn = root.findViewById(R.id.home_add_schedule_close_img);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_main, new HomeFragment());
                transaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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