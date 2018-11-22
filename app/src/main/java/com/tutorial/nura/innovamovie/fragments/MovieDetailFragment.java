package com.tutorial.nura.innovamovie.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tutorial.nura.innovamovie.MainActivity;
import com.tutorial.nura.innovamovie.R;
import com.tutorial.nura.innovamovie.pojo.Movie;
import com.tutorial.nura.innovamovie.rest.MovieAPI;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nura on 20.11.2018.
 */
public class MovieDetailFragment extends Fragment {

    private String movieId;

    private TextView caption;
    private TextView budget;
    private TextView releaseDate;
    private TextView overview;
    private ProgressDialog progressDialog;
    private ImageView favorite;

    private SharedPreferences preferences;
    private Gson gson = new Gson();
    private Type type = new TypeToken<List<String>>() {
    }.getType();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        ((MainActivity) getActivity()).setTitle(("Детальный экран о фильме"));

        movieId = getArguments().getString("movieId");
        initViews();
        loadInfo(movieId);

        favorite.setOnClickListener(view -> {
            List<String> favoriteList = getFavoriteMovies();
            if (isFavorite()) {
                favoriteList.remove(movieId);
            } else {
                favoriteList.add(movieId);
            }

            String newJson = gson.toJson(favoriteList);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("favorites", newJson);
            editor.apply();

            favorite.setImageResource(isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
        });
    }

    private List<String> getFavoriteMovies() {
        String json = preferences.getString("favorites", null);
        List<String> list;
        if (json != null) {
            list = gson.fromJson(json, type);
        } else {
            list = new ArrayList<>();
        }

        return list;
    }

    private void initViews() {
        caption = getView().findViewById(R.id.captionValue);
        budget = getView().findViewById(R.id.budgetValue);
        releaseDate = getView().findViewById(R.id.releaseDateValue);
        overview = getView().findViewById(R.id.overviewValue);
        favorite = getView().findViewById(R.id.favorite);

        preferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        favorite.setImageResource(isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);
    }

    private boolean isFavorite() {
        for (String id : getFavoriteMovies()) {
            if (id.equals(movieId)) {
                return true;
            }
        }
        return false;
    }

    private void updateViews(Movie.MovieItem movieItem) {
        caption.setText(movieItem.getTitle());
        budget.setText(String.valueOf(movieItem.getBudget()));
        releaseDate.setText(movieItem.getReleaseDate());
        overview.setText(movieItem.getOverview());
    }

    private void loadInfo(String movieId) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Загружается...");
        progressDialog.show();

        MovieAPI.getService().getDetailInfo(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Movie.MovieItem>() {
                    @Override
                    public void onSuccess(Movie.MovieItem movieItem) {
                        updateViews(movieItem);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                    }
                });
    }
}
