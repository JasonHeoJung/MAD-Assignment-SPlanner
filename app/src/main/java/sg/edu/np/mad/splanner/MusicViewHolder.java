package sg.edu.np.mad.splanner;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MusicViewHolder extends RecyclerView.ViewHolder {
    TextView songName;
    Button play;
    Button pause;
    Button remove;

    public MusicViewHolder(View v) {
        super(v);

        songName = v.findViewById(R.id.musicName);
        play = v.findViewById(R.id.playMusic);
        pause = v.findViewById(R.id.pauseMusic);
        remove = v.findViewById(R.id.deleteMusic);
    }
}
