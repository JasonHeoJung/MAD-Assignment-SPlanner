package sg.edu.np.mad.splanner.ui.home.schedule.journal;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.journal;

public class journalRecyclerView extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    journal_Adapter journal_adapter;
    private TextView textweek;
    private ArrayList<String> journalid;
    private ArrayList<journal> journallist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_recycler_view);

        journallist = new ArrayList<>();
        recyclerView = findViewById(R.id.journalList);

        FloatingActionButton addjournal = findViewById(R.id.journal_fab);
        addjournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddJournal.class);
                startActivity(intent);
            }
        });

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        reference.child(auth.getCurrentUser().getUid()).child("journal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                journallist = new ArrayList<>();
                journalid = new ArrayList<>();

                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    journalid.add(eventSnapshot.getKey());
                    journallist.add(eventSnapshot.getValue(journal.class));
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        reference.child(auth.getCurrentUser().getUid()).child("journal").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                journallist = new ArrayList<>();
                journalid = new ArrayList<>();

                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    journalid.add(eventSnapshot.getKey());
                    journallist.add(eventSnapshot.getValue(journal.class));
                }

                setAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setAdapter(){
        Log.v("JOURNAL SIZE", String.valueOf(journallist.size()));
        journal_Adapter adapter = new journal_Adapter(journallist, journalid, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

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

            reference.child(auth.getCurrentUser().getUid()).child("journal").child(id).removeValue();

            journal_Adapter adapter = new journal_Adapter(journallist, journalid, journalRecyclerView.this);
            adapter.notifyDataSetChanged();
        }
    };
}