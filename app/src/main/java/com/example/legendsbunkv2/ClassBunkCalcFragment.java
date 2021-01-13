package com.example.legendsbunkv2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.DeleteSubjectActivity;
import com.example.legendsbunkv2.HomeFragment;
import com.example.legendsbunkv2.Intros.ClassBunkIntro;
import com.example.legendsbunkv2.Intros.HistoryIntro;
import com.example.legendsbunkv2.Intros.TimeTableIntro;
import com.example.legendsbunkv2.model.AttendenceDB;
import com.example.legendsbunkv2.model.MondayTT_DB;
import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.util.Methods;
import com.example.legendsbunkv2.util.SubjectListAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import org.apache.commons.math3.util.Precision;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.legendsbunkv2.R;
import com.pixplicity.easyprefs.library.Prefs;

public class ClassBunkCalcFragment extends Fragment {

    View root;

    List<SubjectList> items;
    UltimateRecyclerView ultimateRecyclerView;
    //   UltimateViewAdapter adapter;
    SubjectListAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    TextView totalStudentPercentage,totalStudentPresent,totalStudentAbsents,totalStudentCancelled,totalStudentBunkStatus;
    EditText subjectName;
    Context context;
    Intent refresh;

    DecimalFormat decimalFormat;

    AlertDialog.Builder builder;
    AlertDialog dialog;
    Button addSubject;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_home,container,false);
        setHasOptionsMenu(true);
        if(Prefs.getString("ClassBunkIntro","enabled").equals("enabled") ){
            Intent intent=new Intent(this.getContext(), ClassBunkIntro.class);
            startActivityForResult(intent,1000);
        }


        totalStudentPercentage=root.findViewById(R.id.totalStudentPerc);
        totalStudentPresent=root.findViewById(R.id.totalStudentPresents);
        totalStudentAbsents=root.findViewById(R.id.totalStudentAbsents);
        totalStudentCancelled=root.findViewById(R.id.totalStudentCancelled);
        totalStudentBunkStatus=root.findViewById(R.id.totalStudentBunkStatus);
        context=this.getContext();
        refresh=new Intent(this.getContext(), HomeFragment.class);
        decimalFormat=new DecimalFormat("00.00 ");




        items = new ArrayList<>();
        items=SubjectList.listAll(SubjectList.class);
        ultimateRecyclerView=root.findViewById(R.id.recyclerViewUltimate);
        ultimateRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getContext());
        adapter=new SubjectListAdapter((ArrayList<SubjectList>) items);
        ultimateRecyclerView.setLayoutManager(layoutManager);
        ultimateRecyclerView.setAdapter(adapter);
