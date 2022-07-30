package sg.edu.np.mad.splanner.ui.home.schedule.journal;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import sg.edu.np.mad.splanner.R;

//implements View.OnClickListener
public class journalRecyclerviewholder extends RecyclerView.ViewHolder {
    TextView title;
    ConstraintLayout cl;
//    private journal_Adapter.RecyclerViewClickListener listener;

    public journalRecyclerviewholder(final View view){
        super(view);
        title = view.findViewById(R.id.journal_title);
        cl = view.findViewById(R.id.cl);
//        view.setOnClickListener(this);
    }


//    @Override
//    public void onClick(View view) {
//        listener.onClick(view, getAdapterPosition());
//    }
}