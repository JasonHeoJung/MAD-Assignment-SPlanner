package sg.edu.np.mad.splanner;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {
    TextView title;
    TextView currentTime;
    TextView totalDuration;
    SeekBar seekBar;
    ImageView playAndPause;
    ImageView next;
    ImageView prev;
    ImageView loop;
    ImageView musicIcon;
    ArrayList<AudioModel> songsList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    int x = 0;
    boolean looping = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        title = findViewById(R.id.songTitle);
        currentTime = findViewById(R.id.current_time);
        totalDuration = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        playAndPause = findViewById(R.id.pause_play);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.previous);
        musicIcon = findViewById(R.id.music_icon);
        loop = findViewById(R.id.loop);

        title.setSelected(true);

        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");

        setResourcesWithMusic();

        MusicPlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTime.setText(convertToMilliseconds(mediaPlayer.getCurrentPosition() + ""));

                    if (mediaPlayer.isPlaying()) {
                        playAndPause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                        musicIcon.setRotation(x++);
                        if (currentTime.getText().equals(totalDuration.getText()) && !looping) {
                            playNextSong();
                        }
                        else if (currentTime.getText().equals(totalDuration.getText()) && looping) {
                            playMusic();
                        }
                    }
                    else {
                        playAndPause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                        musicIcon.setRotation(0);
                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    void setResourcesWithMusic() {
        currentSong = songsList.get(MyMediaPlayer.currentIndex);

        title.setText(currentSong.getTitle());

        totalDuration.setText(convertToMilliseconds(currentSong.getDuration()));

        playAndPause.setOnClickListener(v -> pausePlay());
        next.setOnClickListener(v -> playNextSong());
        prev.setOnClickListener(v -> playPreviousSong());
        loop.setOnClickListener(v -> loopSong());

        playMusic();
    }

    private void playMusic() {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playNextSong() {
        if (MyMediaPlayer.currentIndex == songsList.size() - 1) {
            return;
        }

        MyMediaPlayer.currentIndex += 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void playPreviousSong() {
        if (MyMediaPlayer.currentIndex == 0) {
            return;
        }

        MyMediaPlayer.currentIndex -= 1;
        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void loopSong() {
        if (looping) {
            looping = false;
            Toast.makeText(MusicPlayerActivity.this, "Looping disabled", Toast.LENGTH_SHORT).show();
        }
        else {
            looping = true;
            Toast.makeText(MusicPlayerActivity.this, "Looping enabled", Toast.LENGTH_SHORT).show();
        }
    }

    private void pausePlay() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        else {
            mediaPlayer.start();
        }
    }

    public static String convertToMilliseconds(String duration) {
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }
}