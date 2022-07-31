package sg.edu.np.mad.splanner.ui.home.schedule;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import sg.edu.np.mad.splanner.Event;
import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.databinding.FragmentHomeScheduleBinding;

public class HomeScheduleFragment extends Fragment {

    private FragmentHomeScheduleBinding binding;
    private RecyclerView recyclerView;
    private View nextLesson;
    private TextView emptyView;
    private TextView nextEmptyView;
    private TextView schedule_seeall;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    private ArrayList<String> scheduleIds;
    private ArrayList<Event> schedule;
    private Map<String, Event> scheduleMap;
    private ScheduleAdapter adapter;
    private String day;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        recyclerView = root.findViewById(R.id.HomeScheduleRV);
        nextLesson = root.findViewById(R.id.nextLessonCell);
        emptyView = root.findViewById(R.id.empty_view);
        nextEmptyView = root.findViewById(R.id.empty_view_nextlesson);
        schedule_seeall = root.findViewById(R.id.schedule_seeall);

        schedule_seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(requireActivity(), ScheduleSeeAllActivity.class));
            }
        });

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadData();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                loadData();
                reference.child(auth.getCurrentUser().getUid()).child("schedule").child(day).child(scheduleIds.get(viewHolder.getAdapterPosition())).removeValue();

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

                Toast.makeText(requireActivity(), "Deleted Schedule", Toast.LENGTH_SHORT).show();
                setAdapter();
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadData() {
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                day = "Monday";
                break;
            case Calendar.TUESDAY:
                day = "Tuesday";
                break;
            case Calendar.WEDNESDAY:
                day = "Wednesday";
                break;
            case Calendar.THURSDAY:
                day = "Thursday";
                break;
            case Calendar.FRIDAY:
                day = "Friday";
                break;
            default:
                day = "Weekend";
                break;
        }

        reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("schedule").child(day).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduleIds = new ArrayList<>();
                schedule = new ArrayList<>();
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

                    Event nextSchedule = null;
                    String[] current_time = String.valueOf(Calendar.getInstance().getTime()).split(" ")[3].split(":");
                    String current_hour = current_time[0];
                    String current_min = current_time[1];

                    for (int i = 0; i < scheduleIds.size(); i++) {
                        if (day.equals("Weekend")) break;
                        try {
                            if (formatter.parse(current_hour + ":" + current_min).compareTo(formatter.parse(schedule.get(i).getTiming())) <= 0) {
                                nextSchedule = schedule.get(i);
                                break;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (nextSchedule != null) {
                        TextView nextTitle = nextLesson.findViewById(R.id.event_title);
                        TextView nextLocation = nextLesson.findViewById(R.id.event_location);
                        TextView nextTime = nextLesson.findViewById(R.id.event_time);

                        nextTitle.setText(nextSchedule.getModule());
                        nextLocation.setText(nextSchedule.getLocation());
                        nextTime.setText(nextSchedule.getTiming());

                        nextLesson.setVisibility(View.VISIBLE);
                        nextEmptyView.setVisibility(View.GONE);
                    }
                    else {
                        nextLesson.setVisibility(View.GONE);
                        nextEmptyView.setVisibility(View.VISIBLE);
                    }

                    setAdapter();
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                    nextLesson.setVisibility(View.GONE);
                    nextEmptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter() {
        adapter = new ScheduleAdapter(scheduleIds, schedule);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}