package sg.edu.np.mad.splanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoListRecyclerView extends RecyclerView.Adapter<ToDoListRecyclerView.myViewHolder> {
    ArrayList<task> taskList;
    OnNoteListener mOnNoteListener;

    public ToDoListRecyclerView(ArrayList<task> taskList, OnNoteListener onNoteListener) {
        this.taskList = taskList;
        this.mOnNoteListener = onNoteListener;
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView taskName;
        private TextView dueDate;
        private TextView title;
        private TextView title2;
        private Button complete;
        OnNoteListener onNoteListener;

        public myViewHolder(View v, OnNoteListener onNoteListener) {
            super(v);
            taskName = v.findViewById(R.id.Task);
            dueDate = v.findViewById(R.id.DueDate);
            complete = v.findViewById(R.id.status);
            title = v.findViewById(R.id.textView11);
            title2 = v.findViewById(R.id.textView12);
            this.onNoteListener = onNoteListener;

            complete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }

    @NonNull
    @Override
    public ToDoListRecyclerView.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_to_do_list_recycler_view, parent, false);
        return new myViewHolder(itemView, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListRecyclerView.myViewHolder holder, int position) {
        String name = taskList.get(position).getTaskName();
        String dd = taskList.get(position).getDueDate();

        if (position >= 1) {
            holder.title.setVisibility(View.GONE);
            holder.title2.setVisibility(View.GONE);
        }

        holder.taskName.setText(name);
        holder.dueDate.setText(dd);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}