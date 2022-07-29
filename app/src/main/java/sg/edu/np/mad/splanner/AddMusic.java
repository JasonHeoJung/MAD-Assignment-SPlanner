package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddMusic extends AppCompatActivity {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private EditText songName;
    private EditText songURL;
    private Button add;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_music);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        songName = findViewById(R.id.inputSongName);
        songURL = findViewById(R.id.inputSongURL);
        add = findViewById(R.id.addNewMusic);
        cancel = findViewById(R.id.cancelAddMusic);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = songName.getText().toString();
                String url = songURL.getText().toString();

                DatabaseReference musicRef = reference.child(auth.getCurrentUser().getUid()).child("songs").push();
                musicRef.setValue(new Music(name, url));

                Intent musicPage = new Intent(AddMusic.this, MusicList.class);
                startActivity(musicPage);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent musicPage = new Intent(AddMusic.this, MusicList.class);
                startActivity(musicPage);
            }
        });
    }
}