package com.tutorial.nura.innovamovie;

import android.os.Bundle;

import com.tutorial.nura.innovamovie.viewmodel.MoviesViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getMovies().observe(this, movies -> {
            if (movies == null) {
                moviesViewModel.loadMovies();
                return;
            }
            //3242

        });
    }
}
