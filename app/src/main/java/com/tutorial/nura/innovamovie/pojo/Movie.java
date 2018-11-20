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

        @SerializedName("id")
        int id;

        @SerializedName("title")
        private String title;

        @SerializedName("release_date")
        private String releaseDate;

        @SerializedName("budget")
        private int budget;

        @SerializedName("overview")
        private String overview;

        @Override
        public String toString() {
            return "MovieItem{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", releaseDate='" + releaseDate + '\'' +
                    ", budget=" + budget +
                    ", overview='" + overview + '\'' +
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

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public int getBudget() {
            return budget;
        }

        public void setBudget(int budget) {
            this.budget = budget;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }
    }
}
