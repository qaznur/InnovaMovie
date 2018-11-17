package com.tutorial.nura.innovamovie;

import android.os.Bundle;

import com.tutorial.nura.innovamovie.pojo.Movie;
import com.tutorial.nura.innovamovie.rest.MovieAPI;
import com.tutorial.nura.innovamovie.viewmodel.MoviesViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
//        moviesViewModel.getMovies().observe(this, movies -> {
//            if (movies == null) {
//                moviesViewModel.loadMovies();
//                System.out.println("### movie is null");
//                return;
//            }
//            System.out.println("### movie is not null");
//
//            //3242
//
//        });

        DisposableSingleObserver<List<Movie>> disposableSingleObserver = MovieAPI.getService().getPopularMovies()
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<List<Movie>>() {
                    @Override
                    public void onSuccess(List<Movie> movies) {
//                        MoviesViewModel.this.movies.postValue(movies);
                        System.out.println("### movies: " + movies.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("### movies: " + e.toString());
//                        MoviesViewModel.this.moviesError.postValue(e.getLocalizedMessage());
                    }
                });
    }
}
