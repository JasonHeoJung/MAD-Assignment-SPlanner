package sg.edu.np.mad.splanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;

public class ScheduleFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button mon = view.findViewById(R.id.monday);
        Button tues = view.findViewById(R.id.tuesday);
        Button weds = view.findViewById(R.id.wed);
        Button thurs = view.findViewById(R.id.thursday);
        Button fri = view.findViewById(R.id.friday);

        ArrayList<Button> dayList = new ArrayList<>(Arrays.asList(mon, tues, weds, thurs, fri));

        for (Button day: dayList) {
            day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TimetableDetailsActivity.class);
                    intent.putExtra("day", day.getText());
                    startActivity(intent);
                }
            });
        }
    }
}