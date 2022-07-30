package sg.edu.np.mad.splanner.ui.home.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Task;
import sg.edu.np.mad.splanner.databinding.FragmentHomeTaskBinding;

public class HomeTaskFragment extends Fragment {

    private FragmentHomeTaskBinding binding;
    private RecyclerView recyclerView;
    private TextView emptyView;
    private TextView task_count;
    private ProgressBar task_progress_bar;
    private TextView task_progress_text;

    private FirebaseAuth auth;
    private DatabaseReference reference;

    private ArrayList<String> taskIds;
    private ArrayList<Task> tasks;
    private Map<String, Task> taskMap;
    private TaskAdapter adapter;
    private int completedTask;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeTaskBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        recyclerView = root.findViewById(R.id.HomeTaskRV);
        emptyView = root.findViewById(R.id.empty_view);
        task_count = root.findViewById(R.id.task_count);
        task_progress_bar = root.findViewById(R.id.task_progress_bar);
        task_progress_text = root.findViewById(R.id.task_progress_text);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        loadData();

        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                loadData();
                reference.child(auth.getCurrentUser().getUid()).child("tasks").child(taskIds.get(viewHolder.getAdapterPosition())).removeValue();

                if (taskMap.get(taskIds.get(viewHolder.getAdapterPosition())).getStatus()) {
                    completedTask -= 1;
                }

                taskMap.remove(taskIds.get(viewHolder.getAdapterPosition()));
                taskIds.remove(viewHolder.getAdapterPosition());
                tasks.remove(viewHolder.getAdapterPosition());
                adapter.taskIds = taskIds;
                adapter.tasks = tasks;

                if (tasks.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }

                task_count.setText(String.valueOf(taskIds.size()));
                task_progress_text.setText(String.format("%d out of %d tasks done", completedTask, taskIds.size()));
                task_progress_bar.setProgress(taskIds.size() == 0 ? 100 : (completedTask * 100)/taskIds.size());

                Toast.makeText(requireActivity(), "Deleted Task", Toast.LENGTH_SHORT).show();
                setAdapter();
            }
        });

        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void loadData() {
        reference.child(auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "").child("tasks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                taskIds = new ArrayList<>();
                tasks = new ArrayList<>();
                taskMap = new TreeMap<String,Task>();

                completedTask = 0;

                for (DataSnapshot taskSnapshot: snapshot.getChildren()) {
                    taskIds.add(taskSnapshot.getKey());
                    tasks.add(taskSnapshot.getValue(Task.class));
                    if (tasks.get(tasks.size() - 1).getStatus()) {
                        completedTask++;
                    }
                }

                for (int i = 0; i < taskIds.size(); i++) {
                    taskMap.put(taskIds.get(i),tasks.get(i));
                }

                if (snapshot.hasChildren()) {
                    DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

                    Collections.sort(taskIds, (t1, t2) -> {
                        try {
                            return format.parse(taskMap.get(t1).getDueDate()).compareTo(format.parse(taskMap.get(t2).getDueDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    });

                    Collections.sort(tasks, (t1, t2) -> {
                        if (t1.getDueDate().equals("") || t2.getDueDate().equals("")) return -1;
                        try {
                            return format.parse(t1.getDueDate()).compareTo(format.parse(t2.getDueDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    });

                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    setAdapter();
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }

                task_count.setText(Integer.toString(taskIds.size()));
                task_progress_bar.setProgress(taskIds.size() == 0 ? 100 : (completedTask * 100)/taskIds.size());
                task_progress_text.setText(String.format("%d out of %d tasks done", completedTask, taskIds.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter() {
        adapter = new TaskAdapter(taskIds, tasks, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}