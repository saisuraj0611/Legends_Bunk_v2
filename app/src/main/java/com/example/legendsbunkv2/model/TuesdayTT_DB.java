package com.example.legendsbunkv2.model;

import com.orm.SugarRecord;

public class TuesdayTT_DB extends SugarRecord<TuesdayTT_DB> {
    public String subjectName;

    public TuesdayTT_DB() {
    }

    public TuesdayTT_DB(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
