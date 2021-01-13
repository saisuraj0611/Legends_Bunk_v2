package com.example.legendsbunkv2.model;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendenceDB extends SugarRecord<AttendenceDB> implements Comparable<AttendenceDB> {
    public int isPresent=0,isAbsent=0,isCancelled=0;
    public long foreignID;
    public Date date;

    public AttendenceDB() {
    }

    public AttendenceDB(int isPresent, int isAbsent, int isCancelled, long foreignID, Date date) {
        this.isPresent = isPresent;
        this.isAbsent = isAbsent;
        this.isCancelled = isCancelled;
        this.foreignID = foreignID;
        this.date = date;
    }

    public int getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(int isPresent) {
        this.isPresent = isPresent;
    }

    public int getIsAbsent() {
        return isAbsent;
    }

    public void setIsAbsent(int isAbsent) {
        this.isAbsent = isAbsent;
    }

    public int getIsCancelled() {
        return isCancelled;
    }

    public void setIsCancelled(int isCancelled) {
        this.isCancelled = isCancelled;
    }

    public long getForeignID() {
        return foreignID;
    }

    public void setForeignID(long foreignID) {
        this.foreignID = foreignID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String toString(){

        DateFormat dateFormat = new SimpleDateFormat("EEE, dd-MM-YYYY");
        return ("ForeignID="+ foreignID +" Present="+isPresent+" Absent="+isAbsent+" Cancelled="+isCancelled+" Date="+dateFormat.format(date));
    }

    @Override
    public int compareTo(AttendenceDB adb){
        return this.date.compareTo(adb.date);
    }
}
