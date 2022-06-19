package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ToDoListFragment extends Fragment implements ToDoListRecyclerView.OnNoteListener {

    MainActivity mainActivity;
    private Fragment fragment;
    private Button addTask;
    private RecyclerView recyclerView;
    private task t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_to_do_list, container, false);
        mainActivity = (MainActivity) getActivity();
        recyclerView = v.findViewById(R.id.taskList);
        addTask = v.findViewById(R.id.addMoreTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new addTask();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        // Inflate the layout for this fragment
        setAdapter();
        return v;
    }

    private void setAdapter() {
        ToDoListRecyclerView adapter = new ToDoListRecyclerView(mainActivity.taskList1.getTaskList(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {
        t = mainActivity.taskList1.getTaskList().get(position);
        mainActivity.taskList1.getTaskList().remove(t);
        fragment = new ToDoListFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }
}