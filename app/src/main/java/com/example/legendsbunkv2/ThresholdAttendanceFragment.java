package com.example.legendsbunkv2;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.legendsbunkv2.R;
import com.example.legendsbunkv2.util.Methods;
import com.pixplicity.easyprefs.library.Prefs;

public class ThresholdAttendanceFragment extends Fragment {

    View root;

    EditText threshold;
    Button setThreshold;
    Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.fragment_threshold_attendance,container,false);
        threshold=root.findViewById(R.id.thresholdAttendance);
        setThreshold=root.findViewById(R.id.setThresholdAttedanceButton);
        setHasOptionsMenu(true);
        context=this.getContext();
        threshold.setText(Float.toString(new Methods().getThresholdAttendance()));
        setThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=threshold.getText().toString().trim();
                if (str.isEmpty())
                    str="75";
                else{
                    float n=Float.valueOf(str);
                    if(n>0f && n<100f){
                        new Methods().setThresholdAttendance(Float.valueOf(str));
                        Toast.makeText(context,"Successfully set threshold attendance to "+str+"%",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(context,"Value must be >1 and <100 ",Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });


        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (menu != null){
            menu.findItem(R.id.addSubjectMain).setVisible(false);
            menu.findItem(R.id.deleteSubjectMain).setVisible(false);
        }
    }
}
