package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        EditText mycreateclass = findViewById(R.id.editclassname);
        EditText myceatemodule = findViewById(R.id.editmodname);
        EditText mycreatetime = findViewById(R.id.edittime);

        Button myCreatesched = findViewById(R.id.add);
        Button mycancel = findViewById(R.id.cancel);
        myCreatesched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myintent = new Intent(addDetails.this, timetable_details.class);
                startActivity(myintent);
            }
        });
        mycancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(addDetails.this, timetable_details.class);
                startActivity(myintent);
            }
        });
    }
}