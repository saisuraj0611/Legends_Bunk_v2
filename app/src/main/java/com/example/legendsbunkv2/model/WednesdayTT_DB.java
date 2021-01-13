package com.example.legendsbunkv2.model;

import com.orm.SugarRecord;

public class WednesdayTT_DB extends SugarRecord<WednesdayTT_DB> {
    public String subjectName;

    public WednesdayTT_DB() {
    }

    public WednesdayTT_DB(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
