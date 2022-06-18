package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText etEmail;
    private EditText etPassword;
    private Button loginBtn;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        loginBtn = findViewById(R.id.loginBtn);
        pb = findViewById(R.id.progressBar);
        ImageView closeImg = findViewById(R.id.closeImg);

        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etEmail.addTextChangedListener(tw);
        etPassword.addTextChangedListener(tw);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                pb.setVisibility(View.VISIBLE);
                loginUser(email, password);
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
            String username = etEmail.getText().toString();
            String password = etPassword.getText().toString();

            if (username.trim().length() != 0 && password.trim().length() != 0) {
                loginBtn.setEnabled(true);
                loginBtn.setBackgroundColor(loginBtn.getContext().getResources().getColor(R.color.purple_500));
                loginBtn.setTextColor(loginBtn.getContext().getResources().getColor(R.color.white));
            }
            else {
                loginBtn.setEnabled(false);
                loginBtn.setBackgroundColor(loginBtn.getContext().getResources().getColor(R.color.white));
                loginBtn.setTextColor(Color.rgb(94, 94, 94));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void loginUser(String email, String password) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    try {
                        throw task.getException();
                    }
                    catch (FirebaseAuthInvalidUserException | FirebaseAuthInvalidCredentialsException e) {
                        etEmail.setError("Email or Password is invalid");
                        etPassword.setError("Email or Password is invalid");
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                }
            }
        });
    }
}