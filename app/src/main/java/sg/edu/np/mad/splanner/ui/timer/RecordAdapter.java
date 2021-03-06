package sg.edu.np.mad.splanner.ui.timer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import sg.edu.np.mad.splanner.R;
import sg.edu.np.mad.splanner.Record;

public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {
    private final ArrayList<Record> record;
    private ArrayList<String> recordIds;
    private FirebaseAuth auth;
    private DatabaseReference reference;
    private Integer timeUsed;

    public RecordAdapter(ArrayList<String> recordIds, ArrayList<Record> record){
        this.recordIds = recordIds;
        this.record = record;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_recycler_view, parent, false);
        return new RecordViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("users");
        holder.recordName.setText("Name: "+ record.get(position).getRecordName());
        holder.recordComment.setText("Comments: "+ record.get(position).getRecordComment());
        holder.timeSet.setText("Time Set: "+ record.get(position).getTimeValue() + " min");
        timeUsed = Integer.valueOf(record.get(position).getTimeTaken());
        if (timeUsed < 60){
            holder.timeTaken.setText("Time Taken: " + timeUsed + " s");
        }
        else {
            Integer minutes = timeUsed/60;
            Integer seconds = timeUsed%60;
            holder.timeTaken.setText("Time Taken: " + minutes + " min " + seconds + " s");
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(auth.getCurrentUser().getUid()).child("records").child(recordIds.get(holder.getAdapterPosition())).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return record.size();
    }
}
