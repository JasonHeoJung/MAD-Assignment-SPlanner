package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends AppCompatActivity {

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            EditText etUsername = findViewById(R.id.etUsername);
            EditText etPassword = findViewById(R.id.etPassword);
            Button loginBtn = findViewById(R.id.loginBtn);

            String username = etUsername.getText().toString();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ImageView closeImg = findViewById(R.id.closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        EditText etUsername = findViewById(R.id.etUsername);
        EditText etPassword = findViewById(R.id.etPassword);

        etUsername.addTextChangedListener(tw);
        etPassword.addTextChangedListener(tw);

        Button loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyUser()) {
                    Intent Signup = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(Signup);
                }
                else {
                    // error feedback
                }
            }
        });
    }

    private Boolean verifyUser() {
        return true;
    }
}