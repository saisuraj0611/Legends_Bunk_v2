package com.example.legendsbunkv2.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.R;
import com.example.legendsbunkv2.model.SubjectList;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

public class LogPastListAdapter extends UltimateViewAdapter<LogPastListAdapter.LogPastListViewHolder> {
    public ArrayList<SubjectList> items;
    OnItemClickListener mlistener;

    public LogPastListAdapter(ArrayList<SubjectList> items) {
        this.items = items;
    }

    @Override
    public LogPastListViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public LogPastListViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public LogPastListViewHolder onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.log_past_attendence_recycler_row,parent,false);
        LogPastListViewHolder viewHolder=new LogPastListViewHolder(v,mlistener);
        return viewHolder;
    }

    @Override
    public int getAdapterItemCount() {
        return items.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull LogPastListViewHolder holder, int position) {
        SubjectList sl=items.get(position);
        holder.subjectName.setText(sl.subject);

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }
    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnPresentClick(int position);
        void OnAbsentClick(int position);
        void OnCancelClick(int position);
    }

    public static class LogPastListViewHolder extends UltimateRecyclerviewViewHolder {
        public TextView subjectName;
        ImageView presButton,absButton,cancButton;
        public LogPastListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.subjectName_LogPastAttendane);
            presButton=itemView.findViewById(R.id.postPresent_logPastAttendance);
            absButton=itemView.findViewById(R.id.postAbsent_logPastAttendance);
            cancButton=itemView.findViewById(R.id.postCancelled_logPastAttendance);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnItemClick(position);
                    }
                }

            });

            presButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnPresentClick(position);
                    }
                }
            });

            absButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnAbsentClick(position);
                    }
                }
            });

            cancButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnCancelClick(position);
                    }
                }
            });
        }
    }
}
