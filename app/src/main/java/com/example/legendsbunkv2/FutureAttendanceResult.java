package com.example.legendsbunkv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.util.VacationAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FutureAttendanceResult extends AppCompatActivity {
    UltimateRecyclerView vacationIndividualSubjectRecyclerView;
    VacationAdapter adapter;
    List<SubjectList> subjectLists;

    TextView status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_attendance_result);
        status=findViewById(R.id.vacationStatus);


//        Intent intent=getIntent();
//        Bundle args=intent.getBundleExtra("BUNDLE");
//        List<SubjectList> subjectLists= (ArrayList<SubjectList>) args.getSerializable("LIST");
        String json= Prefs.getString("LIST",null);
        Gson gson=new Gson();
        Type type = new TypeToken<List<SubjectList>>() {
        }.getType();
        subjectLists=gson.fromJson(json,type);
        status.setText(Prefs.getString("Vacation Status for resultActivity","Error"));


        vacationIndividualSubjectRecyclerView=findViewById(R.id.recyclerViewUltimateVacation);
        vacationIndividualSubjectRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this);
        adapter=new VacationAdapter((ArrayList<SubjectList>) subjectLists);
        vacationIndividualSubjectRecyclerView.setLayoutManager(layoutManager);
        vacationIndividualSubjectRecyclerView.setAdapter(adapter);




    }

}