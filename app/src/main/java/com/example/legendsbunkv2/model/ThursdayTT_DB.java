package com.example.legendsbunkv2.model;

import com.orm.SugarRecord;

public class ThursdayTT_DB extends SugarRecord<ThursdayTT_DB> {
    public String subjectName;

    public ThursdayTT_DB() {
    }

    public ThursdayTT_DB(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
