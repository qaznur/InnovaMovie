package com.tutorial.nura.innovamovie.viewmodel;

import android.annotation.SuppressLint;

import com.tutorial.nura.innovamovie.pojo.Movie;
import com.tutorial.nura.innovamovie.rest.MovieAPI;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    private MutableLiveData<String> moviesError = new MutableLiveData<>();
    private CompositeDisposable disposables = new CompositeDisposable();



    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

//    @SuppressLint("CheckResult")
//    public void loadMovies() {
//        DisposableSingleObserver<List<Movie>> disposableSingleObserver = MovieAPI.getService().getPopularMovies()
//                .subscribeOn(Schedulers.io())
//                .subscribeWith(new DisposableSingleObserver<List<Movie>>() {
//                    @Override
//                    public void onSuccess(List<Movie> movies) {
//                        MoviesViewModel.this.movies.postValue(movies);
//                        System.out.println("### movies: " + movies.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        MoviesViewModel.this.moviesError.postValue(e.getLocalizedMessage());
//                    }
//                });
//
////        disposables.add(disposableSingleObserver);
//    }



}
