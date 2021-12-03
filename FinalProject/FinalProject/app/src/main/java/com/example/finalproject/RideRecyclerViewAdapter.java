package com.example.finalproject;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class RideRecyclerViewAdapter extends RecyclerView.Adapter<RideRecyclerViewAdapter.ViewHolder> {
    String[] dates;
    String[] drivers;
    int[] durations;
    double[] costs;

    Context myContext;
    LayoutInflater myInflator;

    public RideRecyclerViewAdapter(String[] dates, String[] drivers, int[] durations,
                                   double[] costs, Context myContext) {
        this.dates = dates;
        this.drivers = drivers;
        this.durations = durations;
        this.costs = costs;

        this.myContext = myContext;
        this.myInflator = LayoutInflater.from(myContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = myInflator.from(parent.getContext()).inflate(R.layout.row_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dateTv.setText(dates[position]);
        holder.driverTv.setText(drivers[position]);
        holder.durationTv.setText(String.valueOf(durations[position]));
        holder.costTv.setText(String.valueOf(costs[position]));
    }

    @Override
    public int getItemCount() {
        return drivers.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTv;
        TextView driverTv;
        TextView durationTv;
        TextView costTv;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.dateTv);
            driverTv = itemView.findViewById(R.id.driverTv);
            durationTv = itemView.findViewById(R.id.durationTv);
            costTv = itemView.findViewById(R.id.costTv);

            parent = itemView.findViewById(R.id.rideParentLayout);
        }
    }
}