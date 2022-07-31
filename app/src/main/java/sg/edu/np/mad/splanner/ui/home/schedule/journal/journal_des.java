package sg.edu.np.mad.splanner.ui.home.schedule.journal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.journal;

public class journal_des extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        setContentView(R.layout.activity_journal_des);
        TextView title = findViewById(R.id.journal_title_2);
        TextView Description = findViewById(R.id.des);
        TextView date = findViewById(R.id.date);

        Intent rec = getIntent();
        String id = rec.getStringExtra("Id");
        reference.child(auth.getCurrentUser().getUid()).child("journal").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                journal j = snapshot.getValue(journal.class);
                title.setText(j.getTitle());
                Description.setText(j.getDescription());
                date.setText(j.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button editbtn = findViewById(R.id.Edtibtn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Edit_Journal.class);
                intent.putExtra("Id", id);
                startActivity(intent);
            }
        });
    }
}