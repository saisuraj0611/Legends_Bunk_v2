package com.example.legendsbunkv2;

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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.Intros.HomeFragmentIntro;
import com.example.legendsbunkv2.Intros.LogPastIntro;
import com.example.legendsbunkv2.model.AttendenceDB;
import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.util.LogPastListAdapter;
import com.example.legendsbunkv2.util.Methods;
import com.example.legendsbunkv2.util.SubjectListAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.pixplicity.easyprefs.library.Prefs;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LogPastAttendanceFragment extends Fragment {
    TextView dateText,dayOfWeek;

    ImageButton prevbutton;
    ImageButton nextbutton;
    int mYear,tempYear;
    int mMonth,tempMonth;
    int mDay,tempDay;
    Date logPastDate;
    DatePickerDialog mDatePicker;
    View viewSnackbar;


    List<SubjectList> items;
    UltimateRecyclerView ultimateRecyclerView;
    //   UltimateViewAdapter adapter;
    LogPastListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Context context;



    View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_log_past_attendance,container,false);
        setHasOptionsMenu(true);



        if(Prefs.getString("LogPastFragmentIntro","enabled").equals("enabled") ){
            Intent intent=new Intent(this.getContext(), LogPastIntro.class);
            startActivityForResult(intent,1000);
        }


        View root = inflater.inflate(R.layout.fragment_log_past_attendance, container, false);
        dateText =(TextView) root.findViewById(R.id.dateviewpast);
        dayOfWeek=root.findViewById(R.id.dayOfWeek_LogPast);
        prevbutton=(ImageButton)root.findViewById(R.id.prevDateButton);
        nextbutton=(ImageButton) root.findViewById(R.id.nextDateButton);
        ultimateRecyclerView=root.findViewById(R.id.ulitmateRecyclerView_logPastAttenandance);
        context=this.getContext();
        viewSnackbar=root;
        logPastDate=Calendar.getInstance().getTime();


        String myFormat = "dd/MM/yy"; //Change as you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        dateText.setText(sdf.format(Calendar.getInstance().getTime()));

        DateTimeFormatter fmt = DateTimeFormat.forPattern("E");
        dayOfWeek.setText(fmt.print(new DateTime()));

        Calendar mcurrentDate = Calendar.getInstance();
        mYear = mcurrentDate.get(Calendar.YEAR);
        mMonth = mcurrentDate.get(Calendar.MONTH);
        mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        //setting date picker ahead to avoid force close if after opeing fragment and if we post attendance todays date should we working
        mDatePicker = new DatePickerDialog(getContext(),R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                Calendar myCalendar = Calendar.getInstance();
                myCalendar.set(Calendar.YEAR, selectedyear);
                myCalendar.set(Calendar.MONTH, selectedmonth);
                myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                String myFormat = "dd/MM/yy"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                dateText.setText(sdf.format(myCalendar.getTime()));




                mDay = selectedday;
                mMonth = selectedmonth;
                mYear = selectedyear;





            }
        }, mYear, mMonth, mDay);

        mDatePicker.setTitle("Select Date");
//        mDatePicker.show();
        Calendar calendar = Calendar.getInstance();
        calendar.set(mYear, mMonth, mDay);

        //Calling recycler view only once as even as date changes the list of subjects remains the same
        logPastRecyclerViewCreator();

        //above create date picker only shows when clicked on date Text View
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show();
            }
        });

        prevbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateTime dt=new DateTime(mYear,mMonth+1,mDay,0,0);
                dt=dt.minusDays(1);

                mYear=dt.getYear();
                mMonth=dt.getMonthOfYear()-1;
                mDay=dt.getDayOfMonth();

                mDatePicker.updateDate(dt.getYear(),dt.getMonthOfYear()-1,dt.getDayOfMonth());

                Date date=dt.toDate();
                String myFormat = "dd/MM/yy"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                dateText.setText(sdf.format(date));


                DateTimeFormatter fmt = DateTimeFormat.forPattern("E");
                dayOfWeek.setText(fmt.print(dt));

                logPastDate=date;


            }


        });

        nextbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DateTime dt=new DateTime(mYear,mMonth+1,mDay,0,0);
                dt=dt.plusDays(1);

                mYear=dt.getYear();
                mMonth=dt.getMonthOfYear()-1;
                mDay=dt.getDayOfMonth();

                mDatePicker.updateDate(dt.getYear(),dt.getMonthOfYear()-1,dt.getDayOfMonth());

                Date date=dt.toDate();
                String myFormat = "dd/MM/yy"; //Change as you need
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                dateText.setText(sdf.format(date));

                DateTimeFormatter fmt = DateTimeFormat.forPattern("E");
                dayOfWeek.setText(fmt.print(dt));

                logPastDate=date;



            }
        });







        return root;
    }

    private void logPastRecyclerViewCreator() {

        items=new ArrayList<SubjectList>();
        items=SubjectList.listAll(SubjectList.class);

        ultimateRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(context);
        adapter=new LogPastListAdapter((ArrayList<SubjectList>) items);
        ultimateRecyclerView.setLayoutManager(layoutManager);
        ultimateRecyclerView.setAdapter(adapter);
//        adapter.items.add(new SubjectList("hello sample"));
//        adapter.notifyDataSetChanged();

        adapter.setOnItemClickListener(new LogPastListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

            }

            @Override
            public void OnPresentClick(int position) {
//                      extract the clicked object from the recycler list with updated TotalPResents and sync with SubjectList DB
                SubjectList sl=SubjectList.findById(SubjectList.class,items.get(position).getId());
                sl.totalPresents+=1;
                sl.save();
//              add this present to the AttendenceDB with date
                long primaryID=new Methods().getSubjectListID(sl.getSubject());
//                        Calendar cal=Calendar.getInstance();
//                        cal.setTime(logPastDate);
                AttendenceDB adb=new AttendenceDB(1,0,0,primaryID, logPastDate);
                adb.save();
                Snackbar.make(viewSnackbar, sl.getSubject()+" marked Present", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void OnAbsentClick(int position) {
//                        extract the clicked object from the recycler list with updated TotalAbsents and sync with SubjectList DB
                SubjectList sl=SubjectList.findById(SubjectList.class,items.get(position).getId());
                sl.totalAbsents+=1;
                sl.save();
//              add this absent to the AttendenceDB with date
                long primaryID=new Methods().getSubjectListID(sl.subject);
                AttendenceDB adb=new AttendenceDB(0,1,0,primaryID,logPastDate);
                adb.save();

                Snackbar.make(viewSnackbar, sl.getSubject()+" marked Absent", Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void OnCancelClick(int position) {
//                extract the clicked object from the recycler list with updated TotalAbsents and sync with SubjectList DB
                SubjectList sl=SubjectList.findById(SubjectList.class,items.get(position).getId());
                sl.totalCancelled+=1;
                sl.save();
//              add this Cancelled to the AttendenceDB with date
                long primaryID=new Methods().getSubjectListID(sl.subject);
                AttendenceDB adb=new AttendenceDB(0,0,1,primaryID,logPastDate);
                adb.save();
                Snackbar.make(viewSnackbar, sl.getSubject()+" marked Cancelled", Snackbar.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (menu != null){
            menu.findItem(R.id.addSubjectMain).setVisible(false);
            menu.findItem(R.id.deleteSubjectMain).setVisible(false);
            menu.findItem(R.id.helpLogPast).setVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.helpLogPast:
                Intent _intent = new Intent(this.getContext(), LogPastIntro.class);
                startActivityForResult(_intent, 1000);
        }
        return false;
    }
}
