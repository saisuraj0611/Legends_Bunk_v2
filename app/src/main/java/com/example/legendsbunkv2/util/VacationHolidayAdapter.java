package com.example.legendsbunkv2.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

public class VacationHolidayAdapter extends UltimateViewAdapter<VacationHolidayAdapter.VacationHolidaysViewHolder> {
    public ArrayList<String> items;
    public boolean isSelected[];

    public VacationHolidayAdapter(ArrayList<String> items) {
        Log.d("abcde", "VacationHolidayAdapter: isBoolean entered and constructed");
        this.items = items;
        isSelected=new boolean[items.size()];
    }

    @Override
    public VacationHolidaysViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public VacationHolidaysViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public VacationHolidaysViewHolder onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.vacation_holidays_row_recycler,parent,false);
        VacationHolidaysViewHolder viewHolder=new VacationHolidaysViewHolder(v);
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
    public void onBindViewHolder(@NonNull VacationHolidaysViewHolder holder, final int position) {
        String str=items.get(position);
        holder.dateDisplay.setText(items.get(position));
        Log.d("abcde", "onBindViewHolder: "+position);
        if(isSelected[position])
            holder.holidayChecker.setChecked(true);
        else holder.holidayChecker.setChecked(false);
        holder.holidayChecker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isSelected[position]=true;
                }
                else isSelected[position]=false;
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

    public class VacationHolidaysViewHolder extends UltimateRecyclerviewViewHolder {
        TextView dateDisplay;
        CheckBox holidayChecker;
        public VacationHolidaysViewHolder(View itemView) {
            super(itemView);
            dateDisplay=itemView.findViewById(R.id.dateVacationHolidaysRow);
            holidayChecker=itemView.findViewById(R.id.checkBoxVacationHolidaysRows);

        }
    }
}
