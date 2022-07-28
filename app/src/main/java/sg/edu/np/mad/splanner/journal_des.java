package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class journal_des extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_des);
        TextView title = findViewById(R.id.journal_title_2);
        TextView Description = findViewById(R.id.editdes);
        TextView date = findViewById(R.id.editviewdate);


        Button editbtn = findViewById(R.id.Edtibtn);
    }
}