package sg.edu.np.mad.splanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class recyclerview extends RecyclerView.Adapter<recyclerview.MyViewHolder> {

    private ArrayList<schedule> scheduleArraylist;

    public recyclerview(ArrayList<schedule> scheduleArraylist){
        this.scheduleArraylist = scheduleArraylist;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView classtxt;
        private TextView modtxt;
        private TextView timetxt;
        public MyViewHolder(final View view){
            super(view);
            classtxt = view.findViewById(R.id.classname);
            modtxt = view.findViewById(R.id.moduletext);
            timetxt = view.findViewById(R.id.timetext);
        }
    }
    @NonNull
    @Override
    public recyclerview.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lists, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerview.MyViewHolder holder, int position) {
        String clsname = scheduleArraylist.get(position).getClassname();
        holder.classtxt.setText(clsname);
        String mdtxt = scheduleArraylist.get(position).getClassname();
        holder.modtxt.setText(mdtxt);
        String titxt = scheduleArraylist.get(position).getClassname();
        holder.timetxt.setText(titxt);


    }

    @Override
    public int getItemCount() {
        return scheduleArraylist.size();
    }
}
