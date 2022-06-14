package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class timetable_details extends AppCompatActivity {

    private ArrayList<schedule> scheduleArrayList;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_details);
        scheduleArrayList = new ArrayList<>();
        Button addschedule = findViewById(R.id.addsched);
        recyclerView = findViewById(R.id.recyclerView);
        addschedule.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent myint = new Intent(timetable_details.this, addDetails.class);
                startActivity(myint);
            }
        });
        setAdapter();
    }
    private void setAdapter(){
        recyclerview adapter = new recyclerview(scheduleArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}