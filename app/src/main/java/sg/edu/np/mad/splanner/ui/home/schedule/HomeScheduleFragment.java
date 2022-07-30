package sg.edu.np.mad.splanner.ui.home.schedule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import sg.edu.np.mad.splanner.Event;
import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.databinding.FragmentHomeScheduleBinding;

public class HomeScheduleFragment extends Fragment {

    private FragmentHomeScheduleBinding binding;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private TextView event_title;
    private TextView event_location;
    private TextView event_time;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    private ArrayList<String> scheduleIds;
    private ArrayList<Event> schedule;
    private Map<String, Event> scheduleMap;
    private ScheduleAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeScheduleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        recyclerView = root.findViewById(R.id.HomeScheduleRV);
        emptyView = root.findViewById(R.id.empty_view);
        event_title = root.findViewById(R.id.event_title);
        event_location = root.findViewById(R.id.event_location);
        event_time = root.findViewById(R.id.event_time);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadData() {
        String day;
        switch (Calendar.getInstance().get(Calendar.DAY_OF_WEEK)) {
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
                day = "Monday";
                break;
        }

        reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("schedule").child(day).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scheduleIds = new ArrayList<>();
                schedule = new ArrayList<>();
                scheduleMap = new TreeMap<String, Event>();

                for (DataSnapshot scheduleSnapshot: snapshot.getChildren()) {
                    scheduleIds.add(scheduleSnapshot.getKey());
                    schedule.add(scheduleSnapshot.getValue(Event.class));
                }

                for (int i = 0; i < scheduleIds.size(); i++) {
                    scheduleMap.put(scheduleIds.get(i),schedule.get(i));
                }

                if (snapshot.hasChildren()) {
//                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
//
//                    Collections.sort(scheduleIds, (t1, t2) -> {
//                        try {
//                            return format.parse(scheduleMap.get(t1).getTiming()).compareTo(format.parse(scheduleMap.get(t2).getTiming()));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        return 0;
//                    });
//
//                    Collections.sort(schedule, (t1, t2) -> {
//                        if (t1.getTiming().equals("") || t2.getTiming().equals("")) return -1;
//                        try {
//                            return format.parse(t1.getTiming()).compareTo(format.parse(t2.getTiming()));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        return 0;
//                    });

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
        adapter = new ScheduleAdapter(scheduleIds, schedule, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}