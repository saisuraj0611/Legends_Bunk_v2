package com.example.legendsbunkv2.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.R;
import com.example.legendsbunkv2.model.SubjectList;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class VacationAdapter extends UltimateViewAdapter<VacationAdapter.VacationViewHolder> {
    public ArrayList<SubjectList> items;

    public VacationAdapter(ArrayList<SubjectList> items) {
        this.items = items;
    }

    @Override
    public VacationViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public VacationViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.vacation_recycle_row,parent,false);
        VacationViewHolder viewHolder=new VacationViewHolder(v);
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
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        DecimalFormat decimalFormat=new DecimalFormat("00.00 ");
        SubjectList sl=items.get(position);
        holder.subjectNames.setText(sl.subject);
        holder.totalAttendancePercentage.setText(decimalFormat.format(calculateTotalAttendence(sl.totalPresents,sl.getTotalAbsents()))+"%");
        holder.status.setText(getBunkStatus(sl));
    }


    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class VacationViewHolder extends UltimateRecyclerviewViewHolder{
    public TextView subjectNames,totalAttendancePercentage,status;
        public VacationViewHolder(View itemView) {
            super(itemView);
            subjectNames=itemView.findViewById(R.id.subjectNameVacation);
            totalAttendancePercentage=itemView.findViewById(R.id.subjectTotalPercentageVacation);
            status=itemView.findViewById(R.id.subjectStatusVacation);
        }
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
        Log.d("abcde", "calculateTotalAttendence: subject details="+totalPresents+"="+totalPresents);
        return ( ( (float)totalPresents/ (float)(totalPresents+totalAbsents))*100.0f);
    }
}
