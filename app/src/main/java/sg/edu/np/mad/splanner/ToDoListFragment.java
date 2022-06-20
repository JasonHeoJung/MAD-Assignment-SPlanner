package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ToDoListFragment extends Fragment {
    MainActivity mainActivity;
    private Fragment fragment;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_to_do_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainActivity = (MainActivity) getActivity();
        recyclerView = view.findViewById(R.id.taskList);

        Button addTask = view.findViewById(R.id.addMoreTask);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new AddTaskFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            }
        });
        // Inflate the layout for this fragment
        setAdapter();
    }

    private void setAdapter() {
        ToDoListAdapter adapter = new ToDoListAdapter(mainActivity.taskList1.getTaskList());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

//    @Override
//    public void onNoteClick(int position) {
//        Task task = mainActivity.taskList1.getTaskList().get(position);
//        mainActivity.taskList1.getTaskList().remove(task);
//        fragment = new ToDoListFragment();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
//    }
}