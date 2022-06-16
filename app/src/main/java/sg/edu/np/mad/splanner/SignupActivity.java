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

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

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

        Button signupBtn = findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addUser()) {
                    Intent Signup = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(Signup);
                }
                else {
                    // error feedback
                }
            }
        });
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            EditText etUsername = findViewById(R.id.etUsername);
            EditText etPassword = findViewById(R.id.etPassword);
            Button signupBtn = findViewById(R.id.signupBtn);

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (username.trim().length() != 0 && password.trim().length() != 0) {
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

    private Boolean addUser() {
        return true;
    }
}