package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class HomeFragment extends Fragment {

    private TextView schedule;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        schedule = view.findViewById(R.id.schedule);
        Date dateCurrent = Calendar.getInstance().getTime();

        schedule.setText(dateCurrent.toString());

        return view;
    }
}