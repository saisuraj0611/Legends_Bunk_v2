package com.example.legendsbunkv2.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.legendsbunkv2.R;
import com.example.legendsbunkv2.model.SubjectList;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SubjectListAdapter extends UltimateViewAdapter<SubjectListAdapter.SubjectListViewHolder> {

    public ArrayList<SubjectList> items;
    OnItemClickListener mlistener;


    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnPresentClick(int position);
        void OnAbsentClick(int position);
        void OnCancelClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }

    public static class SubjectListViewHolder extends UltimateRecyclerviewViewHolder{
        public TextView totalSubjectPercentage,totalPresent,totalAbsent,totalCancelled, subjectName,bunkStatus;
        public Button pres,abs,canc;

        public SubjectListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.subjectName);
            totalSubjectPercentage=itemView.findViewById(R.id.subjectTotalPercentage);
            totalPresent=itemView.findViewById(R.id.totalPresentNo);
            totalAbsent=itemView.findViewById(R.id.totalAbsentNo);
            totalCancelled=itemView.findViewById(R.id.totalCancelledNo);
            pres=itemView.findViewById(R.id.presentButton);
            abs=itemView.findViewById(R.id.absentButton);
            canc=itemView.findViewById(R.id.cancelledButton);
            bunkStatus=itemView.findViewById(R.id.status);


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

            pres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnPresentClick(position);
                    }
                }
            });

            abs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnAbsentClick(position);
                    }
                }
            });

            canc.setOnClickListener(new View.OnClickListener() {
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

    public SubjectListAdapter(ArrayList<SubjectList> items){
        this.items=items;
    }

    @Override
    public SubjectListViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public SubjectListViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public SubjectListViewHolder onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.main_recycle_row,parent,false);
        SubjectListViewHolder viewHolder=new SubjectListViewHolder(v,mlistener);
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
    public void onBindViewHolder(@NonNull SubjectListViewHolder holder, int position) {
        DecimalFormat decimalFormat=new DecimalFormat("0.00");

        SubjectList sl=items.get(position);
        holder.subjectName.setText(sl.getSubject());
        holder.totalSubjectPercentage.setText(decimalFormat.format((calculateTotalAttendence(sl.getTotalPresents(),sl.getTotalAbsents())))+" %");
//        holder.totalSubjectPercentage.setText(Float.toString(sl.getSubjectTotalAttendence()));
        holder.totalPresent.setText(String.valueOf(sl.getTotalPresents()));
        holder.totalAbsent.setText(String.valueOf(sl.totalAbsents));
        holder.totalCancelled.setText(String.valueOf(sl.totalCancelled));
        holder.bunkStatus.setText(getBunkStatus (sl));


    }

    private String getBunkStatus(SubjectList sl) {
        float thisSubjectAttendencePerc=0;
        if(sl.totalPresents+sl.totalAbsents ==0){
            thisSubjectAttendencePerc=100.0f;
        }
        else
            thisSubjectAttendencePerc=( (float)sl.getTotalPresents() / ( (float)(sl.totalPresents+sl.totalAbsents)))*100.0f;

        if(thisSubjectAttendencePerc >= new Methods().getThresholdAttendance()){
            int bunks=0;
            while((( (float)(sl.getTotalPresents()) / ( (float)(sl.totalPresents+sl.totalAbsents+bunks)))*100.0f) >= new Methods().getThresholdAttendance()){
                bunks++;
            }
            bunks--;

            if(bunks<=0){
                return("You cant Bunk any Classes");
            }
            else{
                return("Next "+bunks+" classes can be bunked");
            }
        }
        else{
            int mustAttend=0;
            while((( (float)(sl.getTotalPresents()+mustAttend) / ( (float)(sl.totalPresents+sl.totalAbsents+mustAttend)))*100.0f) < new Methods().getThresholdAttendance()){
                mustAttend++;
            }


            if(mustAttend<=0){
                return("Error");
            }
            else{
                return("Next "+mustAttend+" classes must be attended");
            }
        }
    }

    private float calculateTotalAttendence(int totalPresents, int totalAbsents) {
        if((totalPresents+totalAbsents)==0)
            return 0f;
        return ( ( (float)totalPresents/ (float)(totalPresents+totalAbsents))*100.0f);
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
       return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

}
