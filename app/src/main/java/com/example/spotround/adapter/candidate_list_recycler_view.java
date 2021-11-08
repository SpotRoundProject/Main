package com.example.spotround.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.spotround.R;
import com.example.spotround.modle.CandidateListObj;

import java.util.List;

public class candidate_list_recycler_view extends RecyclerView.Adapter<candidate_list_recycler_view.ViewHolder> {
    List<CandidateListObj> list;
    Context context;

    public candidate_list_recycler_view(List<CandidateListObj> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public candidate_list_recycler_view.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.candidate_list_recycler_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull candidate_list_recycler_view.ViewHolder holder, int position) {
        CandidateListObj candidate = list.get(position);

        holder.rank.setText(candidate.getRank());
        holder.applicationId.setText(candidate.getApplicationId());
        holder.name.setText(candidate.getName());
        holder.cetPercentage.setText(candidate.getCetPercentage());
        holder.caste.setText(candidate.getCaste());
        holder.seat.setText(candidate.getSeat());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView rank, applicationId, name, cetPercentage, caste, seat;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            rank = itemView.findViewById(R.id.rank);
            applicationId = itemView.findViewById(R.id.applicationID);
            name = itemView.findViewById(R.id.name);
            cetPercentage = itemView.findViewById(R.id.cetPercentage);
            caste = itemView.findViewById(R.id.caste);
            seat = itemView.findViewById(R.id.seat);
        }
    }
}
