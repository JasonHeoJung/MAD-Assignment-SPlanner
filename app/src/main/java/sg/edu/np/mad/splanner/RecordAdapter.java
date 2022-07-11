package sg.edu.np.mad.splanner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {
    private final ArrayList<Record> record;
    private ArrayList<String> recordIds;
    private FirebaseAuth auth;
    private DatabaseReference reference;

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
        holder.timeTaken.setText("Time Taken: "+ record.get(position).getTimeValue() + " min");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Test", auth.getCurrentUser().getUid() + "");
                reference.child(auth.getCurrentUser().getUid()).child("records").child(recordIds.get(holder.getAdapterPosition())).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return record.size();
    }
}
