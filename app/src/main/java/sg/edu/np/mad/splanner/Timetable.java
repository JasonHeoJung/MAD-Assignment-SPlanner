package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Timetable extends AppCompatActivity {
    private static String mon1;
    private static String tues1;
    private static String wed1;
    private static String thurs1;
    private static String fri1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        Button mon = findViewById(R.id.monday);
        Button tues = findViewById(R.id.tuesday);
        Button weds = findViewById(R.id.wed);
        Button thurs = findViewById(R.id.thursday);
        Button fri = findViewById(R.id.friday);

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mon1 = "monday";
                Bundle mybundle = new Bundle();
                mybundle.putString("Monday", mon1);
                Intent myint = new Intent(Timetable.this, timetable_details.class);
                startActivity(myint);
            }
        });
        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tues1 = "tuesday";
                Bundle mybundle = new Bundle();
                mybundle.putString("tuesday", tues1);
                Intent myint = new Intent(Timetable.this, timetable_details.class);
                startActivity(myint);
            }
        });
        weds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wed1 = "wednesday";
                Bundle mybundle = new Bundle();
                mybundle.putString("wednesday", wed1);
                Intent myint = new Intent(Timetable.this, timetable_details.class);
                startActivity(myint);
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thurs1 = "thursday";
                Bundle mybundle = new Bundle();
                mybundle.putString("thursday", thurs1);
                Intent myint = new Intent(Timetable.this, timetable_details.class);
                startActivity(myint);
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fri1 = "friday";
                Bundle mybundle = new Bundle();
                mybundle.putString("friday", fri1);
                Intent myint = new Intent(Timetable.this, timetable_details.class);
                startActivity(myint);
            }
        });
    }
}