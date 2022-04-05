package com.example.movielibrary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    ArrayList<String[]> movieData;

    public RecyclerViewAdapter(ArrayList<String[]> movieData) {
        this.movieData = movieData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int i = 0;
        holder.titleText.setText(movieData.get(position)[i]);
        i++;
        holder.yearText.setText(movieData.get(position)[i]);
        i++;
        holder.countryText.setText(movieData.get(position)[i]);
        i++;
        holder.genreText.setText(movieData.get(position)[i]);
        i++;
        holder.costText.setText(movieData.get(position)[i]);
        i++;
        holder.keywordsText.setText(movieData.get(position)[i]);
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public View itemView;
        public TextView titleText;
        public TextView yearText;
        public TextView countryText;
        public TextView genreText;
        public TextView costText;
        public TextView keywordsText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.titleText = itemView.findViewById(R.id.titleText);
            this.yearText = itemView.findViewById(R.id.yearText);
            this.countryText = itemView.findViewById(R.id.countryText);
            this.genreText = itemView.findViewById(R.id.genreText);
            this.costText = itemView.findViewById(R.id.costText);
            this.keywordsText = itemView.findViewById(R.id.keywordsText);
        }
    }
}
