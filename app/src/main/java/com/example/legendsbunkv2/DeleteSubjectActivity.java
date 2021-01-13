package com.example.legendsbunkv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.util.DeleteSubjectAdapter;
import com.example.legendsbunkv2.util.Methods;
import com.example.legendsbunkv2.util.SubjectListAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

public class DeleteSubjectActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    UltimateRecyclerView deleteSubjectRecyclerView;
    DeleteSubjectAdapter adapter;
    Button delete;
    List<SubjectList> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();
// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,android.R.color.black));
//setting status bar color android

        setContentView(R.layout.activity_delete_subject);
        delete=findViewById(R.id.deleteCheckedSubjects);

        items=SubjectList.listAll(SubjectList.class);
        for(SubjectList sl:items){
            sl.totalCancelled=-1;   //as we r using the total cancelled field to check wheather checkbox in recycle row is clicked or not i am initiali
//                                          it to a some no. other than 0 or 1
        }


        deleteSubjectRecyclerView=findViewById(R.id.deleteSubjectUltimate);
        deleteSubjectRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(DeleteSubjectActivity.this);
        adapter=new DeleteSubjectAdapter((ArrayList<SubjectList>) items);
        deleteSubjectRecyclerView.setLayoutManager(layoutManager);
        deleteSubjectRecyclerView.setAdapter(adapter);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<SubjectList> deleteList=adapter.items;
                for(SubjectList sl:deleteList){
                    if(sl.totalCancelled==1){
                        new Methods().deleteSubject(sl.subject);
                    }
                }
                setResult(DeleteSubjectActivity.RESULT_OK);
                finish();

            }
        });
    }
}