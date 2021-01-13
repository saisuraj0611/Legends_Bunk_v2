package com.example.legendsbunkv2.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.legendsbunkv2.R;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;

import java.util.ArrayList;

public class PostTimeTableAdapter extends UltimateViewAdapter<PostTimeTableAdapter.PostTimeTableViewHolder> {

    public ArrayList<String> items;
    OnItemClickListener mlistener;


    public PostTimeTableAdapter(ArrayList<String> items) {
        this.items = items;
    }

    @Override
    public PostTimeTableViewHolder newFooterHolder(View view) {
        return null;
    }

    @Override
    public PostTimeTableViewHolder newHeaderHolder(View view) {
        return null;
    }

    @Override
    public PostTimeTableViewHolder onCreateViewHolder(ViewGroup parent) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.show_time_table_row,parent,false);
        PostTimeTableAdapter.PostTimeTableViewHolder viewHolder=new PostTimeTableAdapter.PostTimeTableViewHolder(v,mlistener);
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
    public void onBindViewHolder(@NonNull PostTimeTableViewHolder holder, int position) {
        String subName=items.get(position);
        holder.subjectNames.setText(subName);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
        void OnPresentClick(int position);
        void OnAbsentClick(int position);
        void OnCancelClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener=listener;
    }

    public class PostTimeTableViewHolder extends UltimateRecyclerviewViewHolder {

        public TextView subjectNames;
        public ImageView pres,abs,canc;

        public PostTimeTableViewHolder(View itemView,final OnItemClickListener listener) {
            super(itemView);
            subjectNames=itemView.findViewById(R.id.subjectNamePostTimeTable);
            pres=itemView.findViewById(R.id.presentButtonPostTimeTable);
            abs=itemView.findViewById(R.id.absentButtonPostTimeTable);
            canc=itemView.findViewById(R.id.cancelledButtonPostTimeTable);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnItemClick(position);
                    }
//
                }
            });

            pres.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnPresentClick(position);
                    }
//                    pres.setEnabled(false);
//                    abs.setEnabled(true);
//                    canc.setEnabled(true);
                }
            });

            abs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnAbsentClick(position);
                    }
//                    pres.setEnabled(true);
//                    abs.setEnabled(false);
//                    canc.setEnabled(true);
                }
            });

            canc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();

                        if (position != UltimateRecyclerView.NO_ID)
                            listener.OnCancelClick(position);
                    }
//                    pres.setEnabled(true);
//                    abs.setEnabled(true);
//                    canc.setEnabled(false);


                }
            });
        }
    }
}
