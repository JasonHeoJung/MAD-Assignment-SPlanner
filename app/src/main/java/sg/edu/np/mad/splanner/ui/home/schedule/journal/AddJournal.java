package sg.edu.np.mad.splanner.ui.home.schedule.journal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.journal;

public class AddJournal extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        setContentView(R.layout.activity_add_journal);
        EditText ettitle = findViewById(R.id.addTitle);
        TextView etdate = findViewById(R.id.editviewdate);
        EditText etdes = findViewById(R.id.editdes);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String d = s.format(date.getTime());
        etdate.setText(d);

        Button addj = findViewById(R.id.addBtn);
        addj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eT = ettitle.getText().toString();
                String dateed = d;
                String etdesc = etdes.getText().toString();
                DatabaseReference journalRef = reference.child(auth.getCurrentUser().getUid()).child("journal").push();
                journalRef.setValue(new journal(eT, etdesc, d));
                Toast.makeText(AddJournal.this, "Added Journal", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddJournal.this, journalRecyclerView.class);
                startActivity(intent);
            }
        });
    }
}