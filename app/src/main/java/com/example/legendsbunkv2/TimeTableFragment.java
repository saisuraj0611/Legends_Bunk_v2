package com.example.legendsbunkv2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.legendsbunkv2.Intros.LogPastIntro;
import com.example.legendsbunkv2.Intros.TimeTableIntro;
import com.example.legendsbunkv2.model.FridayTT_DB;
import com.example.legendsbunkv2.model.MondayTT_DB;
import com.example.legendsbunkv2.model.SaturdayTT_DB;
import com.example.legendsbunkv2.model.SubjectList;
import com.example.legendsbunkv2.model.SundayTT_DB;
import com.example.legendsbunkv2.model.ThursdayTT_DB;
import com.example.legendsbunkv2.model.TuesdayTT_DB;
import com.example.legendsbunkv2.model.WednesdayTT_DB;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

public class TimeTableFragment extends Fragment {
    protected View root;
    protected Context context;
    protected ArrayList allSubjectList;

    protected Spinner mondaySpinner;
    protected ListView mondayListView;
    protected ArrayList<String> mondayTT;
    protected ArrayAdapter<String> mondayListAdapter;

    protected Spinner tuesdaySpinner;
    protected ListView tuesdayListView;
    protected ArrayList<String> tuesdayTT;
    protected ArrayAdapter<String> tuesdayListAdapter;

    protected Spinner wednesdaySpinner;
    protected ListView wednesdayListView;
    protected ArrayList<String> wednesdayTT;
    protected ArrayAdapter<String> wednesdayListAdapter;

    protected Spinner thursdaySpinner;
    protected ListView thursdayListView;
    protected ArrayList<String> thursdayTT;
    protected ArrayAdapter<String> thursdayListAdapter;

    protected Spinner fridaySpinner;
    protected ListView fridayListView;
    protected ArrayList<String> fridayTT;
    protected ArrayAdapter<String> fridayListAdapter;

    protected Spinner saturdaySpinner;
    protected ListView saturdayListView;
    protected ArrayList<String> saturdayTT;
    protected ArrayAdapter<String> saturdayListAdapter;

