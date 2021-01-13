package com.example.legendsbunkv2;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.Intros.HistoryIntro;
import com.example.legendsbunkv2.Intros.TimeTableIntro;
import com.example.legendsbunkv2.Intros.VacationIntro;
import com.example.legendsbunkv2.model.FridayTT_DB;
import com.example.legendsbunkv2.model.MondayTT_DB;
import com.example.legendsbunkv2.model.SaturdayTT_DB;
import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.model.SundayTT_DB;
import com.example.legendsbunkv2.model.ThursdayTT_DB;
import com.example.legendsbunkv2.model.TuesdayTT_DB;
import com.example.legendsbunkv2.model.WednesdayTT_DB;
import com.example.legendsbunkv2.util.Methods;
import com.example.legendsbunkv2.util.VacationAdapter;
import com.example.legendsbunkv2.util.VacationAdditionalAdapter;
import com.example.legendsbunkv2.util.VacationHolidayAdapter;
import com.google.gson.Gson;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.pixplicity.easyprefs.library.Prefs;

import net.danlew.android.joda.JodaTimeAndroid;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class FutureAttendanceFragment extends Fragment {
    View root;

    TextView departure,arrival,status;
    Button dateSetButton;

    int mYear,mMonth,mDay;
    Date departureDate,arrivalDate;
    boolean isSelected[];

    UltimateRecyclerView vacationIndividualSubjectRecyclerView;
    VacationAdapter adapter;
    List<SubjectList> subjectLists;

    UltimateRecyclerView vacationAdditionalRecyclerView;
    VacationAdditionalAdapter adapterAdditional;
    List<SubjectList> additionalSubjectLists;

    UltimateRecyclerView vacationHolidaysRecyclerView;
    VacationHolidayAdapter holidayAdapter;
    List<String> holidayRecycleViewInput;
    RecyclerView.LayoutManager layoutManager;


    AlertDialog.Builder alertBuilderForHolidaysSelection;
    AlertDialog alertDialogForHolidaySelection;
    Button nextButtonToGoTOAdditionClassesSelector;

    AlertDialog.Builder alertBuilderForAdditinalClassesSelection;
    AlertDialog alertDialogForAdditinalClassesSelection;
    Button estimateFutureAttendanceResult;


    Context context;

    boolean isTimeTableSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_future_attendance,container,false);
        setHasOptionsMenu(true);
        JodaTimeAndroid.init(this.getContext());

        if(Prefs.getString("VacationIntro","enabled").equals("enabled") ){
            Intent intent=new Intent(this.getContext(), VacationIntro.class);
            startActivityForResult(intent,1000);
        }

        departure=root.findViewById(R.id.departureDate);
        arrival=root.findViewById(R.id.arrivalDate);
        status=root.findViewById(R.id.vacationStatus);
        vacationIndividualSubjectRecyclerView=root.findViewById(R.id.recyclerViewUltimateVacation);
        vacationAdditionalRecyclerView=root.findViewById(R.id.recyclerViewUltimateVacationAdditionalClasses);
        context=this.getContext();
        dateSetButton=root.findViewById(R.id.futureAttendanceDateSelectButton);
        nextButtonToGoTOAdditionClassesSelector=root.findViewById(R.id.afterHolidaySelectedFutureAttendanceButton);
//        vacationHolidaysRecyclerView=root.findViewById(R.id.vacationHolidaysRecyclerView);




        String myFormat = "dd/MM/yy"; //Change as you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        departure.setText("Choose Date");
        arrival.setText("Choose Date");
        departureDate=null;
        arrivalDate=null;

        departureDateSetter();
        arrivalDateSetter();

        dateSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrivalDate!=null && departureDate!=null){
                    openDialogForHolidaysSelection();

                }
                else{
                    Log.d("wxyz", "onClick: error with arrival and departure dates in future fragment1");
                }
            }
        });



        isTimeTableSet=false;

       isTimeTableSetChecker();






