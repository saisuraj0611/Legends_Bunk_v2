package com.example.legendsbunkv2.util;

import android.view.View;
import android.widget.TextView;

import com.example.legendsbunkv2.R;
import com.example.legendsbunkv2.model.AttendenceDB;
import com.hereshem.lib.recycler.MyViewHolder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EVHolder extends MyViewHolder<AttendenceDB> {
    TextView subjectName,subjectStatus,datePosted;
    public EVHolder(View v) {
        super(v);
       subjectName=v.findViewById(R.id.subjectNameHistory);
       subjectStatus=v.findViewById(R.id.AttendeneStatusHistory);
       datePosted=v.findViewById(R.id.datePosted);
    }
    @Override
    public void bindView(AttendenceDB adb) {

        subjectName.setText(new Methods().getSubjectListNameFromForeignID(adb.foreignID));
        if(adb.getIsPresent()==1)
            subjectStatus.setText("Present");
        else if (adb.getIsAbsent()==1)
            subjectStatus.setText("Absent");
        else subjectStatus.setText("Cancelled");

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
        datePosted.setText(dateFormat.format(adb.date));
    }
}