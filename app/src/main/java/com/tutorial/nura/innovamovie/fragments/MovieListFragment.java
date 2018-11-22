package com.tutorial.nura.innovamovie.fragments;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tutorial.nura.innovamovie.MainActivity;
import com.tutorial.nura.innovamovie.R;
import com.tutorial.nura.innovamovie.adapters.RecyclerMovieAdapter;
import com.tutorial.nura.innovamovie.pojo.Movie;
import com.tutorial.nura.innovamovie.rest.MovieAPI;
import com.tutorial.nura.innovamovie.utils.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nura on 20.11.2018.
 */
public class MovieListFragment extends Fragment {

    public static final String TAG = MovieListFragment.class.getName();

    private List<Movie.MovieItem> movies = new ArrayList<>();
    private RecyclerMovieAdapter adapter;
    private LinearLayoutManager layoutManager;

    private ProgressDialog progressDialog;

    private boolean isLastPage = false;
    private boolean isLoading = false;
    private static final int MAX_PAGE = 10;
    private int pageCount = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);
        ((MainActivity) getActivity()).setTitle(("Список популярных фильмов"));
        setHasOptionsMenu(true);


        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new LinearLayoutManager(getContext());
        }

        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerMovieAdapter(getActivity(), movies);
        recyclerView.setAdapter(adapter);

        loadMovie(pageCount);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                if (lastVisibleItemPosition == adapter.getItemCount() - 1) {
                    if (!isLoading && !isLastPage) {
                        isLoading = true;
                        loadMovie(++pageCount);
                    }
                }
            }
        });

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Загружается...");
        progressDialog.show();

//        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
//        moviesViewModel.getMovies().observe(this, movies -> {
//            if (movies == null) {
//                moviesViewModel.loadMovies();
//                return;
//            }
//        });

    }

    private void loadMovie(int page) {
        if (ConnectionUtil.hasConnection(getContext())) {
            MovieAPI.getService().getPopularMovies(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Movie>() {
                        @Override
                        public void onSuccess(Movie movie) {
                            adapter.addMovie(movie.getMovieList());

                            isLoading = false;
                            isLastPage = page == MAX_PAGE;

                            progressDialog.dismiss();
                            if (getContext() != null) {
                                Toast.makeText(getContext(), page + " page is loaded", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            progressDialog.dismiss();
                        }
                    });
        } else {
            Toast.makeText(getContext(), "Нет соединения к интернету", Toast.LENGTH_LONG).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu, menu);

        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint("Поиск...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MovieAPI.getService().searchMovie(newText)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DisposableSingleObserver<Movie>() {
                            @Override
                            public void onSuccess(Movie movie) {
                                adapter.addMovie(movie.getMovieList());
                                System.out.println("result: " + movie.toString());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                return false;
            }
        });
    }

}
