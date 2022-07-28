package sg.edu.np.mad.splanner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sg.edu.np.mad.splanner.databinding.ActivityEditJournalBinding;

public class Edit_Journal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_journal);
        EditText ettitle = findViewById(R.id.editTitle);
        TextView etdate = findViewById(R.id.editviewdate);
        EditText etdes = findViewById(R.id.editdes);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String d = s.format(date.getTime());
        etdate.setText(d);

        Button save = findViewById(R.id.addBtn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


}