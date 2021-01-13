package com.example.legendsbunkv2.model;

import com.orm.SugarRecord;

public class SundayTT_DB extends SugarRecord<SundayTT_DB> {
    public String subjectName;

    public SundayTT_DB() {
    }

    public SundayTT_DB(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
