package com.tutorial.nura.innovamovie.rest;

import com.tutorial.nura.innovamovie.pojo.Movie;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieService {

    @GET("3/movie/popular")
    Single<List<Movie>> getPopularMovies();

}