//        estimate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(departureDate.compareTo(arrivalDate)>0 || departureDate==null || arrivalDate==null)
//                    Toast.makeText(context,"Make sure the Dates set are greater than today & also the Departure date is earlier than Arrival date",Toast.LENGTH_LONG).show();
//                else{
//                    Log.d("abcde", "onClick: "+departureDate.compareTo(arrivalDate));
//                    List<SubjectList> originalSL=SubjectList.listAll(SubjectList.class);
//                    subjectLists=SubjectList.listAll(SubjectList.class);
//                    subjectLists.clear();
//                    additionalSubjectLists=adapterAdditional.items;
//                    int i=0;
//                    for(SubjectList sl:originalSL){
//                        subjectLists.add(getSubjectListItemForRecyclerView(sl));
//                        if(additionalSubjectLists.get(i).totalPresents>0) {
//                            Log.d("abcde", "onClick: greater than 0");
//                            subjectLists.get(i).totalAbsents-=additionalSubjectLists.get(i).totalPresents;
//                        }
//                        else if (additionalSubjectLists.get(i).totalPresents<0) {
//                            Log.d("abcde", "onClick: less than 0");
//                            subjectLists.get(i).totalAbsents-=additionalSubjectLists.get(i).totalPresents;
//                        }
//                        Log.d("ijkl", "onClick: "+additionalSubjectLists.get(i).getSubject()+" tp="+additionalSubjectLists.get(i).totalPresents);
//                        i++;
//                    }
//
//                    parsingResultListAndStartResultActivity();
//
//
//
//
//
//                }
//            }
//        });

        return root;
    }

    private void openDialogForHolidaysSelection() {
        alertBuilderForHolidaysSelection=new AlertDialog.Builder(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        View view=getLayoutInflater().inflate(R.layout.fragment_future_attendance_2_holiday_selector,null);
        alertBuilderForHolidaysSelection.setView(view);
        alertDialogForHolidaySelection=alertBuilderForHolidaysSelection.create();
        alertDialogForHolidaySelection.show();
        holidayListDisplay(view);

        nextButtonToGoTOAdditionClassesSelector=view.findViewById(R.id.afterHolidaySelectedFutureAttendanceButton);
        nextButtonToGoTOAdditionClassesSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSelected=holidayAdapter.isSelected;

                alertBuilderForAdditinalClassesSelection=new AlertDialog.Builder(getContext(),android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                View view2=getLayoutInflater().inflate(R.layout.fragment_future_attendance_3_additional_classes_selector,null);
                alertBuilderForAdditinalClassesSelection.setView(view2);
                alertDialogForAdditinalClassesSelection=alertBuilderForAdditinalClassesSelection.create();
                alertDialogForAdditinalClassesSelection.show();

                callVacationAdditionalClassesRecycleList(view2);

                estimateFutureAttendanceResult=view2.findViewById(R.id.calculateVacation);
                estimateFutureAttendanceResult.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        List<SubjectList> originalSL=SubjectList.listAll(SubjectList.class);
                    subjectLists=SubjectList.listAll(SubjectList.class);
                    subjectLists.clear();
                    additionalSubjectLists=adapterAdditional.items;
                    int i=0;
                    for(SubjectList sl:originalSL){
                        subjectLists.add(getSubjectListItemForRecyclerView(sl));
                        if(additionalSubjectLists.get(i).totalPresents>0) {
                            Log.d("abcde", "onClick: greater than 0");
                            subjectLists.get(i).totalAbsents-=additionalSubjectLists.get(i).totalPresents;
                        }
                        else if (additionalSubjectLists.get(i).totalPresents<0) {
                            Log.d("abcde", "onClick: less than 0");
                            subjectLists.get(i).totalAbsents-=additionalSubjectLists.get(i).totalPresents;
                        }
                        Log.d("ijkl", "onClick: "+additionalSubjectLists.get(i).getSubject()+" tp="+additionalSubjectLists.get(i).totalPresents);
                        i++;
                    }

                    parsingResultListAndStartResultActivity();
                    }
                });

            }
        });
    }

    private void parsingResultListAndStartResultActivity() {
        SubjectList cumilative=new SubjectList("cumilative");
        for(SubjectList sl:subjectLists){
            cumilative.totalPresents+=sl.totalPresents;
            cumilative.totalAbsents+=sl.totalAbsents;
        }
        Gson gson=new Gson();
        String json=gson.toJson(subjectLists);
        Prefs.putString("LIST",json);
        Prefs.putString("Vacation Status for resultActivity",getBunkStatus(cumilative));



        Intent intent=new Intent(context,FutureAttendanceResult.class);
        startActivity(intent);
    }

    private void arrivalDateSetter() {
        arrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(),R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);


                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                        arrivalDate = new GregorianCalendar(mYear, mMonth, mDay).getTime();
                        arrival.setText(sdf.format(myCalendar.getTime()));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select final date");
                mDatePicker.show();
            }
        });
    }

    private void departureDateSetter() {
        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate1 = Calendar.getInstance();
                mYear = mcurrentDate1.get(Calendar.YEAR);
                mMonth = mcurrentDate1.get(Calendar.MONTH);
                mDay = mcurrentDate1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(),R.style.DatePickerTheme,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);


                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;

                        departureDate = new GregorianCalendar(mYear, mMonth, mDay).getTime();
                        departure.setText(sdf.format(myCalendar.getTime()));
                        Log.d("abcde", "onDateSet: fuck");
