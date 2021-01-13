package com.example.legendsbunkv2.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.R;
import com.example.legendsbunkv2.model.SubjectList;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

public class DeleteSubjectAdapter extends UltimateViewAdapter<DeleteSubjectAdapter.DeleteSubjectViewHolder> {
    public ArrayList<SubjectList> items;

    public DeleteSubjectAdapter(ArrayList<SubjectList> items) {
        this.items = items;
    }

    @Override
    public DeleteSubjectViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public DeleteSubjectViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public DeleteSubjectViewHolder onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.delete_subject_main_recycle_row,parent,false);
        DeleteSubjectViewHolder viewHolder=new DeleteSubjectViewHolder(v);
        viewHolder.setIsRecyclable(false);
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
    public void onBindViewHolder(@NonNull DeleteSubjectViewHolder holder, final int position) {
        SubjectList sl=items.get(position);
        holder.subjectName.setText(sl.subject);
        if(items.get(position).totalCancelled==1)
            holder.deletable.setChecked(true);
        else holder.deletable.setChecked(false);

        holder.deletable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    items.get(position).totalCancelled=1;
                }
                else {
                    items.get(position).totalCancelled=0;
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public class DeleteSubjectViewHolder extends UltimateRecyclerviewViewHolder {
        TextView subjectName;
        CheckBox deletable;
        public DeleteSubjectViewHolder(View itemView) {
            super(itemView);
            subjectName=itemView.findViewById(R.id.subjectNameDeleteSubjectRow);
            deletable=itemView.findViewById(R.id.checkBoxDeleteSubjectRow);

        }
    }
}
