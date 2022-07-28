package sg.edu.np.mad.splanner;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class journal_Adapter extends RecyclerView.Adapter<journalRecyclerviewholder>  implements Filterable {
    private final ArrayList<journal> journallist;
    private final ArrayList<journal> journallistfull;
    private final ArrayList<String> journalid;

    private journal_Adapter.RecyclerViewClickListener listener;
    Activity activity;

    public journal_Adapter(ArrayList<journal> journallist, ArrayList<String> journalid, Activity activity) {
        this.journallistfull = journallist;
        this.journalid = journalid;
        this.activity = activity;
        this.journallist = new ArrayList<>(journallistfull);
    }

    @NonNull
    @Override
    public journalRecyclerviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_journal_adapter, parent, false);

        return new journalRecyclerviewholder(item);
    }


    @Override
    public void onBindViewHolder(@NonNull final journalRecyclerviewholder holder, final int position) {
        Log.v("JOURNAL SIZE", journallist.get(position).getTitle());
        holder.title.setText(journallist.get(position).getTitle());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, journal_des.class);
                intent.putExtra("Id", journalid.get(position));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return journallist.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private final Filter newFIlter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<journal> filteredArrayList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredArrayList.addAll(journallistfull);
            }
            else{
                String filterpattern = constraint.toString().toLowerCase().trim();
                for(journal j : journallistfull){
                    filteredArrayList.add(j);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredArrayList;
            results.count = filteredArrayList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            journallist.clear();
            journallist.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);

    }

}