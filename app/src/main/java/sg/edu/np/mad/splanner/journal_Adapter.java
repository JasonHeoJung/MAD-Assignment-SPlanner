package sg.edu.np.mad.splanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class journal_Adapter extends RecyclerView.Adapter<journalRecyclerviewholder>  {
    private final ArrayList<journal> journallist;
    private journal_Adapter.RecyclerViewClickListener listener;


    public journal_Adapter(ArrayList<journal> journallist, RecyclerViewClickListener listener) {
        this.journallist = journallist;
        this.listener = listener;
    }

    @NonNull
    @Override
    public journalRecyclerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_journal_recycler_view, parent, false);
        return new journalRecyclerviewholder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull final journalRecyclerviewholder holder, final int position) {
        holder.title.setText(journallist.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return journallist.size();
    }


    public interface RecyclerViewClickListener{
        void onClick(View v, int position);

    }

}