package com.example.legendsbunkv2.model;

import com.orm.SugarRecord;

public class FridayTT_DB extends SugarRecord<FridayTT_DB> {
    public String subjectName;

    public FridayTT_DB() {
    }

    public FridayTT_DB(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
