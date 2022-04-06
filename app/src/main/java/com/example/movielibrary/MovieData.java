package com.example.movielibrary;

public class MovieData {
    private String title;
    private String year;
    private String country;
    private String genre;
    private String cost;
    private String keywords;

    public MovieData(String title, String year, String country, String genre, String cost, String keywords) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.genre = genre;
        this.cost = cost;
        this.keywords = keywords;
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
