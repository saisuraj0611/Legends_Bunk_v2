package com.example.legendsbunkv2.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.R;
import com.example.legendsbunkv2.model.SubjectList;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VacationAdditionalAdapter extends UltimateViewAdapter<VacationAdditionalAdapter.VacationAdditionalHolder> {
    public ArrayList<SubjectList> items;
//    public OnItemClickListener mlistener;
    SubjectList sl;
    Date departureDate,arrivalDate;
    public List<SubjectList> maxClassesCancelledList;

    public VacationAdditionalAdapter(ArrayList<SubjectList> items, List<SubjectList> maxClassesCancelledList) {
        this.items = items;
        this.maxClassesCancelledList = maxClassesCancelledList;
    }

    public ArrayList<SubjectList> getItems() {
        return items;
    }

    public VacationAdditionalAdapter(ArrayList<SubjectList> items) {
        this.items = items;
    }

    public VacationAdditionalAdapter(ArrayList<SubjectList> items, Date departureDate, Date arrivalDate) {
        this.items = items;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
    }
    //    public void setOnItemClickListener(OnItemClickListener listener){
//        mlistener=listener;
//    }

    @Override
    public VacationAdditionalHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public VacationAdditionalHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public VacationAdditionalHolder onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.vacation_additional_classes_recycler_row,parent,false);
        VacationAdditionalHolder viewHolder=new VacationAdditionalHolder(v);
        return viewHolder;
    }

    @Override
    public int getAdapterItemCount() {
        return items.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull final VacationAdditionalHolder holder, final int position) {
        //here SubjectList sl=items.get(position) and then using sl inside onClick dosent work so always call items.get(position)
        holder.subjectName.setText(items.get(position).subject);
        holder.count.setText(Integer.toString(items.get(position).totalPresents));
        holder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(position).totalPresents = items.get(position).totalPresents + 1;
                holder.count.setText(String.valueOf(items.get(position).totalPresents));
                //undoing the above change if the claasses cancelled value exceeds max
                if(items.get(position).totalPresents>maxClassesCancelledList.get(position).totalCancelled){
                    items.get(position).totalPresents = items.get(position).totalPresents - 1;
                    holder.count.setText(String.valueOf(items.get(position).totalPresents));
                }

            }
        });

        holder.decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.get(position).totalPresents = items.get(position).totalPresents - 1;
                holder.count.setText(String.valueOf(items.get(position).totalPresents));
            }
        });

        holder.count.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("abcde", "onTextChanged: detected");
                String text=holder.count.getText().toString();

                if(!text.isEmpty() && !text.equals("-")){
                    int input=Integer.valueOf(text);
                    if (input>maxClassesCancelledList.get(position).totalCancelled) {
                        //add toast saying you cant exceed the classes cancelled than those in the timetable period of vacation
                        holder.count.setText(Integer.toString(maxClassesCancelledList.get(position).totalCancelled));
                        items.get(position).totalCancelled=maxClassesCancelledList.get(position).totalCancelled;
                    }
                    else {
                        items.get(position).totalCancelled=Integer.parseInt(text);
                    }

                    Log.d("abcde", "onTextChanged: from edit text"+String.valueOf(items.get(position).totalPresents));
                }
            }



            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

//    public interface OnItemClickListener{
//        void OnItemClick(int position);
//        void OnIncrementClick(int position);
//        void OnDecrementClick(int position);
//    }

    public class VacationAdditionalHolder extends UltimateRecyclerviewViewHolder{
        public Button increment,decrement;
        public TextView count,subjectName;
        public VacationAdditionalHolder(View itemView) {
            super(itemView);
            increment=itemView.findViewById(R.id.increaseButtonVacation);
            decrement=itemView.findViewById(R.id.decreaseButtonVacation);
            count=itemView.findViewById(R.id.additionalClassesEditTextVacationRow);
            subjectName=itemView.findViewById(R.id.subjectNameAdditionalClasses);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//
//                        if (position != UltimateRecyclerView.NO_ID)
//                            listener.OnItemClick(position);
//                    }
//                }
//            });
//            increment.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//
//                        if (position != UltimateRecyclerView.NO_ID)
//                            listener.OnIncrementClick(position);
//                    }
//                }
//            });
//
//            decrement.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//
//                        if (position != UltimateRecyclerView.NO_ID)
//                            listener.OnDecrementClick(position);
//                    }
//                }
//            });
        }
    }
}