//                        holidayListDisplay();
//                        callVacationAdditionalClassesRecycleList();
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Initial Date");
                mDatePicker.show();


            }
        });
    }

    public void callVacationAdditionalClassesRecycleList(View v){
        additionalSubjectLists=SubjectList.listAll(SubjectList.class);
        for(SubjectList sl:additionalSubjectLists)
            sl.totalPresents=0;
        vacationAdditionalRecyclerView=v.findViewById(R.id.recyclerViewUltimateVacationAdditionalClasses);
        vacationAdditionalRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
        adapterAdditional=new VacationAdditionalAdapter((ArrayList<SubjectList>) additionalSubjectLists,setMaxClassesThatCanBeCancelledForEachSubjectDuringVacation());
        vacationAdditionalRecyclerView.setLayoutManager(layoutManager);
        vacationAdditionalRecyclerView.setAdapter(adapterAdditional);
    }

    private void holidayListDisplay(View v) {
        Log.d("abcde", "holidayListDisplay: entered");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd, MMMM, YYYY, EEEE");
        Date initialDate=departureDate,finalDate=arrivalDate;
        holidayRecycleViewInput =new ArrayList<>();

        int differenceBetweenInitialFInal=0;
        while(initialDate.compareTo(finalDate)<=0 || DateUtils.isSameDay(initialDate,finalDate) ){
            holidayRecycleViewInput.add(sdf2.format(initialDate));
            initialDate=DateUtils.addDays(initialDate,1);
            differenceBetweenInitialFInal++;
            Log.d("abcde", "holidayListDisplay: "+initialDate.compareTo(finalDate));
        }
        isSelected=new boolean[differenceBetweenInitialFInal];

        vacationHolidaysRecyclerView=v.findViewById(R.id.vacationHolidaysRecyclerView);
        Log.d("wxyz", "holidayListDisplay: holiday recycler view object="+vacationHolidaysRecyclerView);
        vacationHolidaysRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(context);
        holidayAdapter=new VacationHolidayAdapter((ArrayList<String>) holidayRecycleViewInput);
        vacationHolidaysRecyclerView.setLayoutManager(layoutManager);
        vacationHolidaysRecyclerView.setAdapter(holidayAdapter);

    }

    private List<SubjectList> setMaxClassesThatCanBeCancelledForEachSubjectDuringVacation() {
        //will be using total cancelled field to store that data;
        Log.d("abcde", ": setMaxClassesThatCanBeCancelledForEachSubjectDuringVacation entered");
        isSelected=holidayAdapter.isSelected;
        List<SubjectList> maxClassesCancelledList=SubjectList.listAll(SubjectList.class);
        Date initialDate,finalDate;
        initialDate=departureDate;
        finalDate=arrivalDate;
        for(SubjectList sl:maxClassesCancelledList)
            sl.totalCancelled=0;

        int counterForBooleanHolidaySelectorArray=0;
        for (SubjectList sl : maxClassesCancelledList) {
        while(initialDate.compareTo(finalDate)<=0 || DateUtils.isSameDay(initialDate,finalDate)) {

            if(isSelected[counterForBooleanHolidaySelectorArray]==false){   //means holiday is set as false in boolean array
                DateTimeZone dtz=DateTimeZone.forTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                DateTimeZone.setDefault(dtz);
//            DateTime dt=new DateTime(DateUtils.addDays(initialDate,1));
                DateTime dt=new DateTime(initialDate);
                DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE, d, MMMM, yyyy");
                Log.d("abcde", ": setMaxClassesThatCanBeCancelledForEachSubjectDuringVacation"+fmt.print(dt));
                switch (dt.getDayOfWeek()){
                    case 1:
                        for (MondayTT_DB mtd : MondayTT_DB.listAll(MondayTT_DB.class)) {
                    if (mtd.subjectName.equals(sl.subject))
                        sl.totalCancelled++;
                }
                        break;
                    case 2:
                        for (TuesdayTT_DB mtd : TuesdayTT_DB.listAll(TuesdayTT_DB.class)) {
                            if (mtd.subjectName.equals(sl.subject))
                                sl.totalCancelled++;
                        }
                        break;
                    case 3:
                        for (WednesdayTT_DB mtd : WednesdayTT_DB.listAll(WednesdayTT_DB.class)) {
                            if (mtd.subjectName.equals(sl.subject))
                                sl.totalCancelled++;
                        }
                        break;
                    case 4:
                        for (ThursdayTT_DB mtd : ThursdayTT_DB.listAll(ThursdayTT_DB.class)) {
                            if (mtd.subjectName.equals(sl.subject))
                                sl.totalCancelled++;
                        }
                        break;
                    case 5:
                        for (FridayTT_DB mtd : FridayTT_DB.listAll(FridayTT_DB.class)) {
                            if (mtd.subjectName.equals(sl.subject))
                                sl.totalCancelled++;
                        }
                        break;
                    case 6:
                        for (SaturdayTT_DB mtd : SaturdayTT_DB.listAll(SaturdayTT_DB.class)) {
                            if (mtd.subjectName.equals(sl.subject))
                                sl.totalCancelled++;
                        }
                        break;
                    case 7:
                        for (SundayTT_DB mtd : SundayTT_DB.listAll(SundayTT_DB.class)) {
                            if (mtd.subjectName.equals(sl.subject))
                                sl.totalCancelled++;
                        }
                        break;

                }
            }
            initialDate=DateUtils.addDays(initialDate,1);
            counterForBooleanHolidaySelectorArray++;
            }
            Log.d("abcde", "setMaxClassesThatCanBeCancelledForEachSubjectDuringVacation: maxclasses of ->"+sl.toString());
        }

        return maxClassesCancelledList;
    }

    private void isTimeTableSetChecker() {
        List<MondayTT_DB> monday=MondayTT_DB.listAll(MondayTT_DB.class);
        if (monday.size()>0) {
            Log.d("abcde", "onCreateView: timetable isEnabled="+monday.isEmpty());
            isTimeTableSet=true;
        }
        List<TuesdayTT_DB> tuesday=TuesdayTT_DB.listAll(TuesdayTT_DB.class);
        if (tuesday.size()>0)
            isTimeTableSet=true;
        List<WednesdayTT_DB> wednesday=WednesdayTT_DB.listAll(WednesdayTT_DB.class);
        if (wednesday.size()>0)
            isTimeTableSet=true;
        List<ThursdayTT_DB> thursday=ThursdayTT_DB.listAll(ThursdayTT_DB.class);
        if (thursday.size()>0)
            isTimeTableSet=true;
        List<FridayTT_DB> friday=FridayTT_DB.listAll(FridayTT_DB.class);
        if (friday.size()>0)
            isTimeTableSet=true;
        List<SaturdayTT_DB> saturday=FridayTT_DB.listAll(SaturdayTT_DB.class);
        if (saturday.size()>0)
            isTimeTableSet=true;
        List<SundayTT_DB> sunday=SundayTT_DB.listAll(SundayTT_DB.class);
        if (sunday.size()>0)
            isTimeTableSet=true;

    }

    private SubjectList getSubjectListItemForRecyclerView(SubjectList sl) {
        int totalPresent=sl.getTotalPresents();
        int totalAbsents=sl.getTotalAbsents();
        float totalPercentage;
        Date initialDate,finalDate;
        initialDate=departureDate;
        finalDate=arrivalDate;
        Log.d("efghi", "getSubjectListItemForRecyclerView: "+initialDate.toString()+"  "+finalDate.toString());

        int counterForBooleanHolidaySelectorArray=0;
        while(initialDate.compareTo(finalDate)<=0 || initialDate.equals(finalDate)){
            if(isSelected[counterForBooleanHolidaySelectorArray]==false){   //means holiday is set as false in boolean array
                    DateTimeZone dtz=DateTimeZone.forTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
                    DateTimeZone.setDefault(dtz);
//            DateTime dt=new DateTime(DateUtils.addDays(initialDate,1));
                    DateTime dt=new DateTime(initialDate);
                    DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE, d, MMMM, yyyy");
                    Log.d("efghi", "getSubjectListItemForRecyclerView: "+fmt.print(dt));
                    switch (dt.getDayOfWeek()){
                    case 1:
                        for(MondayTT_DB mtdb:MondayTT_DB.listAll(MondayTT_DB.class)){
                            if(mtdb.subjectName.equals(sl.subject)){
                                sl.totalAbsents+=1;
                                Log.d("efghi", "getSubjectListItemForRecyclerView: "+sl.subject+" hit once on Mon");
                            }
                        }
                        break;
                    case 2:
                        for(TuesdayTT_DB mtdb:TuesdayTT_DB.listAll(TuesdayTT_DB.class)) {
                            if (mtdb.subjectName.equals(sl.subject)) {
                                sl.totalAbsents += 1;
                                Log.d("efghi", "getSubjectListItemForRecyclerView: "+sl.subject+" hit once on Tue");
                            }
                        }
                        break;
                    case 3:
                        for(WednesdayTT_DB mtdb:WednesdayTT_DB.listAll(WednesdayTT_DB.class)) {
                            if (mtdb.subjectName.equals(sl.subject)) {
                                sl.totalAbsents += 1;
                                Log.d("efghi", "getSubjectListItemForRecyclerView: "+sl.subject+" hit once on Wed");
                            }
                        }
                        break;
                    case 4:
                        for(ThursdayTT_DB mtdb: ThursdayTT_DB.listAll(ThursdayTT_DB.class)) {
                            if (mtdb.subjectName.equals(sl.subject)) {
                                sl.totalAbsents += 1;
                                Log.d("efghi", "getSubjectListItemForRecyclerView: "+sl.subject+" hit once on Thu");
                            }
                        }
                        break;
                    case 5:
                        for(FridayTT_DB mtdb:FridayTT_DB.listAll(FridayTT_DB.class)) {
                            if (mtdb.subjectName.equals(sl.subject)) {
                                sl.totalAbsents += 1;
                                Log.d("efghi", "getSubjectListItemForRecyclerView: "+sl.subject+" hit once on Fri");
                            }
                        }
                        break;
                    case 6:
                        for(SaturdayTT_DB mtdb:SaturdayTT_DB.listAll(SaturdayTT_DB.class)) {
                            if (mtdb.subjectName.equals(sl.subject)) {
                                sl.totalAbsents += 1;
                                Log.d("efghi", "getSubjectListItemForRecyclerView: "+sl.subject+" hit once on Sat");
                            }
                        }
                        break;
                    case 7:
                        for (SundayTT_DB mtdb : SundayTT_DB.listAll(SundayTT_DB.class)) {
                            if (mtdb.subjectName.equals(sl.subject)) {
                                sl.totalAbsents += 1;
                                Log.d("efghi", "getSubjectListItemForRecyclerView: "+sl.subject+" hit once on Sun");
                            }
                        }
                        break;

                }




            }
            initialDate=DateUtils.addDays(initialDate,1);
            counterForBooleanHolidaySelectorArray++;
        }

        Log.d("abcde", "getSubjectListItemForRecyclerView: name="+sl.subject+" pres="+sl.totalPresents+" abs="+sl.totalAbsents);
        return sl;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (menu != null){
            menu.findItem(R.id.addSubjectMain).setVisible(false);
            menu.findItem(R.id.deleteSubjectMain).setVisible(false);
            menu.findItem(R.id.helpVacation).setVisible(true);}
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.helpVacation)
        {
            Intent intent=new Intent(this.getContext(), VacationIntro.class);
            startActivityForResult(intent,1000);
        }
        return false;
    }

    private String getBunkStatus(SubjectList sl) {
        float thisSubjectAttendencePerc=0;
        if(sl.totalPresents+sl.totalAbsents ==0){
            thisSubjectAttendencePerc=100.0f;
        }
        else
            thisSubjectAttendencePerc=( (float)sl.getTotalPresents() / ( (float)(sl.totalPresents+sl.totalAbsents)))*100.0f;

        if(thisSubjectAttendencePerc >= new Methods().getThresholdAttendance()){
            int bunks=0;
            while((( (float)(sl.getTotalPresents()) / ( (float)(sl.totalPresents+sl.totalAbsents+bunks)))*100.0f) >= new Methods().getThresholdAttendance()){
                bunks++;
            }
            bunks--;

            if(bunks<=0){
                return("You cant Bunk any Classes");
            }
            else{
                return("Next "+bunks+" classes can be bunked");
            }
        }
        else{
            int mustAttend=0;
            while((( (float)(sl.getTotalPresents()+mustAttend) / ( (float)(sl.totalPresents+sl.totalAbsents+mustAttend)))*100.0f) < new Methods().getThresholdAttendance()){
                mustAttend++;
            }


            if(mustAttend<=0){
                return("Error");
            }
            else{
                return("Next "+mustAttend+" classes must be attended");
            }
        }
    }
}
