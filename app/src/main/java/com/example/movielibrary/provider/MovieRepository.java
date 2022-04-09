package com.example.movielibrary.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MovieRepository {
    private MovieDao movieDao;
    private LiveData<List<MovieData>> allMovies;

    MovieRepository(Application application) {
        MovieDatabase db = MovieDatabase.getDatabase(application);
        movieDao = db.movieDao();
        allMovies = movieDao.getAllMovies();
    }

    LiveData<List<MovieData>> getAllMovies() {
        return allMovies;
    }

    void insert(MovieData movie) {
        MovieDatabase.databaseWriteExecutor.execute(() -> movieDao.addMovie(movie));
    }

    void deleteAll() {
        MovieDatabase.databaseWriteExecutor.execute(() -> movieDao.deleteAllMovies());
    }
}
