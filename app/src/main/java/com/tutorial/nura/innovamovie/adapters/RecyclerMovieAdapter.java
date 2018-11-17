package com.tutorial.nura.innovamovie.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorial.nura.innovamovie.R;
import com.tutorial.nura.innovamovie.pojo.Movie;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by nura on 17.11.2018.
 */
public class RecyclerMovieAdapter extends RecyclerView.Adapter<RecyclerMovieAdapter.MovieViewHolder> {

    private List<Movie.MovieItem> movies;

    public RecyclerMovieAdapter(List<Movie.MovieItem> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie.MovieItem item = movies.get(position);
        holder.title.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
        }
    }
}
