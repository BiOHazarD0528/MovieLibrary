package com.example.movielibrary.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private LiveData<List<MovieData>> allMovies;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        allMovies = movieRepository.getAllMovies();
    }

    public LiveData<List<MovieData>> getAllMovies() {
        return allMovies;
    }

    public void insert(MovieData movie) {
        movieRepository.insert(movie);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }

    public void deleteMovieByYear(int movieYear) {
        movieRepository.deleteMovieByYear(movieYear);
    }
}