    protected Spinner sundaySpinner;
    protected ListView sundayListView;
    protected ArrayList<String> sundayTT;
    protected ArrayAdapter<String> sundayListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_time_table,container,false);
        setHasOptionsMenu(true);
        context=this.getContext();

        if(Prefs.getString("TimeTableIntro","enabled").equals("enabled") ){
            Intent intent=new Intent(this.getContext(), TimeTableIntro.class);
            startActivityForResult(intent,1000);
        }


        allSubjectList=new ArrayList<String>();
        allSubjectList.add("Select Subject");

        for(SubjectList sl:SubjectList.listAll(SubjectList.class))
            allSubjectList.add(sl.subject);

        mondayUI();
        tuesdayUI();
        wednesdayUI();
        thursdayUI();
        fridayUI();
        saturdayUI();
        sundayUI();

        timetableDB_LOG();




        return root;
    }

    private void timetableDB_LOG() {
        for(MondayTT_DB mondayTT_db:MondayTT_DB.listAll(MondayTT_DB.class))
            Log.d("abcde", "onCreateView: monday  "+mondayTT_db.subjectName);
        for(TuesdayTT_DB tuesdayTT_db:TuesdayTT_DB.listAll(TuesdayTT_DB.class))
            Log.d("abcde", "onCreateView: tuesday  "+tuesdayTT_db.subjectName);
        for(WednesdayTT_DB wednesdayTT_db:WednesdayTT_DB.listAll(WednesdayTT_DB.class))
            Log.d("abcde", "onCreateView: wednesd  "+wednesdayTT_db.subjectName);
        for(ThursdayTT_DB thursdayTT_db:ThursdayTT_DB.listAll(ThursdayTT_DB.class))
            Log.d("abcde", "onCreateView: thursda  "+thursdayTT_db.subjectName);
        for(FridayTT_DB fridayTT_db:FridayTT_DB.listAll(FridayTT_DB.class))
            Log.d("abcde", "onCreateView: friday  "+fridayTT_db.subjectName);
        for(SaturdayTT_DB saturdayTT_db:SaturdayTT_DB.listAll(SaturdayTT_DB.class))
            Log.d("abcde", "onCreateView: Saturda  "+saturdayTT_db.subjectName);
        for(SundayTT_DB sundayTT_db:SaturdayTT_DB.listAll(SundayTT_DB.class))
            Log.d("abcde", "onCreateView: Sunday  "+sundayTT_db.subjectName);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void sundayUI() {
        sundaySpinner=root.findViewById(R.id.sundaySpinner);
        sundayListView=root.findViewById(R.id.sundayList);
        ArrayAdapter<String> sundaySpinnerAdapter=new ArrayAdapter<String>(context,R.layout.spinner_dropdown_custom,(allSubjectList)){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        sundaySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        sundaySpinner.setAdapter(sundaySpinnerAdapter);

        sundayTT=new ArrayList<>();

        for(SundayTT_DB mdb: SundayTT_DB.listAll(SundayTT_DB.class)){
            sundayTT.add(mdb.subjectName);
        }
        sundayListAdapter=new ArrayAdapter<String>(context,R.layout.simple_list_item_custom,sundayTT);
        sundayListView.setAdapter(sundayListAdapter);
        sundayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<SundayTT_DB> mdb=SundayTT_DB.listAll(SundayTT_DB.class);
                long dbID=mdb.get(position).getId();
                SundayTT_DB toDelete=SundayTT_DB.findById(SundayTT_DB.class, dbID);
                toDelete.delete();

                sundayTT.remove(position);
                sundayListAdapter.notifyDataSetChanged();
            }
        });

        fridayListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        sundaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    String selected=parent.getItemAtPosition(position).toString();
                    sundayTT.add(selected);
                    sundayListAdapter.notifyDataSetChanged();
                    SundayTT_DB mdb=new SundayTT_DB(selected);
                    mdb.save();

                    sundaySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void saturdayUI() {
        saturdaySpinner=root.findViewById(R.id.saturdaySpinner);
        saturdayListView=root.findViewById(R.id.saturdayList);
        ArrayAdapter<String> saturdaySpinnerAdapter=new ArrayAdapter<String>(context,R.layout.simple_list_item_custom,(allSubjectList)){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        saturdaySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        saturdaySpinner.setAdapter(saturdaySpinnerAdapter);

        saturdayTT=new ArrayList<>();

        for(SaturdayTT_DB mdb: SaturdayTT_DB.listAll(SaturdayTT_DB.class)){
            saturdayTT.add(mdb.subjectName);
        }
        saturdayListAdapter=new ArrayAdapter<String>(context,R.layout.simple_list_item_custom,saturdayTT);
        saturdayListView.setAdapter(saturdayListAdapter);
        saturdayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<SaturdayTT_DB> mdb=SaturdayTT_DB.listAll(SaturdayTT_DB.class);
                long dbID=mdb.get(position).getId();
                SaturdayTT_DB toDelete=SaturdayTT_DB.findById(SaturdayTT_DB.class, dbID);
                toDelete.delete();

                saturdayTT.remove(position);
                saturdayListAdapter.notifyDataSetChanged();
            }
        });

        saturdayListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        saturdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    String selected=parent.getItemAtPosition(position).toString();
                    saturdayTT.add(selected);
                    saturdayListAdapter.notifyDataSetChanged();
                    SaturdayTT_DB mdb=new SaturdayTT_DB(selected);
                    mdb.save();

                    saturdaySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fridayUI() {
        fridaySpinner=root.findViewById(R.id.fridaySpinner);
        fridayListView=root.findViewById(R.id.fridayList);
        ArrayAdapter<String> fridaySpinnerAdapter=new ArrayAdapter<String>(context,R.layout.spinner_dropdown_custom,(allSubjectList)){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        fridaySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        fridaySpinner.setAdapter(fridaySpinnerAdapter);

        fridayTT=new ArrayList<>();

        for(FridayTT_DB mdb: FridayTT_DB.listAll(FridayTT_DB.class)){
            fridayTT.add(mdb.subjectName);
        }
        fridayListAdapter=new ArrayAdapter<String>(context,R.layout.simple_list_item_custom,fridayTT);
        fridayListView.setAdapter(fridayListAdapter);
        fridayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<FridayTT_DB> mdb=FridayTT_DB.listAll(FridayTT_DB.class);
                long dbID=mdb.get(position).getId();
                FridayTT_DB toDelete=FridayTT_DB.findById(FridayTT_DB.class, dbID);
                toDelete.delete();

                fridayTT.remove(position);
                fridayListAdapter.notifyDataSetChanged();
            }
        });

        fridayListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        fridaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    String selected=parent.getItemAtPosition(position).toString();
                    fridayTT.add(selected);
                    fridayListAdapter.notifyDataSetChanged();
                    FridayTT_DB mdb=new FridayTT_DB(selected);
                    mdb.save();

                    fridaySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void thursdayUI() {
        thursdaySpinner=root.findViewById(R.id.thursdaySpinner);
        thursdayListView=root.findViewById(R.id.thursdayList);
        ArrayAdapter<String> thursdaySpinnerAdapter=new ArrayAdapter<String>(context,R.layout.spinner_dropdown_custom,(allSubjectList)){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        thursdaySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        thursdaySpinner.setAdapter(thursdaySpinnerAdapter);

        thursdayTT=new ArrayList<>();

        for(ThursdayTT_DB mdb: ThursdayTT_DB.listAll(ThursdayTT_DB.class)){
            thursdayTT.add(mdb.subjectName);
        }
        thursdayListAdapter=new ArrayAdapter<String>(context,R.layout.simple_list_item_custom,thursdayTT);
        thursdayListView.setAdapter(thursdayListAdapter);
        thursdayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<ThursdayTT_DB> mdb=ThursdayTT_DB.listAll(ThursdayTT_DB.class);
                long dbID=mdb.get(position).getId();
                ThursdayTT_DB toDelete=ThursdayTT_DB.findById(ThursdayTT_DB.class, dbID);
                toDelete.delete();

                thursdayTT.remove(position);
                thursdayListAdapter.notifyDataSetChanged();
            }
        });

        thursdayListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        thursdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    String selected=parent.getItemAtPosition(position).toString();
                    thursdayTT.add(selected);
                    thursdayListAdapter.notifyDataSetChanged();
                    ThursdayTT_DB mdb=new ThursdayTT_DB(selected);
                    mdb.save();

                    thursdaySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void wednesdayUI() {
        wednesdaySpinner=root.findViewById(R.id.wednesdaySpinner);
        wednesdayListView=root.findViewById(R.id.wednesdayList);
        ArrayAdapter<String> wednesdaySpinnerAdapter=new ArrayAdapter<String>(this.getContext(),R.layout.spinner_dropdown_custom,(allSubjectList)){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        wednesdaySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        wednesdaySpinner.setAdapter(wednesdaySpinnerAdapter);

        wednesdayTT=new ArrayList<>();

        for(WednesdayTT_DB mdb: WednesdayTT_DB.listAll(WednesdayTT_DB.class)){
            wednesdayTT.add(mdb.subjectName);
        }
        wednesdayListAdapter=new ArrayAdapter<String>(context,R.layout.simple_list_item_custom,wednesdayTT);
        wednesdayListView.setAdapter(wednesdayListAdapter);
        wednesdayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<WednesdayTT_DB> mdb=WednesdayTT_DB.listAll(WednesdayTT_DB.class);
                long dbID=mdb.get(position).getId();
                WednesdayTT_DB toDelete=WednesdayTT_DB.findById(WednesdayTT_DB.class, dbID);
                toDelete.delete();

                wednesdayTT.remove(position);
                wednesdayListAdapter.notifyDataSetChanged();
            }
        });

        wednesdayListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        wednesdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    String selected=parent.getItemAtPosition(position).toString();
                    wednesdayTT.add(selected);
                    wednesdayListAdapter.notifyDataSetChanged();
                    WednesdayTT_DB mdb=new WednesdayTT_DB(selected);
                    mdb.save();

                    wednesdaySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @SuppressLint("ClickableViewAccessibility")
    private void tuesdayUI() {
        tuesdaySpinner=root.findViewById(R.id.tuesdaySpinner);
        tuesdayListView=root.findViewById(R.id.tuesdayList);
        ArrayAdapter<String> tuesdaySpinnerAdapter=new ArrayAdapter<String>(context,R.layout.spinner_dropdown_custom,(allSubjectList)){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        tuesdaySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        tuesdaySpinner.setAdapter(tuesdaySpinnerAdapter);

        tuesdayTT=new ArrayList<>();

        for(TuesdayTT_DB mdb: TuesdayTT_DB.listAll(TuesdayTT_DB.class)){
            tuesdayTT.add(mdb.subjectName);
        }
        tuesdayListAdapter=new ArrayAdapter<String>(context,R.layout.simple_list_item_custom,tuesdayTT);
        tuesdayListView.setAdapter(tuesdayListAdapter);
        tuesdayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<TuesdayTT_DB> mdb=TuesdayTT_DB.listAll(TuesdayTT_DB.class);
                long dbID=mdb.get(position).getId();
                TuesdayTT_DB toDelete=TuesdayTT_DB.findById(TuesdayTT_DB.class, dbID);
                toDelete.delete();

                tuesdayTT.remove(position);
                tuesdayListAdapter.notifyDataSetChanged();
            }
        });

        tuesdaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    String selected=parent.getItemAtPosition(position).toString();
                    tuesdayTT.add(selected);
                    tuesdayListAdapter.notifyDataSetChanged();

                    TuesdayTT_DB mdb=new TuesdayTT_DB(selected);
                    mdb.save();

                    tuesdaySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tuesdayListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }


    });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void mondayUI() {
        mondaySpinner=root.findViewById(R.id.mondaySpinner);
        mondayListView=root.findViewById(R.id.mondayList);
        ArrayAdapter<String> mondaySpinnerAdapter=new ArrayAdapter<String>(this.getContext(),R.layout.spinner_dropdown_custom,(allSubjectList)){
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };
        mondaySpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_custom);
        mondaySpinner.setAdapter(mondaySpinnerAdapter);

        mondayTT=new ArrayList<>();

        for(MondayTT_DB mdb: MondayTT_DB.listAll(MondayTT_DB.class)){
            mondayTT.add(mdb.subjectName);
        }
        mondayListAdapter=new ArrayAdapter<String>(this.getContext(),R.layout.simple_list_item_custom,mondayTT);
        mondayListView.setAdapter(mondayListAdapter);
        mondayListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<MondayTT_DB> mdb=MondayTT_DB.listAll(MondayTT_DB.class);
                long dbID=mdb.get(position).getId();
                MondayTT_DB toDelete=MondayTT_DB.findById(MondayTT_DB.class, dbID);
                toDelete.delete();

                mondayTT.remove(position);
                mondayListAdapter.notifyDataSetChanged();
            }
        });

        mondayListView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });

        mondaySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    String selected=parent.getItemAtPosition(position).toString();
                    mondayTT.add(selected);
                    mondayListAdapter.notifyDataSetChanged();

                    MondayTT_DB mdb=new MondayTT_DB(selected);
                    mdb.save();

                    mondaySpinner.setSelection(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (menu != null){
            menu.findItem(R.id.addSubjectMain).setVisible(false);
            menu.findItem(R.id.deleteSubjectMain).setVisible(false);
        menu.findItem(R.id.helpTimeTable).setVisible(true);}
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.helpTimeTable)
        {
            Intent intent=new Intent(this.getContext(), TimeTableIntro.class);
            startActivityForResult(intent,1000);
        }
        return false;
    }
}
