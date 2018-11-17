package com.tutorial.nura.innovamovie.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<MovieItem> movieList;

    @Override
    public String toString() {
        return "Movie{" +
                "page=" + page +
                ", movieList=" + movieList +
                '}';
    }

    public List<MovieItem> getMovieList() {
        return movieList;
    }

    public class MovieItem {

        int id;
        private String title;

        @Override
        public String toString() {
            return "MovieItem{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
