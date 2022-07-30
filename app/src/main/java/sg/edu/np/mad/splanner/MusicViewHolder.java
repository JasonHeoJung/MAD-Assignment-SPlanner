package sg.edu.np.mad.splanner;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MusicViewHolder extends RecyclerView.ViewHolder {
    TextView songName;
    ImageView img;
//    Button play;
//    Button pause;
//    TextView currentTime;
//    TextView totalDuration;
//    SeekBar playerSeekBar;

    public MusicViewHolder(View v) {
        super(v);

        songName = v.findViewById(R.id.musicName);
        img = v.findViewById(R.id.songIcon);
    }
}
