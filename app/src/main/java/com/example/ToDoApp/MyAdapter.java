package com.example.ToDoApp;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private RealmResults<Task> mtask;
    OnItemClickListener mlistener;
    public MyAdapter(RealmResults<Task> data,OnItemClickListener listener) {
        mtask=data;
        mlistener=listener;
    }
    public interface OnItemClickListener
    {
        public int onButtonClick(int position);
        public void onItemClick(int position);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new MyViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Task task=mtask.get(position);
        holder.taskname.setText(task.getTaskName());
        holder.duedate.setText(new SimpleDateFormat().format(task.getDueDate()));
        holder.item.setBackgroundColor(Color.parseColor(task.getColor()));
        if(task.getChecked().equals("true")) {
            holder.button.setImageResource(R.drawable.mbuttoncheck);
        }
    }


    @Override
    public int getItemCount() {
        return mtask.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView taskname;
        private TextView duedate;
        private RelativeLayout item;
        private ImageView button;
        private OnItemClickListener mitem;

        public MyViewHolder(@NonNull View itemView,OnItemClickListener listener) {
            super(itemView);
            taskname = itemView.findViewById(R.id.taskname);
            duedate=itemView.findViewById(R.id.date);
            item=itemView.findViewById(R.id.background);
            button=itemView.findViewById(R.id.click);
            mitem=listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(getAdapterPosition()!=RecyclerView.NO_POSITION);
                    mitem.onItemClick(getAdapterPosition());
                }
            });
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(getAdapterPosition()!=RecyclerView.NO_POSITION);
                    mitem.onButtonClick(getAdapterPosition());
            button.setImageResource(R.drawable.ic_check_box1);
        }
    }
}
