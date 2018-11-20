package com.tutorial.nura.innovamovie.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.tutorial.nura.innovamovie.MainActivity;
import com.tutorial.nura.innovamovie.R;
import com.tutorial.nura.innovamovie.adapters.RecyclerMovieAdapter;
import com.tutorial.nura.innovamovie.pojo.Movie;
import com.tutorial.nura.innovamovie.rest.MovieAPI;
import com.tutorial.nura.innovamovie.utils.ConnectionUtil;
import com.tutorial.nura.innovamovie.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by nura on 20.11.2018.
 */
public class MovieListFragment extends Fragment {

    public static final String TAG = MovieListFragment.class.getName();

    private List<Movie.MovieItem> movies = new ArrayList<>();
    private RecyclerMovieAdapter adapter;
    private ProgressDialog progressDialog;

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

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecyclerMovieAdapter(getActivity(), movies);
        MovieAPI.getService();
        recyclerView.setAdapter(adapter);

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

        if(ConnectionUtil.hasConnection(getContext())) {
            MovieAPI.getService().getPopularMovies()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Movie>() {
                        @Override
                        public void onSuccess(Movie movie) {
                            adapter.updateList(movie.getMovieList());
                            System.out.println("result: " + movie.toString());
                            progressDialog.dismiss();
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
                                adapter.updateList(movie.getMovieList());
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
