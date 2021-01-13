package com.example.legendsbunkv2.model;

import androidx.annotation.NonNull;

import com.orm.SugarRecord;

public class MondayTT_DB extends SugarRecord<MondayTT_DB> {
    public String subjectName;

    @NonNull
    @Override
    public String toString() {
        return ("Monday "+subjectName);
    }

    public MondayTT_DB() {
    }

    public MondayTT_DB(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
