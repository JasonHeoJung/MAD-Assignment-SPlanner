package sg.edu.np.mad.splanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class Edit_Journal extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        setContentView(R.layout.activity_edit_journal);
        EditText ettitle = findViewById(R.id.editTitle);
        TextView etdate = findViewById(R.id.editviewdate);
        EditText etdes = findViewById(R.id.editdes);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String d = s.format(date.getTime());
        etdate.setText(d);
        Intent rec = getIntent();
        String id = rec.getStringExtra("Id");
        reference.child(auth.getCurrentUser().getUid()).child("journal").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                journal j = snapshot.getValue(journal.class);
                ettitle.setText(j.getTitle());
                etdes.setText(j.getDescription());
                etdate.setText(d);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Button save = findViewById(R.id.SaveBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eT = ettitle.getText().toString();
                String dateed = d;
                String etdesc = etdes.getText().toString();
                reference.child(auth.getCurrentUser().getUid()).child("journal").child(id).setValue(new journal(eT, etdesc, etdesc));
                Toast.makeText(Edit_Journal.this, "Update Journal", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Edit_Journal.this, journalRecyclerView.class);
                startActivity(intent);
            }
        });

        Button cancel = findViewById(R.id.cancel2btn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Edit_Journal.this, journalRecyclerView.class);
                startActivity(intent);
                finish();
            }
        });

    }


}