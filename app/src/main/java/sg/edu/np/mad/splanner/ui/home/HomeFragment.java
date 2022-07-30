package sg.edu.np.mad.splanner.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.databinding.FragmentHomeBinding;
import sg.edu.np.mad.splanner.ui.home.schedule.HomeAddScheduleFragment;
import sg.edu.np.mad.splanner.ui.home.schedule.HomeScheduleFragment;
import sg.edu.np.mad.splanner.ui.home.task.HomeAddTaskFragment;
import sg.edu.np.mad.splanner.ui.home.task.HomeTaskFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private int pagerValue;
    GestureDetector gesture;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        TextView home_username = root.findViewById(R.id.home_username);
        home_username.setText(auth.getCurrentUser() != null ? auth.getCurrentUser().getDisplayName() : "username not found");

        SharedPreferences prefs = requireActivity().getSharedPreferences("SPlanner", Context.MODE_PRIVATE);
        pagerValue = prefs.getInt("pager", 0);

        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.HomeFrame, pagerValue == 0 ? new HomeTaskFragment() : new HomeScheduleFragment()).commit();

        setGesture();
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.homefab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                Fragment target = pagerValue == 0 ? new HomeAddTaskFragment() : new HomeAddScheduleFragment();
                transaction.replace(R.id.nav_host_fragment_activity_main, target);
                transaction.commit();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setGesture() {
        gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                swipeLeft();
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                swipeRight();
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });
    }

    private void swipeLeft() {
        if (pagerValue == 0) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.HomeFrame, new HomeScheduleFragment()).commit();
            pagerValue = 1;

            SharedPreferences.Editor editor = requireActivity().getSharedPreferences("SPlanner", Context.MODE_PRIVATE).edit();
            editor.putInt("pager", pagerValue);
            editor.apply();
        }
    }

    private void swipeRight() {
        if (pagerValue == 1) {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.HomeFrame, new HomeTaskFragment()).commit();
            pagerValue = 0;

            SharedPreferences.Editor editor = requireActivity().getSharedPreferences("SPlanner", Context.MODE_PRIVATE).edit();
            editor.putInt("pager", pagerValue);
            editor.apply();
        }
    }
}