package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;

public class EditProfile extends AppCompatActivity {

    Button saveButton;
    EditText editName, editEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String Name = data.getStringExtra("name");
        String Email = data.getStringExtra("email");

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();

        saveButton = findViewById(R.id.saveProf);
        editName = findViewById(R.id.etName);
        editEmail = findViewById(R.id.editTextEmail);

        ArrayList<String> fcList = new ArrayList();

        editName.setText(Name);
        editEmail.setText(Email);

        editName.addTextChangedListener(tw);
        editEmail.addTextChangedListener(tw);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editName.getText().toString().length() == 0 || editEmail.getText().toString().length() == 0) {
                    Toast.makeText(EditProfile.this, "A Field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = editName.getText().toString();
                final String email = editEmail.getText().toString();
                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                user.updateProfile(profileChangeRequest);
                user.updateEmail(email);
                finish();
            }
        });
    }
    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = editName.getText().toString();
            String email = editEmail.getText().toString();

            boolean valid = false;

            if (username.trim().length() == 0) {
                editName.setError("Name is Required");
            }
            if (email.trim().length() == 0) {
                editEmail.setError("Email is Required");
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editEmail.setError("Valid Email is Required");
            } else {
                valid = true;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
