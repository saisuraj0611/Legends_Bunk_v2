package com.example.legendsbunkv2.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.example.legendsbunkv2.R;
import com.example.legendsbunkv2.model.AttendenceDB;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AttendenceHistoryAdapter extends UltimateViewAdapter<AttendenceHistoryAdapter.AttendenceHistoryViewHolder> {
    public ArrayList<AttendenceDB> items;
    OnItemClickListener mlistener;

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }

    @Override
    public AttendenceHistoryViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public AttendenceHistoryViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public AttendenceHistoryViewHolder onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_history_row,parent,false);
        AttendenceHistoryViewHolder viewHolder=new AttendenceHistoryViewHolder(v,mlistener);
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
    public void onBindViewHolder(@NonNull AttendenceHistoryViewHolder holder, int position) {
    AttendenceDB adb=items.get(position);
        holder.subjectNameHistory.setText(new Methods().getSubjectListNameFromForeignID(adb.foreignID));
        Log.d("historyatt", "onBindViewHolder: "+new Methods().getSubjectListNameFromForeignID(adb.foreignID));
        if(adb.getIsPresent()==1)
            holder.attendenceStatusHistory.setText("Present");
        else if (adb.getIsAbsent()==1)
            holder.attendenceStatusHistory.setText("Absent");
        else holder.attendenceStatusHistory.setText("Cancelled");

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        holder.datePosted.setText(dateFormat.format(adb.date));
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }



    public class AttendenceHistoryViewHolder extends UltimateRecyclerviewViewHolder {

        public TextView subjectNameHistory,datePosted,attendenceStatusHistory;

        public AttendenceHistoryViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            subjectNameHistory=itemView.findViewById(R.id.subjectNameHistory);
            datePosted=itemView.findViewById(R.id.datePosted);
            attendenceStatusHistory=itemView.findViewById(R.id.AttendeneStatusHistory);

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
        }
    }

    public AttendenceHistoryAdapter(ArrayList<AttendenceDB> items){
        this.items=items;
    }
}
