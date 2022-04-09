package com.example.movielibrary.provider;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movieTable")
public class MovieData {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "movieId")
    private int movieId;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "year")
    private String year;
    @ColumnInfo(name = "country")
    private String country;
    @ColumnInfo(name = "genre")
    private String genre;
    @ColumnInfo(name = "cost")
    private String cost;
    @ColumnInfo(name = "keywords")
    private String keywords;

    public MovieData(String title, String year, String country, String genre, String cost, String keywords) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.genre = genre;
        this.cost = cost;
        this.keywords = keywords;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genre;
    }

    public String getCost() {
        return cost;
    }

    public String getKeywords() {
        return keywords;
    }
}
