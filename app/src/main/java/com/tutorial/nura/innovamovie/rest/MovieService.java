package com.tutorial.nura.innovamovie.rest;

import com.tutorial.nura.innovamovie.pojo.Movie;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("3/movie/popular")
    Single<Movie> getPopularMovies();

    @GET("3/search/movie")
    Single<Movie> searchMovie(@Query("query") String text);

    @GET("3/movie/{movie_id}")
    Single<Movie.MovieItem> getDetailInfo(@Path("movie_id") String movieId);
}
