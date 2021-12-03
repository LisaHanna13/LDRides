package com.example.finalproject;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public class RideRecyclerViewAdapter extends RecyclerView.Adapter<RideRecyclerViewAdapter.ViewHolder> {
    String[] rides;
    String[] dates;
    String[] drivers;
    int[] durations;
    double[] costs;
    int currentRideId;

    ClickListener listener;
    Context myContext;
    LayoutInflater myInflator;

    public RideRecyclerViewAdapter(String[] rides, String[] dates, String[] drivers, int[] durations,
                                   double[] costs, Context myContext, ClickListener listener) {
        this.dates = dates;
        this.rides = rides;
        this.drivers = drivers;
        this.durations = durations;
        this.costs = costs;

        this.listener = listener;
        this.myContext = myContext;
        this.myInflator = LayoutInflater.from(myContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = myInflator.from(parent.getContext()).inflate(R.layout.row_item, parent,false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentRideId = Integer.valueOf(rides[position]);
        holder.dateTv.setText(dates[position]);
        holder.driverTv.setText(drivers[position]);
        holder.durationTv.setText(String.valueOf(durations[position]));
        holder.costTv.setText(String.format("%.2f", costs[position]) + "$");
    }

    @Override
    public int getItemCount() {
        return drivers.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dateTv;
        TextView driverTv;
        TextView durationTv;
        TextView costTv;
        Button viewConfirmationB;
        LinearLayout parent;

        WeakReference<ClickListener> listenerRef;

        public ViewHolder(final View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            dateTv = itemView.findViewById(R.id.dateTv);
            driverTv = itemView.findViewById(R.id.driverTv);
            durationTv = itemView.findViewById(R.id.durationTv);
            costTv = itemView.findViewById(R.id.costTv);
            viewConfirmationB = itemView.findViewById(R.id.viewConfirmationB);

            parent = itemView.findViewById(R.id.rideParentLayout);
            viewConfirmationB.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listenerRef.get().onPositionClicked(currentRideId);
        }
    }
}