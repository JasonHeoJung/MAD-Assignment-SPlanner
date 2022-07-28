package sg.edu.np.mad.splanner.ui.home.task;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Task;
import sg.edu.np.mad.splanner.databinding.FragmentHomeAddTaskBinding;
import sg.edu.np.mad.splanner.ui.home.HomeFragment;

public class HomeAddTaskFragment extends Fragment {

    private FragmentHomeAddTaskBinding binding;

    private FirebaseAuth auth;
    private DatabaseReference reference;
    private EditText task_title;
    private EditText task_due;

    @SuppressLint("ClickableViewAccessibility")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeAddTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String formattedDate  = format.format(calendar.getTime());
                task_due.setText(formattedDate);
            }
        });

        task_title = root.findViewById(R.id.etTaskTitle);
        task_due = root.findViewById(R.id.etTaskDue);

        task_due.setInputType(InputType.TYPE_NULL);
        task_due.setKeyListener(null);

        task_due.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                datePicker.show(getParentFragmentManager(), "tag");
            }
            return false;
        });

        Button addTask = root.findViewById(R.id.add_task_btn);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = task_title.getText().toString();
                String dueDate = task_due.getText().toString();

                DatabaseReference tasksRef = reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("tasks").push();
                tasksRef.setValue(new Task(name, dueDate, false));

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_main, new HomeFragment());
                transaction.commit();
            }
        });

        ImageView cancel = root.findViewById(R.id.closeImg);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment_activity_main, new HomeFragment());
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
}