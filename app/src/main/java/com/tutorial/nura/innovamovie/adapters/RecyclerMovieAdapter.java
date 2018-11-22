package com.tutorial.nura.innovamovie.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorial.nura.innovamovie.MainActivity;
import com.tutorial.nura.innovamovie.R;
import com.tutorial.nura.innovamovie.fragments.MovieDetailFragment;
import com.tutorial.nura.innovamovie.fragments.MovieListFragment;
import com.tutorial.nura.innovamovie.pojo.Movie;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by nura on 17.11.2018.
 */
public class RecyclerMovieAdapter extends RecyclerView.Adapter<RecyclerMovieAdapter.MovieViewHolder> {

    private List<Movie.MovieItem> movies = new ArrayList<>();
    private Activity activity;

    public RecyclerMovieAdapter(Activity activity, List<Movie.MovieItem> movies) {
        this.movies = movies;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        itemView.setOnClickListener(clickListener);
        return new MovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie.MovieItem item = movies.get(position);
        System.out.println("movie" + position + ": " + item);
        holder.title.setText(item.getTitle());
        holder.movieId.setText(String.valueOf(item.getId()));
    }

    public void addMovie(List<Movie.MovieItem> list) {
        movies.addAll(list);
//        movies = list;
        notifyDataSetChanged();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String movieId = ((TextView) view.findViewById(R.id.movieId)).getText().toString();

            Bundle args = new Bundle();
            args.putString("movieId", movieId);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(args);
            ((MainActivity) activity).changeFragment(fragment, true);
        }
    };

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView movieId;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            movieId = itemView.findViewById(R.id.movieId);
        }
    }
}
