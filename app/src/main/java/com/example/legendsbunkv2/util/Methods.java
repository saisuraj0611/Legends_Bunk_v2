package com.example.legendsbunkv2.util;

import android.util.Log;

import com.example.legendsbunkv2.model.AttendenceDB;
import com.example.legendsbunkv2.model.FridayTT_DB;
import com.example.legendsbunkv2.model.MondayTT_DB;
import com.example.legendsbunkv2.model.SaturdayTT_DB;
import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.model.SundayTT_DB;
import com.example.legendsbunkv2.model.ThursdayTT_DB;
import com.example.legendsbunkv2.model.TuesdayTT_DB;
import com.example.legendsbunkv2.model.WednesdayTT_DB;
import com.orm.StringUtil;
import com.pixplicity.easyprefs.library.Prefs;

import org.apache.commons.lang.time.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Methods {

    public void addSubject(String subjectName){
        SubjectList sl=new SubjectList(subjectName);
        sl.save();

    }
    public long getSubjectListID (String subjectName){
        List<SubjectList> list =SubjectList.find(SubjectList.class,"subject=?",subjectName);
        SubjectList sl=list.get(0);
        return sl.getId();
    }

    public String getSubjectListNameFromForeignID (long foreignID){
        SubjectList sl =SubjectList.findById(SubjectList.class,foreignID);

        return sl.getSubject();
    }

    public void postPresent(String subjectName, Date date){
        long primaryID=getSubjectListID(subjectName);
        AttendenceDB adb=new AttendenceDB(1,0,0,primaryID,date);
        adb.save();
//        SubjectList sl=SubjectList.findById(SubjectList.class,primaryID);
//        sl.setTotalPresents(sl.getTotalPresents()+1);
//        sl.save();
    }
    public void postAbsent(String subjectName, Date date){
        long primaryID=getSubjectListID(subjectName);
        AttendenceDB adb=new AttendenceDB(0,1,0,primaryID,date);
        adb.save();
    }
    public void postCancelled(String subjectName, Date date){
        long primaryID=getSubjectListID(subjectName);
        AttendenceDB adb=new AttendenceDB(0,0,1,primaryID,date);
        adb.save();
    }

    public List<AttendenceDB> getSubjectIndividualAttendenceList(String subjectName){
        long foreignID=getSubjectListID(subjectName);
        List<AttendenceDB> list=AttendenceDB.find(AttendenceDB.class, StringUtil.toSQLName("foreignID") +"=?",Long.toString(foreignID));
//        List<AttendenceDB> list = AttendenceDB.findWithQuery(AttendenceDB.class, "Select * from AttendenceDB where foreignID = ?", "1");
//        List<AttendenceDB> list= AttendenceDB.listAll(AttendenceDB.class);
        return list;
    }

    public void calcIndividualSubjectAttendence(String subjectName){
        int present=0,absent=0,cancelled=0;
        long fid=new Methods().getSubjectListID(subjectName);
        List<AttendenceDB> list=AttendenceDB.find(AttendenceDB.class,StringUtil.toSQLName("foreignID")+"=?",Long.toString(fid));
        for (AttendenceDB adb:list){
            present+=adb.getIsPresent();
            absent+=adb.getIsAbsent();
            cancelled+=adb.getIsCancelled();
        }
//        AttendenceDB adb=AttendenceDB.findById(AttendenceDB.class,fid);
//        adb.setIsPresent(present);
//        adb.setIsAbsent(absent);
//        adb.setIsCancelled(cancelled);
//        adb.save();

        SubjectList sl=SubjectList.findById(SubjectList.class,fid);
        sl.setTotalPresents(present);
        sl.setTotalAbsents(absent);
        sl.setTotalCancelled(cancelled);
//        Log.d("abcde", "calcIndividualSubjectAttendence:SUBJECTLIST OBJ= "+sl.toString());
        sl.save();

//        Log.d("abcde", "calcIndividualSubjectAttendence:updated subject list db "+"Pres="+sl.getTotalPresents()+"Abs="+sl.getTotalAbsents()+"Canc="+sl.getTotalCancelled());


    }

    public void calculateTotalAttendenceAllSubjects() {
        for (SubjectList sl : SubjectList.listAll(SubjectList.class)) {
            new Methods().calcIndividualSubjectAttendence(sl.getSubject());
//            Log.d("abcde", "calculateTotalAttendenceAllSubjects: " + sl.toString());
//            Log.d("abcde", "calculateTotalAttendenceAllSubjects: " + "Subject from primary list ="
//                    +sl.getSubject()+" id="+sl.getId()+" totPres="+sl.totalPresents+" totAbs="+sl.getTotalAbsents()+" totCancel="+sl.getTotalCancelled());
        }
    }

    public void deleteSubject(String subjectName){
        long fid=getSubjectListID(subjectName);
        for(AttendenceDB adb:AttendenceDB.find(AttendenceDB.class,StringUtil.toSQLName("foreignID")+"=?",Long.toString(fid))){
            AttendenceDB aobj=AttendenceDB.findById(AttendenceDB.class,adb.getId());
            aobj.delete();
        }

        SubjectList sl= SubjectList.findById(SubjectList.class,getSubjectListID(subjectName));
        sl.delete();

        for(MondayTT_DB mtd:MondayTT_DB.listAll(MondayTT_DB.class)){
            if (mtd.subjectName.equals(subjectName))
                mtd.delete();
        }

        for(TuesdayTT_DB mtd:TuesdayTT_DB.listAll(TuesdayTT_DB.class)){
            if (mtd.subjectName.equals(subjectName))
                mtd.delete();
        }

        for(WednesdayTT_DB mtd:WednesdayTT_DB.listAll(WednesdayTT_DB.class)){
            if (mtd.subjectName.equals(subjectName))
                mtd.delete();
        }

        for(ThursdayTT_DB mtd:ThursdayTT_DB.listAll(ThursdayTT_DB.class)){
            if (mtd.subjectName.equals(subjectName))
                mtd.delete();
        }

        for(FridayTT_DB mtd:FridayTT_DB.listAll(FridayTT_DB.class)){
            if (mtd.subjectName.equals(subjectName))
                mtd.delete();
        }

        for (SaturdayTT_DB mtd:SaturdayTT_DB.listAll(SaturdayTT_DB.class)){
            if (mtd.subjectName.equals(subjectName))
                mtd.delete();
        }

        for(SundayTT_DB mtd:SundayTT_DB.listAll(SundayTT_DB.class)){
            if (mtd.subjectName.equals(subjectName))
                mtd.delete();
        }

        //add update attendence FUNCTION

    }

    public void showSubjectListDB_in_Log(){
        Log.d("abcde", "showSubjectListDB_in_Log: ");
        List<SubjectList> l=SubjectList.listAll(SubjectList.class);
        for (SubjectList sl:l) {
            Log.d("abcde", "showSubjectListDB_in_Log: "+sl.toString());
        }
    }

    public List<AttendenceDB> attendenceDB_List_DateDescOrderSort(List<AttendenceDB> list){
        Collections.sort(list,Collections.<AttendenceDB>reverseOrder());

       return list;
    }

    public void setThresholdAttendance(float threshold){
        Prefs.putFloat("ThresholdAttendance", threshold);
        Log.d("abcde", "setThresholdAttendance: Threshold Attendance Set");
    }

    public float getThresholdAttendance(){
        return Prefs.getFloat("ThresholdAttendance",75.00f);
    }

    public void updateSubjectAttendance(String subjectName,Date date,long AttendenceDB_id,String updatedState){
    AttendenceDB adb=AttendenceDB.findById(AttendenceDB.class,AttendenceDB_id);
    if(adb.getIsPresent()==1){
        long subjectListID =new Methods().getSubjectListID(subjectName);
        SubjectList sl=SubjectList.findById(SubjectList.class,subjectListID);
        sl.totalPresents-=1;
        sl.save();
    }
    if(adb.getIsAbsent()==1){
        long subjectListID =new Methods().getSubjectListID(subjectName);
        SubjectList sl=SubjectList.findById(SubjectList.class,subjectListID);
        sl.totalAbsents-=1;
        sl.save();
    }
    if(adb.getIsCancelled()==1){
        long subjectListID =new Methods().getSubjectListID(subjectName);
        SubjectList sl=SubjectList.findById(SubjectList.class,subjectListID);
        sl.totalCancelled-=1;
        sl.save();
    }

    adb.delete();

    if(updatedState.compareTo("present")==0)
    postPresent(subjectName,date);
    else if(updatedState.compareTo("absent")==0)
    postAbsent(subjectName,date);
    else if (updatedState.compareTo("cancelled")==0)
        postCancelled(subjectName,date);


    }

    public SubjectList getAllSubjectTotalsOfPresAbsCanc(){
        SubjectList cumilative=new SubjectList();
        int totalPres=0,totalAbs=0,totalCanc=0;
        for (SubjectList sl:SubjectList.listAll(SubjectList.class)){
            totalPres+=sl.totalPresents;
            totalAbs+=sl.totalAbsents;
            totalCanc+=sl.totalCancelled;
        }
        cumilative.setTotalPresents(totalPres);
        cumilative.setTotalAbsents(totalAbs);
        cumilative.setTotalCancelled(totalCanc);
        return cumilative;
    }

    public List getAttendanceDBListBetweenDateRange(Date initialDate,Date finalDate){
        List<AttendenceDB> list=AttendenceDB.listAll(AttendenceDB.class);
        List<AttendenceDB> selected=new ArrayList<>();

        for (AttendenceDB adb:list){
            Log.d("abcde", "getAttendanceDBListBetweenDateRange: "+adb.toString());

            if( DateUtils.isSameDay(initialDate,adb.date) || DateUtils.isSameDay(finalDate,adb.date) || (initialDate.before(adb.date) && finalDate.after(adb.date))){
                Log.d("abcde", "getAttendanceDBListBetweenDateRange: enter if");
                selected.add(adb);
            }

        }


//        for (AttendenceDB adb:selected){
//            Log.d("abcde", "getAttendanceDBListBetweenDateRange: "+adb.toString());
//        }

        return attendenceDB_List_DateDescOrderSort(selected);

    }

}
