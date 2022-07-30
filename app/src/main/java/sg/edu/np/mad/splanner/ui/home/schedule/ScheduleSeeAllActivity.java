package sg.edu.np.mad.splanner.ui.home.schedule;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import sg.edu.np.mad.splanner.AddDetailsActivity;
import sg.edu.np.mad.splanner.Event;
import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.ui.home.schedule.journal.journalRecyclerView;

public class ScheduleSeeAllActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private TextView textweek;
    Calendar calender = Calendar.getInstance();
    private String nowday;

    private ArrayList<String> scheduleIds;
    private ArrayList<Event> schedule;
    private Map<String, Event> scheduleMap;
    private ScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_see_all);

        switch (calender.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.TUESDAY:
                nowday = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                nowday = "Wednesday";
                break;
            case Calendar.THURSDAY:
                nowday = "Thursday";
                break;
            case Calendar.FRIDAY:
                nowday = "Friday";
                break;
            default:
                nowday = "Monday";
                break;
        }

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        recyclerView = findViewById(R.id.recyclerView);
        emptyView = findViewById(R.id.empty_view);

        textweek = findViewById(R.id.week);

        Button mon = findViewById(R.id.monday);
        Button tues = findViewById(R.id.tuesday);
        Button weds = findViewById(R.id.wed);
        Button thurs = findViewById(R.id.thursday);
        Button fri = findViewById(R.id.friday);

        ArrayList<Button> dayList = new ArrayList<>(Arrays.asList(mon, tues, weds, thurs, fri));

        for (Button day : dayList) {
            day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowday = day.getText().toString();
                    textweek.setText(nowday);

                    loadData();
                }
            });
        }

        FloatingActionButton journalbtn = findViewById(R.id.schedule_fab2);
        journalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), journalRecyclerView.class);
                startActivity(intent);
            }
        });

        FloatingActionButton addScheduleBtn = findViewById(R.id.schedule_fab);
        addScheduleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeAddScheduleActivity.class);
                intent.putExtra("day", nowday);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        textweek.setText(nowday);
        loadData();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                loadData();
                reference.child(auth.getCurrentUser().getUid()).child("schedule").child(nowday).child(scheduleIds.get(viewHolder.getAdapterPosition())).removeValue();

                scheduleMap.remove(scheduleIds.get(viewHolder.getAdapterPosition()));
                scheduleIds.remove(viewHolder.getAdapterPosition());
                schedule.remove(viewHolder.getAdapterPosition());
                adapter.scheduleIds = scheduleIds;
                adapter.schedule = schedule;

                if (schedule.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }

                Toast.makeText(getApplicationContext(), "Deleted Schedule", Toast.LENGTH_SHORT).show();
                setAdapter();
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    private void loadData() {
        reference.child(auth.getCurrentUser().getUid()).child("schedule").child(nowday).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                schedule = new ArrayList<>();
                scheduleIds = new ArrayList<>();
                scheduleMap = new TreeMap<String, Event>();

                if (snapshot.hasChildren()) {
                    for (DataSnapshot eventSnapshot: snapshot.getChildren()) {
                        scheduleIds.add(eventSnapshot.getKey());
                        schedule.add(eventSnapshot.getValue(Event.class));
                    }

                    for (int i = 0; i < scheduleIds.size(); i++) {
                        scheduleMap.put(scheduleIds.get(i),schedule.get(i));
                    }

                    DateFormat formatter = new SimpleDateFormat("HH:mm");
                    Collections.sort(scheduleIds, (t1, t2) -> {
                        try {
                            return formatter.parse(scheduleMap.get(t1).getTiming()).compareTo(formatter.parse(scheduleMap.get(t2).getTiming()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    });

                    Collections.sort(schedule, (t1, t2) -> {
                        if (t1.getTiming().equals("") || t2.getTiming().equals("")) return -1;
                        try {
                            return formatter.parse(t1.getTiming()).compareTo(formatter.parse(t2.getTiming()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    });

                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    setAdapter();
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter() {
        adapter = new ScheduleAdapter(scheduleIds, schedule);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}