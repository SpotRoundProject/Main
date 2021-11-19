package com.example.spotround.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotround.R;
import com.example.spotround.modle.Allotedseats;

import java.util.List;

public class alloted_seats_recycler_view extends RecyclerView.Adapter<alloted_seats_recycler_view.ViewHolder> {
    List<Allotedseats> list;
    Context context;

    public alloted_seats_recycler_view(List<Allotedseats> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public alloted_seats_recycler_view.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.alloted_seats_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull alloted_seats_recycler_view.ViewHolder holder, int position) {
        Allotedseats allotedseats=list.get(position);
        holder.seat.setText(allotedseats.getSeat());
        holder.seat_type.setText(allotedseats.getSeat_type());
        holder.name.setText(allotedseats.getName());
        holder.caste.setText(allotedseats.getCaste());
        holder.applicationId.setText(allotedseats.getApplicationId());
        holder.rank.setText(allotedseats.getRank());
        holder.preference.setText(allotedseats.getPreference());
        holder.cetPercentage.setText(allotedseats.getCetPercentage());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView rank, applicationId, name, cetPercentage, caste, seat,seat_type,preference;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rank=itemView.findViewById(R.id.rank);
            applicationId=itemView.findViewById(R.id.applicationID);
            name=itemView.findViewById(R.id.name);
            cetPercentage=itemView.findViewById(R.id.cetPercentage);
            caste=itemView.findViewById(R.id.caste);
            seat=itemView.findViewById(R.id.seat);
            seat_type=itemView.findViewById(R.id.seat_type);
            preference=itemView.findViewById(R.id.preference7);
        }
    }
}
