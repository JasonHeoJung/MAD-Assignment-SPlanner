package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimetableDetailsActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private ArrayList<Event> schedule;
    private Intent retrieveIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_details);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        retrieveIntent = getIntent();
        schedule = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);

        Button addScheduleBtn = findViewById(R.id.addsched);
        addScheduleBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimetableDetailsActivity.this, AddDetailsActivity.class);
                intent.putExtra("day", retrieveIntent.getStringExtra("day"));
                startActivity(intent);
            }
        });

        Button backBtn = findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.child(auth.getCurrentUser().getUid()).child("schedule").child(retrieveIntent.getStringExtra("day"))
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
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ScheduleAdapter adapter = new ScheduleAdapter(schedule);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}