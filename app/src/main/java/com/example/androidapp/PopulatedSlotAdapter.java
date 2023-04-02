package com.example.androidapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib.Countable;
import com.example.lib.PopulatedSlot;

import java.util.ArrayList;

public class PopulatedSlotAdapter extends RecyclerView.Adapter<PopulatedSlotAdapter.SlotsHolder> {
    LayoutInflater mInflater;
    Context context;
    ArrayList<Countable> populatedSlotArrayList;
    public PopulatedSlotAdapter( Context context, ArrayList<Countable> populatedSlotArrayList) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.populatedSlotArrayList = populatedSlotArrayList;
    }






    @NonNull
    @Override
    public SlotsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        // Inflate the layout

        View photoView
                = inflater
                .inflate(R.layout.populated_slot_card,
                        parent, false);

        SlotsHolder holder
                = new SlotsHolder(photoView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SlotsHolder holder, int position) {
        final int index = holder.getAdapterPosition();
        holder.getTimingTV()
                .setText("Timing : "+this.populatedSlotArrayList.get(position).timeSlot());
        holder.getCountTV()
                .setText("Count : "+this.populatedSlotArrayList.get(position).count());

    }

    @Override
    public int getItemCount() {
        return this.populatedSlotArrayList.size();
    }

    class SlotsHolder extends RecyclerView.ViewHolder{

        private TextView timeSlotTV;

        public TextView getTimingTV() {
            return timeSlotTV;
        }

        public TextView getCountTV() {
            return countTV;
        }

        private TextView countTV;
        public SlotsHolder(@NonNull View itemView) {
            super(itemView);
            timeSlotTV = itemView.findViewById(R.id.timeSlotTV);
            countTV=itemView.findViewById(R.id.countTV);

        }


    }

}
