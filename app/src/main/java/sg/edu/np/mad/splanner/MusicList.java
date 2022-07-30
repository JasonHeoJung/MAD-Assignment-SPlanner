package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

public class MusicList extends AppCompatActivity {
//    private FirebaseAuth auth;
//    private DatabaseReference reference;
    private RecyclerView recyclerView;
//    private ArrayList<String> musicIds;
//    private ArrayList<Music> musicList;
    ArrayList<AudioModel> songsList = new ArrayList<>();
    private TextView noSongs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

//        auth = FirebaseAuth.getInstance();
//        reference = FirebaseDatabase.getInstance().getReference("users");
        recyclerView = findViewById(R.id.musicList);
        noSongs = findViewById(R.id.no_songs_text);

        if (!checkPermission()) {
            requestPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION
        };

        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";

        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, null);

        while(cursor.moveToNext()) {
            AudioModel songData = new AudioModel(cursor.getString(1), cursor.getString(0), cursor.getString(2));

            if (new File(songData.getPath()).exists()) {
                songsList.add(songData);
            }
        }

        if (songsList.size() == 0) {
            noSongs.setVisibility(View.VISIBLE);
        }
        else {
            setAdapter();
        }

        Button back = findViewById(R.id.backToHome);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MusicList.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else {
            return false;
        }
    }

    void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MusicList.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MusicList.this, "READ PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTINGS", Toast.LENGTH_SHORT).show();
        }
        else {
            ActivityCompat.requestPermissions(MusicList.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 123);
        }
    }

    private void setAdapter() {
        MusicAdapter adapter = new MusicAdapter(songsList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (recyclerView != null) {
            recyclerView.setAdapter(new MusicAdapter(songsList, this));
        }
    }
}