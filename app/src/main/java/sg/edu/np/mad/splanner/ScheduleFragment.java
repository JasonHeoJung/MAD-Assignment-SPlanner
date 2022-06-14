package sg.edu.np.mad.splanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ScheduleFragment extends Fragment {
    private static String mon1;
    private static String tues1;
    private static String wed1;
    private static String thurs1;
    private static String fri1;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button mon = getView().findViewById(R.id.monday);
        Button tues = getView().findViewById(R.id.tuesday);
        Button weds = getView().findViewById(R.id.wed);
        Button thurs = getView().findViewById(R.id.thursday);
        Button fri = getView().findViewById(R.id.friday);



        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mon1 = "monday";
                Bundle mybundle = new Bundle();
                mybundle.putString("Monday", mon1);
                Intent myint = new Intent(getActivity(), timetable_details.class);
                startActivity(myint);
            }
        });
        tues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tues1 = "tuesday";
                Bundle mybundle = new Bundle();
                mybundle.putString("tuesday", tues1);
                Intent myint = new Intent(getActivity(), timetable_details.class);
                startActivity(myint);
            }
        });
        weds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wed1 = "wednesday";
                Bundle mybundle = new Bundle();
                mybundle.putString("wednesday", wed1);
                Intent myint = new Intent(getActivity(), timetable_details.class);
                startActivity(myint);
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thurs1 = "thursday";
                Bundle mybundle = new Bundle();
                mybundle.putString("thursday", thurs1);
                Intent myint = new Intent(getActivity(), timetable_details.class);
                startActivity(myint);
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fri1 = "friday";
                Bundle mybundle = new Bundle();
                mybundle.putString("friday", fri1);
                Intent myint = new Intent(getActivity(), timetable_details.class);
                startActivity(myint);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }
}