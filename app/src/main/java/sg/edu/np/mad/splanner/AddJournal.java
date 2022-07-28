package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddJournal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal);
        EditText ettitle = findViewById(R.id.addTitle);
        TextView etdate = findViewById(R.id.editviewdate);
        EditText etdes = findViewById(R.id.editdes);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        String d = s.format(date.getTime());
        etdate.setText(d);
    }
}