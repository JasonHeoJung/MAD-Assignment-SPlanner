package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView signuptxt = findViewById(R.id.signuptxt);
        signuptxt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent Signup = new Intent(LoginActivity.this, SignupActivity.class);
                Signup.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(Signup);
                return false;
            }
        });

        Button LoginButton = findViewById(R.id.Signup);
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}