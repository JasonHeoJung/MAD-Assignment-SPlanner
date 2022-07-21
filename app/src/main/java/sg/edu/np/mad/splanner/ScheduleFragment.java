package sg.edu.np.mad.splanner;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import sg.edu.np.mad.splanner.databinding.ActivityMainBinding;

public class ScheduleFragment extends Fragment {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private ArrayList<String> schedID;
    private ArrayList<Event> schedule;
    Calendar calender = Calendar.getInstance();
    private String nowday = "Monday";
    private ActivityMainBinding binding;
    private int notificationid = 1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        recyclerView = view.findViewById(R.id.recyclerView);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        Button mon = view.findViewById(R.id.monday);
        Button tues = view.findViewById(R.id.tuesday);
        Button weds = view.findViewById(R.id.wed);
        Button thurs = view.findViewById(R.id.thursday);
        Button fri = view.findViewById(R.id.friday);

        ArrayList<Button> dayList = new ArrayList<>(Arrays.asList(mon, tues, weds, thurs, fri));

        for (Button day : dayList) {
            day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (Button b: dayList) {
                            if(b == day){
                                int colors = Color.parseColor("#f1cb93");
                                b.setBackgroundColor(colors);
                            }
                            else{
                                int colors = Color.parseColor("#EAB361");
                                b.setBackgroundColor(colors);
                            }
//                            Toast.makeText(getContext(), b.getText().toString(), Toast.LENGTH_SHORT).show();
//                            if(b.getText().toString() == "Monday"){
//                                int colors = Color.parseColor("#f1cb93");
//                                b.setBackgroundColor(colors);
//                            }
//                            if(b.getText().toString() == "Tuesday"){
//                                int colors = Color.parseColor("#ac89ed");
//                                b.setBackgroundColor(colors);
//                            }
//                            if(b.getText().toString() == "Wednesday"){
//                                int colors = Color.parseColor("#788ecf");
//                                b.setBackgroundColor(colors);
//                            }
//                            if(b.getText().toString() == "Thursday"){
//                                int colors = Color.parseColor("#80d6c2");
//                                b.setBackgroundColor(colors);
//                            }
//                            if(b.getText().toString() == "Friday") {
//                                int colors = Color.parseColor("#7ac9e8");
//                                b.setBackgroundColor(colors);
//                            }
//                        }
//                        else{
//                            if(b.getText().toString() == "Monday"){
//                                int colors = Color.parseColor("#EAB361");
//                                b.setBackgroundColor(colors);
//                            }
//                            else if(b.getText().toString() == "Tuesday"){
//                                int colors = Color.parseColor("#9365E8");
//                                b.setBackgroundColor(colors);
//                            }
//                            else if(b.getText().toString() == "Wednesday"){
//                                int colors = Color.parseColor("#3F5CB3");
//                                b.setBackgroundColor(colors);
//                            }
//                            else if(b.getText().toString() == "Thursday"){
//                                int colors = Color.parseColor("#54C8AD");
//                                b.setBackgroundColor(colors);
//                            }
//                            else{
//                                int colors = Color.parseColor("#5BBCE3");
//                                b.setBackgroundColor(colors);
//                            }
                    }
                    nowday = day.getText().toString();
                    reference.child(auth.getCurrentUser().getUid()).child("schedule").child(nowday).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            schedule = new ArrayList<>();
                            schedID = new ArrayList<>();
                            for (DataSnapshot eventSnapshot: snapshot.getChildren()) {
                                schedID.add(eventSnapshot.getKey());
                                schedule.add(eventSnapshot.getValue(Event.class));
                            }
                            Collections.sort(schedule);
                            setAdapter();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

            });
        }


        Button addScheduleBtn = view.findViewById(R.id.addsched);
        addScheduleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddDetailsActivity.class);
                intent.putExtra("day", nowday);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        reference.child(auth.getCurrentUser().getUid()).child("schedule").child(nowday).addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                schedule = new ArrayList<>();
                schedID = new ArrayList<>();
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    Event item = eventSnapshot.getValue(Event.class);
                    schedule.add(item);
                    schedID.add(eventSnapshot.getKey());


                }
                Collections.sort(schedule);
                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void setAdapter() {
        ScheduleAdapter adapter = new ScheduleAdapter(schedule, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            schedule.remove(viewHolder.getAdapterPosition());
            String id = schedID.get(viewHolder.getAdapterPosition());
            Toast.makeText(getContext(), "Deleted", Toast.LENGTH_SHORT).show();
            reference.child(auth.getCurrentUser().getUid()).child("schedule").child(nowday).child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    Fragment fragment = new ScheduleFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    }
            });
            ScheduleAdapter adapter = new ScheduleAdapter(schedule, getActivity());
            adapter.notifyDataSetChanged();
        }
    };
}