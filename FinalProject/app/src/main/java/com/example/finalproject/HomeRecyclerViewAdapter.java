package com.example.finalproject;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    String[] pastPickups;
    String[] pastDestinations;

    Context myContext;
    LayoutInflater myInflator;

    public HomeRecyclerViewAdapter(String[] pastPickups, String[] pastDestinations,
                                   Context myContext) {
        this.pastPickups = pastPickups;
        this.pastDestinations = pastDestinations;

        this.myContext = myContext;
        this.myInflator = LayoutInflater.from(myContext);
    }

    @NonNull
    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = myInflator.from(parent.getContext()).inflate(R.layout.home_child_view, parent,false);
        return new HomeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.pastPickupTv.setText(pastPickups[position]);
        holder.pastDestinationTv.setText(pastDestinations[position]);
    }

    @Override
    public int getItemCount() {
        return pastDestinations.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pastPickupTv;
        TextView pastDestinationTv;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pastPickupTv = itemView.findViewById(R.id.pastPickupTv);
            pastDestinationTv = itemView.findViewById(R.id.pastDestinationTv);

            parent = itemView.findViewById(R.id.homeParentLayout);
        }
    }
}