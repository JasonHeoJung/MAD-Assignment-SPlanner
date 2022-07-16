package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class EditProfile extends AppCompatActivity {

    EditText editName, editEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String Name = data.getStringExtra("name");
        String Email = data.getStringExtra("email");

        editName = findViewById(R.id.editTextName);
        editEmail = findViewById(R.id.editTextEmail);

        editName.setText(Name);
        editEmail.setText(Email);

    }
}