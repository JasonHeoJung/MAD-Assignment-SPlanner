package sg.edu.np.mad.splanner;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class journalRecyclerviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{
    TextView title;
    private journal_Adapter.RecyclerViewClickListener listener;
    public journalRecyclerviewholder(final View view){
        super(view);
        title = view.findViewById(R.id.journal_title);
        view.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition());
    }
}
