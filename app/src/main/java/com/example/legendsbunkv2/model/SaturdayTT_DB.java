package com.example.legendsbunkv2.model;

import com.orm.SugarRecord;

public class SaturdayTT_DB extends SugarRecord<SaturdayTT_DB> {
    public String subjectName;

    public SaturdayTT_DB() {
    }

    public SaturdayTT_DB(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
