package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;

public class HomeDriverRecyclerViewAdapter extends RecyclerView.Adapter<HomeDriverRecyclerViewAdapter.ViewHolder> {
    int[] rides;
    String[] users;
    String[] pickups;
    String[] destinations;
    int[] durations;
    double[] costs;

    Context myContext;
    LayoutInflater myInflator;
    ClickListener listener;
    int currentRideId;

    public HomeDriverRecyclerViewAdapter(int[] rides, String[] users, String[] pickups, String[] destinations, int[] durations,
                                         double[] costs, Context myContext, ClickListener listener) {
        this.rides = rides;
        this.users = users;
        this.pickups = pickups;
        this.destinations = destinations;
        this.durations = durations;
        this.costs = costs;

        this.myContext = myContext;
        this.myInflator = LayoutInflater.from(myContext);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = myInflator.from(parent.getContext()).inflate(R.layout.home_driver_child_view, parent,false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        currentRideId = rides[position];
        //holder.rideTV.setText(rides[position]);
        holder.userTV.setText("User Name: " + users[position]);
        holder.pickupTV.setText("Pickup Location: " + pickups[position]);
        holder.destinationTV.setText("Destination Location: " + destinations[position]);
        holder.durationTv.setText("Duration: " + String.valueOf(durations[position]));
        holder.costTV.setText("Cost: " + String.format("%.2f", costs[position]) + "$");
    }

    @Override
    public int getItemCount() {
        return rides.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView userTV;
        TextView pickupTV;
        TextView destinationTV;
        TextView durationTv;
        TextView costTV;
        Button acceptB;
        LinearLayout parent;

        WeakReference<ClickListener> listenerRef;

        public ViewHolder(final View itemView, ClickListener listener) {
            super(itemView);
            listenerRef = new WeakReference<>(listener);
            userTV = itemView.findViewById(R.id.nameTV);
            pickupTV = itemView.findViewById(R.id.pickupTV);
            destinationTV = itemView.findViewById(R.id.destinationTV);
            durationTv = itemView.findViewById(R.id.durationTV);
            costTV = itemView.findViewById(R.id.costTV);
            acceptB = itemView.findViewById(R.id.acceptB);

            acceptB.setOnClickListener(this);

            parent = itemView.findViewById(R.id.driverHomeParentLayout);
        }

        // onClick Listener for view
        @Override
        public void onClick (View v){
            listenerRef.get().onPositionClicked(currentRideId);
        }


    }
}