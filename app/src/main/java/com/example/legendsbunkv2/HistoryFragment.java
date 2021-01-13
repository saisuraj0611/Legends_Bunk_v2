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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.Intros.HistoryIntro;
import com.example.legendsbunkv2.Intros.TimeTableIntro;
import com.example.legendsbunkv2.model.AttendenceDB;
import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.util.AttendenceHistoryAdapter;
import com.example.legendsbunkv2.util.Methods;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.orm.StringUtil;
import com.pixplicity.easyprefs.library.Prefs;

import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class HistoryFragment extends Fragment {

    public TextView date;
    TextView date1;
    TextView date2;
    int mYear,mMonth, mDay;
    Date initialDate=null,finalDate=null;
    Button historyViewButton;
    UltimateRecyclerView ultimateRecyclerView;
    Context context;
    List<AttendenceDB> list = new ArrayList<>();
    AttendenceHistoryAdapter adapter;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    Button deleteAttendance,cancelDelete;

    View root;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_history,container,false);
        setHasOptionsMenu(true);
        if(Prefs.getString("HistoryIntro","enabled").equals("enabled") ){
            Intent intent=new Intent(this.getContext(), HistoryIntro.class);
            startActivityForResult(intent,1000);
        }

        historyViewButton=root.findViewById(R.id.historyViewButton);
        ultimateRecyclerView = root.findViewById(R.id.recyclerViewUltimate);
        context=this.getContext();



        date1=(TextView) root.findViewById(R.id.dateviewpast);
        date2=(TextView) root.findViewById(R.id.datetext2);

        String myFormat = "dd/MM/yy"; //Change as you need
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        if(initialDate==null) {
            List<AttendenceDB> adbList=AttendenceDB.listAll(AttendenceDB.class);
            Date earliest=Calendar.getInstance().getTime();
            for(AttendenceDB adb:adbList){
                if(adb.date.before(earliest))
                    earliest=adb.date;
            }
            initialDate=earliest;

            date1.setText(sdf.format(initialDate));
        }
        date2.setText(sdf.format(Calendar.getInstance().getTime()));
        finalDate=Calendar.getInstance().getTime();


        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate1 = Calendar.getInstance();
                mYear = mcurrentDate1.get(Calendar.YEAR);
                mMonth = mcurrentDate1.get(Calendar.MONTH);
                mDay = mcurrentDate1.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(getContext(),R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd/MM/yy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
                        date1.setText(sdf.format(myCalendar.getTime()));

                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;

                        initialDate = new GregorianCalendar(mYear, mMonth, mDay).getTime();

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Initial Date");
                mDatePicker.show();

            }
        });
        date2.setOnClickListener(new View.OnClickListener() {
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
                        date2.setText(sdf.format(myCalendar.getTime()));

                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                        finalDate = new GregorianCalendar(mYear, mMonth, mDay).getTime();

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select final date");
                mDatePicker.show();
            }
        });

        historyViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(finalDate==null)
                    initialDate=Calendar.getInstance().getTime();

                list=new Methods().getAttendanceDBListBetweenDateRange(initialDate,finalDate);
//                ultimateRecyclerView=root.findViewById(R.id.recyclerView);
                ultimateRecyclerView.setHasFixedSize(true);
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(context);
                adapter=new AttendenceHistoryAdapter((ArrayList<AttendenceDB>) list);
                ultimateRecyclerView.setLayoutManager(layoutManager);
                ultimateRecyclerView.setAdapter(adapter);
                setHasOptionsMenu(true);
                for(AttendenceDB adb:list){
                    Log.d("abcde", "onClick: recycler List "+adb.toString());
                }
                adapter.setOnItemClickListener(new AttendenceHistoryAdapter.OnItemClickListener() {
                    @Override
                    public void OnItemClick(final int position) {
                        builder=new AlertDialog.Builder(getContext());
                        View view=getLayoutInflater().inflate(R.layout.delete_history_dialogue_box,null);
                        deleteAttendance=view.findViewById(R.id.okButtonDeleteHistoryDialog);
                        cancelDelete=view.findViewById(R.id.cancelButtonDeleteHistoryDialog);

                        builder.setView(view);
                        dialog=builder.create();
                        dialog.show();

                        deleteAttendance.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("abcde", "onItemClick: removing "+position);
                                AttendenceDB adb=list.get(position);
                                list.remove(position);
                                AttendenceDB deleteItem=AttendenceDB.findById(AttendenceDB.class,adb.getId());
                                deleteItem.delete();
                                adapter.notifyItemRemoved(position);
                                String subjectName=new Methods().getSubjectListNameFromForeignID(adb.getForeignID());
                                List<SubjectList> sll=SubjectList.find(SubjectList.class, StringUtil.toSQLName("subject")+"=?",subjectName);
                                SubjectList sl=sll.get(0);
                                if (adb.getIsPresent()==1)
                                    sl.totalPresents-=1;
                                else if (adb.getIsAbsent()==1)
                                    sl.totalAbsents-=1;
                                else
                                    sl.totalCancelled-=1;
                                sl.save();
                                dialog.cancel();
                            }
                        });
                        cancelDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                    }
                });



            }
        });

        return root;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (menu != null){
            menu.findItem(R.id.addSubjectMain).setVisible(false);
            menu.findItem(R.id.deleteSubjectMain).setVisible(false);
            menu.findItem(R.id.helpHistory).setVisible(true);}
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.helpHistory)
        {
            Intent intent=new Intent(this.getContext(), TimeTableIntro.class);
            startActivityForResult(intent,1000);
        }
        return false;
    }
}
