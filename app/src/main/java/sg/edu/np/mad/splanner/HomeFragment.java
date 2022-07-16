package sg.edu.np.mad.splanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;


public class HomeFragment extends Fragment {

    private ImageButton profile;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private ArrayList<Event> schedule;
    private Intent retrieveIntent;
    private String dayOfWeek;
    private TextView noSchedule;
    private TextView weekEndText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        retrieveIntent = getActivity().getIntent();
        schedule = new ArrayList<>();
        noSchedule = view.findViewById(R.id.noSchedule);
        /*weekEndText = view.findViewById(R.id.weekEndText);*/
        profile = view.findViewById(R.id.profile);
        Calendar calender = Calendar.getInstance();
        int day = calender.get(Calendar.DAY_OF_WEEK);
        RecyclerView recyclerView = view.findViewById(R.id.listImage);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        if (day == Calendar.MONDAY){
            dayOfWeek = "Monday";
        }
        else if (day == Calendar.TUESDAY){
            dayOfWeek = "Tuesday";
        }
        else if (day == Calendar.WEDNESDAY){
            dayOfWeek = "Wednesday";
        }
        else if (day == Calendar.THURSDAY){
            dayOfWeek = "Thursday";
        }
        else if (day == Calendar.FRIDAY){
            dayOfWeek = "Friday";
        }
        else {
            weekEndText.setText("No schedule on the weekends, showing next available day's");
            dayOfWeek = "Monday";
        }
        noSchedule.setText(dayOfWeek);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UserProfile.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reference.child(auth.getCurrentUser().getUid()).child("schedule").child(dayOfWeek)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        schedule = new ArrayList<>();
                        for (DataSnapshot eventSnapshot: snapshot.getChildren()) {
                            schedule.add(eventSnapshot.getValue(Event.class));
                        }
                        setAdapter();
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void setAdapter() {
        RecyclerView recyclerView = getActivity().findViewById(R.id.listImage);
        ScheduleAdapter adapter = new ScheduleAdapter(schedule, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}