//        checkTimeTable();


        final SubjectList cumilative1=new Methods().getAllSubjectTotalsOfPresAbsCanc();
        float totalPercentage=( (float)cumilative1.getTotalPresents()/ (float)(cumilative1.getTotalPresents()+cumilative1.getTotalAbsents())*100.0f);
        totalStudentPercentage.setText(decimalFormat.format(totalPercentage)+"%");
        totalStudentPresent.setText(Integer.toString(cumilative1.totalPresents));
        totalStudentAbsents.setText(Integer.toString(cumilative1.totalAbsents));
        totalStudentCancelled.setText(Integer.toString(cumilative1.totalCancelled));
        totalStudentBunkStatus.setText(getTotalBunkStatus(cumilative1));

        adapter.setOnItemClickListener(new SubjectListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

                adapter.notifyItemChanged(position);
            }


            @Override
            public void OnPresentClick(int position) {
//                update the totalPresents of clicked item of recycler view list and notify the adapter
                items.get(position).setTotalPresents(items.get(position).getTotalPresents()+1);
//                adapter.notifyItemChanged(position);
                adapter.notifyDataSetChanged();
//                extract the clicked object from the recycler list with updated TotalPResents and sync with SubjectList DB
//                SubjectList sl=SubjectList.findById(SubjectList.class,items.get(position).getId());
//                sl.totalPresents+=1;
//                sl.save();
//              add this present to the AttendenceDB with date
//                long primaryID=new Methods().getSubjectListID(sl.getSubject());
//                AttendenceDB adb=new AttendenceDB(1,0,0,primaryID, Calendar.getInstance().getTime());
//                adb.save();

//              using my Methods to retrieve sum of all pres abs and cancelled and calc totalStudentPercentage and round it and display above recyclerView


               SubjectList cumilitive=new SubjectList("cumilative");
               for(SubjectList sl:adapter.items){
                   cumilitive.totalPresents+=sl.totalPresents;
                   cumilitive.totalAbsents+=sl.totalAbsents;
               }

//                SubjectList cumilative=new Methods().getAllSubjectTotalsOfPresAbsCanc();
                float totalPercentage=( (float)cumilitive.getTotalPresents()/ (float)(cumilitive.getTotalPresents()+cumilitive.getTotalAbsents())*100.0f);
                totalStudentPercentage.setText(decimalFormat.format(totalPercentage)+"%");
                totalStudentPresent.setText(Integer.toString(cumilitive.totalPresents));
                totalStudentAbsents.setText(Integer.toString(cumilitive.totalAbsents));
                totalStudentCancelled.setText(Integer.toString(cumilitive.totalCancelled));
                totalStudentBunkStatus.setText(getTotalBunkStatus(cumilitive));


                new Methods().showSubjectListDB_in_Log();
            }


            public String getTotalBunkStatus(SubjectList cumilative) {
                float totalAttendence=0;
                if(cumilative.totalPresents+cumilative.totalAbsents ==0){
                    totalAttendence=100.0f;
                }
                else
                    totalAttendence=( (float)cumilative.getTotalPresents() / ( (float)(cumilative.totalPresents+cumilative.totalAbsents)))*100.0f;

                if(totalAttendence >= new Methods().getThresholdAttendance()){
                    int bunks=0;
                    while((( (float)(cumilative.getTotalPresents()) / ( (float)(cumilative.totalPresents+cumilative.totalAbsents+bunks)))*100.0f) >= new Methods().getThresholdAttendance()){
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
                    while((( (float)(cumilative.getTotalPresents()+mustAttend) / ( (float)(cumilative.totalPresents+cumilative.totalAbsents+mustAttend)))*100.0f) < new Methods().getThresholdAttendance()){
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

            @Override
            public void OnAbsentClick(int position) {
//                update the totalAbsents of clicked item of recycler view list and notify the adapter
                items.get(position).setTotalAbsents(items.get(position).getTotalAbsents()+1);
                adapter.notifyItemChanged(position);
//                extract the clicked object from the recycler list with updated TotalAbsents and sync with SubjectList DB
//                SubjectList sl=SubjectList.findById(SubjectList.class,items.get(position).getId());
//                sl.totalAbsents+=1;
//                sl.save();
//              add this absent to the AttendenceDB with date
//                long primaryID=new Methods().getSubjectListID(sl.subject);
//                AttendenceDB adb=new AttendenceDB(0,1,0,primaryID,Calendar.getInstance().getTime());
//                adb.save();

//                using my Methods to retrieve sum of all pres abs and cancelled and calc totalStudentPercentage and display above recyclerView
//
                 SubjectList cumilitive=new SubjectList("cumilative");
               for(SubjectList sl:adapter.items){
                   cumilitive.totalPresents+=sl.totalPresents;
                   cumilitive.totalAbsents+=sl.totalAbsents;
               }
//                SubjectList cumilative=new Methods().getAllSubjectTotalsOfPresAbsCanc();

                float totalPercentage=( (float)cumilitive.getTotalPresents()/ (float)(cumilitive.getTotalPresents()+cumilitive.getTotalAbsents())*100.0f);
                totalStudentPercentage.setText(decimalFormat.format(totalPercentage)+"%");
                totalStudentPresent.setText(Integer.toString(cumilitive.totalPresents));
                totalStudentAbsents.setText(Integer.toString(cumilitive.totalAbsents));
                totalStudentCancelled.setText(Integer.toString(cumilitive.totalCancelled));
                totalStudentBunkStatus.setText(getTotalBunkStatus(cumilitive));

                new Methods().showSubjectListDB_in_Log();
            }

            @Override
            public void OnCancelClick(int position) {
//                update the totalCancelled of clicked item of recycler view list and notify the adapter
                items.get(position).setTotalCancelled(items.get(position).getTotalCancelled()+1);
                adapter.notifyItemChanged(position);
//                extract the clicked object from the recycler list with updated TotalAbsents and sync with SubjectList DB
//                SubjectList sl=SubjectList.findById(SubjectList.class,items.get(position).getId());
//                sl.totalCancelled+=1;
//                sl.save();
//              add this Cancelled to the AttendenceDB with date
//                long primaryID=new Methods().getSubjectListID(sl.subject);
//                AttendenceDB adb=new AttendenceDB(0,0,1,primaryID,Calendar.getInstance().getTime());
//                adb.save();

//                using my Methods to retrieve sum of all pres abs and cancelled and calc totalStudentPercentage and display above recyclerView
//
                 SubjectList cumilitive=new SubjectList("cumilative");
               for(SubjectList sl:adapter.items){
                   cumilitive.totalPresents+=sl.totalPresents;
                   cumilitive.totalAbsents+=sl.totalAbsents;
               }
//
//                SubjectList cumilative=new Methods().getAllSubjectTotalsOfPresAbsCanc();
                float totalPercentage=( (float)cumilitive.getTotalPresents()/ (float)(cumilitive.getTotalPresents()+cumilitive.getTotalAbsents())*100.0f);
                totalStudentPercentage.setText(decimalFormat.format(totalPercentage)+"%");
                totalStudentPresent.setText(Integer.toString(cumilitive.totalPresents));
                totalStudentAbsents.setText(Integer.toString(cumilitive.totalAbsents));
                totalStudentCancelled.setText(Integer.toString(cumilitive.totalCancelled));
                totalStudentBunkStatus.setText(getTotalBunkStatus(cumilitive));

                new Methods().showSubjectListDB_in_Log();
            }
        });


        return root;

    }
    private void checkTimeTable() {
        List<String> mondayTimeTableList=new ArrayList<>();
        items.clear();
        for(MondayTT_DB mtd:MondayTT_DB.listAll(MondayTT_DB.class)){
//            mondayTimeTableList.add(mtd.subjectName);
            items.add(SubjectList.findById(SubjectList.class,new Methods().getSubjectListID(mtd.subjectName)));
        }

        adapter.notifyDataSetChanged();



    }

    private String getTotalBunkStatus(SubjectList cumilative) {
        float totalAttendence=0;
        if(cumilative.totalPresents+cumilative.totalAbsents ==0){
            totalAttendence=100.0f;
        }
        else
            totalAttendence=( (float)cumilative.getTotalPresents() / ( (float)(cumilative.totalPresents+cumilative.totalAbsents)))*100.0f;

        if(totalAttendence >= new Methods().getThresholdAttendance()){
            int bunks=0;
            while((( (float)(cumilative.getTotalPresents()) / ( (float)(cumilative.totalPresents+cumilative.totalAbsents+bunks)))*100.0f) >= new Methods().getThresholdAttendance()){
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
            while((( (float)(cumilative.getTotalPresents()+mustAttend) / ( (float)(cumilative.totalPresents+cumilative.totalAbsents+mustAttend)))*100.0f) < new Methods().getThresholdAttendance()){
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 10001) && (resultCode == DeleteSubjectActivity.RESULT_OK))
        // recreate your fragment here
        {
            Log.d("abcde", "onActivityResult: ");
            getFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        }




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
            Intent intent=new Intent(this.getContext(), ClassBunkIntro.class);
            startActivityForResult(intent,1000);
        }
        return false;
    }

    private void openDialog() {
        builder=new AlertDialog.Builder(getContext());
        View view=getLayoutInflater().inflate(R.layout.layout_dialog,null);
        subjectName=view.findViewById(R.id.addSubjectName);
        addSubject=view.findViewById(R.id.addSubjectnamebutton);

        builder.setView(view);
        dialog=builder.create();
        dialog.show();

        addSubject.setEnabled(false);
        subjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!subjectName.getText().toString().trim().isEmpty())
                    addSubject.setEnabled(true);
                else
                    addSubject.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                add new subject to SubjectList DB
                new Methods().addSubject(subjectName.getText().toString());
//                create a new SubjectList() object to add to recyclerview items list and assign DB id to the list object
                SubjectList sl_to_add=new SubjectList(subjectName.getText().toString());
                sl_to_add.setId(new Methods().getSubjectListID(subjectName.getText().toString()));
//                add the new SubjectList() item to the recycler list
                items.add(items.size(),sl_to_add);
                new Methods().showSubjectListDB_in_Log();
//                notify the adapter of change
                adapter.notifyItemInserted(items.size()-1);
                ultimateRecyclerView.scrollVerticallyToPosition(items.size()-1);

                dialog.cancel();
            }
        });



    }
}
