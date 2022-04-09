package com.example.movielibrary.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("select * from movieTable")
    LiveData<List<MovieData>> getAllMovies();

    @Insert
    void addMovie(MovieData movie);

    @Query("delete from movieTable")
    void deleteAllMovies();
}
