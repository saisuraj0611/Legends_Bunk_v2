package com.example.legendsbunkv2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.Intros.PostTimeTableIntro;
import com.example.legendsbunkv2.Intros.TimeTableIntro;
import com.example.legendsbunkv2.model.AttendenceDB;
import com.example.legendsbunkv2.model.FridayTT_DB;
import com.example.legendsbunkv2.model.MondayTT_DB;
import com.example.legendsbunkv2.model.SaturdayTT_DB;
import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.model.SundayTT_DB;
import com.example.legendsbunkv2.model.ThursdayTT_DB;
import com.example.legendsbunkv2.model.TuesdayTT_DB;
import com.example.legendsbunkv2.model.WednesdayTT_DB;
import com.example.legendsbunkv2.util.Methods;
import com.example.legendsbunkv2.util.PostTimeTableAdapter;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.pixplicity.easyprefs.library.Prefs;

import net.danlew.android.joda.JodaTimeAndroid;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class PostTimeTableFragment extends Fragment {
    View root;

    TextView dayOfWeek;

    List<String> items;
    UltimateRecyclerView ultimateRecyclerView;
    //   UltimateViewAdapter adapter;
    PostTimeTableAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_post_time_table,container,false);
        setHasOptionsMenu(true);

        if(Prefs.getString("PostTimeTableIntro","enabled").equals("enabled") ){
            Intent intent=new Intent(this.getContext(), PostTimeTableIntro.class);
            startActivityForResult(intent,1000);
        }

        JodaTimeAndroid.init(this.getContext());
        dayOfWeek=root.findViewById(R.id.dayOfWeekPostTimeTable);

        items = new ArrayList<>();


        setRecyclerItemsBasedOnWeekDay();

        ultimateRecyclerView=root.findViewById(R.id.recyclerViewUltimateTT);
        ultimateRecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this.getContext());
        adapter=new PostTimeTableAdapter((ArrayList<String>) items);
        ultimateRecyclerView.setLayoutManager(layoutManager);
        ultimateRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new PostTimeTableAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {

            }

            @Override
            public void OnPresentClick(int position) {

                String subName=items.get(position);

                long slID=new Methods().getSubjectListID(subName);
                SubjectList sl=SubjectList.findById(SubjectList.class,slID);
                sl.totalPresents+=1;
                sl.save();

                long fID=sl.getId();
                AttendenceDB adb=new AttendenceDB(1,0,0,fID,Calendar.getInstance().getTime());
                adb.save();

            }

            @Override
            public void OnAbsentClick(int position) {

                String subName=items.get(position);

                long slID=new Methods().getSubjectListID(subName);
                SubjectList sl=SubjectList.findById(SubjectList.class,slID);
                sl.totalAbsents+=1;
                sl.save();

                long fID=sl.getId();
                AttendenceDB adb=new AttendenceDB(0,1,0,fID,Calendar.getInstance().getTime());
                adb.save();
            }

            @Override
            public void OnCancelClick(int position) {
                String subName=items.get(position);

                long slID=new Methods().getSubjectListID(subName);
                SubjectList sl=SubjectList.findById(SubjectList.class,slID);
                sl.totalCancelled+=1;
                sl.save();

                long fID=sl.getId();
                AttendenceDB adb=new AttendenceDB(0,0,1,fID,Calendar.getInstance().getTime());
                Log.d("abcde", "OnCancelClick: adbid "+adb.getId());
                adb.save();
            }
        });





        return root;
    }

    private void setRecyclerItemsBasedOnWeekDay() {
//        Date date = Calendar.getInstance().getTime();
//        DateTime dt=new DateTime(DateUtils.addDays(date,1));
        DateTime dt=new DateTime();
        DateTimeZone dtz=DateTimeZone.forTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        DateTimeZone.setDefault(dtz);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("EEEE, d, MMMM, yyyy, Z");
        Log.d("efghi", "setRecyclerItemsBasedOnWeekDay: TIMETABLE DAY "+fmt.print(dt));
        switch (dt.getDayOfWeek()){
            case 1:
                for(MondayTT_DB mtdb:MondayTT_DB.listAll(MondayTT_DB.class))
                items.add(mtdb.subjectName);
                dayOfWeek.setText("Monday"+"'s Time Table");
                break;
            case 2:
                for(TuesdayTT_DB mtdb:TuesdayTT_DB.listAll(TuesdayTT_DB.class))
                    items.add(mtdb.subjectName);
                dayOfWeek.setText("Tuesday"+"'s Time Table");
                break;
            case 3:
                for(WednesdayTT_DB mtdb:WednesdayTT_DB.listAll(WednesdayTT_DB.class))
                    items.add(mtdb.subjectName);
                dayOfWeek.setText("Wednesday"+"'s Time Table");
                break;
            case 4:
                for(ThursdayTT_DB mtdb: ThursdayTT_DB.listAll(ThursdayTT_DB.class))
                    items.add(mtdb.subjectName);
                dayOfWeek.setText("Thursday"+"'s Time Table");
                break;
            case 5:
                for(FridayTT_DB mtdb:FridayTT_DB.listAll(FridayTT_DB.class))
                    items.add(mtdb.subjectName);
                dayOfWeek.setText("Friday"+"'s Time Table");
                break;
            case 6:
                for(SaturdayTT_DB mtdb:SaturdayTT_DB.listAll(SaturdayTT_DB.class))
                    items.add(mtdb.subjectName);
                dayOfWeek.setText("Saturday"+"'s Time Table");
                break;
            case 7:
                for(SundayTT_DB mtdb:SundayTT_DB.listAll(SundayTT_DB.class))
                    items.add(mtdb.subjectName);
                dayOfWeek.setText("Sunday"+"'s Time Table");
                break;

        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (menu != null){
            menu.findItem(R.id.addSubjectMain).setVisible(false);
            menu.findItem(R.id.deleteSubjectMain).setVisible(false);
            menu.findItem(R.id.helpPostTimeTable).setVisible(true);}
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.helpPostTimeTable)
        {
            Intent intent=new Intent(this.getContext(), PostTimeTableIntro.class);
            startActivityForResult(intent,1000);
        }
        return false;
    }
}
