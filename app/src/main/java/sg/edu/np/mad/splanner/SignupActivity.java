package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button signupBtn;
    private ProgressBar pb;
    private String userID;
    FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        signupBtn = findViewById(R.id.signupBtn);
        pb = findViewById(R.id.progressBar);
        ImageView closeImg = findViewById(R.id.closeImg);

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etUsername.addTextChangedListener(tw);
        etEmail.addTextChangedListener(tw);
        etPassword.addTextChangedListener(tw);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                pb.setVisibility(View.VISIBLE);
                createUser(username, email, password);

                pb.setVisibility(View.GONE);
            }
        });
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = etUsername.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            boolean valid = false;

            if (username.trim().length() == 0) {
                etUsername.setError("Name is Required");
            }
            if (email.trim().length() == 0) {
                etEmail.setError("Email is Required");
            }
            else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.setError("Valid Email is Required");
            }
            else if (password.trim().length() == 0) {
                etPassword.setError("Password is Required");
            }
            else if (password.trim().length() < 6) {
                etPassword.setError("Password needs to be at least 6 characters");
            }
            else {
                valid = true;
            }

            if (valid) {
                signupBtn.setEnabled(true);
                signupBtn.setBackgroundColor(signupBtn.getContext().getResources().getColor(R.color.purple_500));
                signupBtn.setTextColor(signupBtn.getContext().getResources().getColor(R.color.white));
            }
            else {
                signupBtn.setEnabled(false);
                signupBtn.setBackgroundColor(signupBtn.getContext().getResources().getColor(R.color.white));
                signupBtn.setTextColor(Color.rgb(94, 94, 94));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void createUser(String username, String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                    reference.child(auth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                userID = auth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("Uname", username);
                                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                try {
                                    throw task.getException();
                                }
                                catch (Exception e) {
                                    Log.e(TAG, e.toString());
                                }
                            }
                        }
                    });
                }
                else {
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e) {
                        etEmail.setError("Email is invalid");
                        etEmail.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException e) {
                        etEmail.setError("Email is already in use");
                        etEmail.requestFocus();
                    }
                    catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });
    }
}