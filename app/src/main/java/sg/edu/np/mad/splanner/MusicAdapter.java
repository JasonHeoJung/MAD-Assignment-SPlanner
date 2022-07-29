package sg.edu.np.mad.splanner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicViewHolder> {
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private ArrayList<String> musicIds;
    private ArrayList<Music> musicList;
    MediaPlayer mediaPlayer;
    Activity activity;

    public MusicAdapter(ArrayList<String> musicIds, ArrayList<Music> musicList, Activity activity) {
        this.musicIds = musicIds;
        this.musicList = musicList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MusicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");

        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_music_recycler_view, parent, false);
        return new MusicViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String name = musicList.get(position).getName();
        mediaPlayer = new MediaPlayer();

        holder.songName.setText(name);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = musicList.get(position).getUrl();
                Log.v("URL Retrieve", url);

                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                try {
                    mediaPlayer.setDataSource(url);

                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(activity.getApplicationContext(), "Audio started playing...", Toast.LENGTH_SHORT).show();
            }
        });
        holder.pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }
                else {
                    Toast.makeText(activity.getApplicationContext(), "Audio has not played", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(auth.getCurrentUser().getUid()).child("songs").child(musicIds.get(position)).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return musicIds.size();
    }
}
