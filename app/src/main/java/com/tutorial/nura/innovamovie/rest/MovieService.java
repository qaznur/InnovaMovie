package com.tutorial.nura.innovamovie.rest;

import com.tutorial.nura.innovamovie.pojo.Movie;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieService {


    @GET("/discover/movie?sort_by=popularity.desc")
    Single<List<Movie>> getPopularMovies();

}
