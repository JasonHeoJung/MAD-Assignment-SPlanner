package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Collections;

public class journalRecyclerView extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private TextView textweek;
    private ArrayList<String> journalid;
    private ArrayList<journal> journallist;
    private journal_Adapter.RecyclerViewClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_recycler_view);
        journallist = new ArrayList<>();
        recyclerView = findViewById(R.id.journalList);
        setAdapter();

        Button addjournal = findViewById(R.id.addjournal);
        addjournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddJournal.class);
                startActivity(intent);
            }
        });

    }
    private void setAdapter(){
        setOnclicklistner();
        journal_Adapter adapter = new journal_Adapter(journallist, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnclicklistner() {
        listener = new journal_Adapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(), journal_des.class);
                intent.putExtra("Title", journallist.get(position).getTitle());
                startActivity(intent);
            }
        };
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            journallist.remove(viewHolder.getAdapterPosition());
            String id = journalid.get(viewHolder.getAdapterPosition());
            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
            //reference.child(auth.getCurrentUser().getUid())child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            // @Override
            //public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
            //  Fragment fragment = new ScheduleFragment();
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

            //});
            //ScheduleAdapter adapter = new ScheduleAdapter(schedule, getActivity());
            //adapter.notifyDataSetChanged();
        }
    };

}