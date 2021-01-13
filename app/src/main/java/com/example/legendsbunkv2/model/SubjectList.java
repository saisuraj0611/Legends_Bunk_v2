package com.example.legendsbunkv2.model;

import com.orm.SugarRecord;

public class SubjectList extends SugarRecord<SubjectList> {
    public String subject;
    public int totalPresents=0,totalAbsents=0,totalCancelled=0;


    public SubjectList() {
    }



    public int getTotalCancelled() {
        return totalCancelled;
    }

    public void setTotalCancelled(int totalCancelled) {
        this.totalCancelled = totalCancelled;
    }

    public SubjectList(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getTotalPresents() {
        return totalPresents;
    }

    public void setTotalPresents(Integer totalPresents) {
        this.totalPresents = totalPresents;
    }

    public int getTotalAbsents() {
        return totalAbsents;
    }

    public void setTotalAbsents(Integer totalAbsents) {
        this.totalAbsents = totalAbsents;
    }

    public String toString(){
        return ("Subject from primary list ="+subject+" id="+getId()+" totPres="+this.totalPresents+" totAbs="+this.totalAbsents+" totCancel="+this.totalCancelled);
    }

    public float getSubjectTotalAttendence(){
        int pres=this.getTotalPresents();
        int abs=this.getTotalAbsents();
        if(pres+abs==0)
            return 0f;
        return ((pres/(pres+abs)*100.0f));
    }

    public void changeItem(String text){
        subject=text;
    }
}
