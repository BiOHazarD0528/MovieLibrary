package com.example.movielibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences data = getSharedPreferences("movieData", 0);

        EditText titleText = findViewById(R.id.titleTextBox);
        EditText yearText = findViewById(R.id.yearTextBox);
        EditText countryText = findViewById(R.id.countryTextBox);
        EditText genreText = findViewById(R.id.genreTextBox);
        EditText costText = findViewById(R.id.costTextBox);
        EditText keywordText = findViewById(R.id.keywordsTextBox);

        titleText.setText(data.getString("title", ""));
        yearText.setText(data.getString("year", ""));
        countryText.setText(data.getString("country", ""));
        genreText.setText(data.getString("genre", ""));
        costText.setText(data.getString("cost", ""));
        keywordText.setText(data.getString("keyword", ""));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        EditText genre = findViewById(R.id.genreTextBox);
        String lowerCaseGenre = genre.getText().toString().toLowerCase();
        outState.putString("genre", lowerCaseGenre);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText title = findViewById(R.id.titleTextBox);
        EditText genre = findViewById(R.id.genreTextBox);
        title.setText(title.getText().toString().toUpperCase());
        genre.setText(savedInstanceState.getString("genre"));
    }

    public void addMovie(View view) {
        EditText titleText = findViewById(R.id.titleTextBox);
        EditText yearText = findViewById(R.id.yearTextBox);
        EditText countryText = findViewById(R.id.countryTextBox);
        EditText genreText = findViewById(R.id.genreTextBox);
        EditText costText = findViewById(R.id.costTextBox);
        EditText keywordText = findViewById(R.id.keywordsTextBox);

        String title = titleText.getText().toString();
        String year = yearText.getText().toString();
        String country = countryText.getText().toString();
        String genre = genreText.getText().toString();
        String cost = costText.getText().toString();
        String keyword = keywordText.getText().toString();
        showToast(title);

        SharedPreferences data = getSharedPreferences("movieData", 0);
        SharedPreferences.Editor editor = data.edit();
        editor.putString("title", title);
        editor.putString("year", year);
        editor.putString("country", country);
        editor.putString("genre", genre);
        editor.putString("cost", cost);
        editor.putString("keyword", keyword);

        editor.apply();
    }

    public void showToast(String title) {
        Toast toast = Toast.makeText(this, "Movie " + title + " has been added.",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    public void doubleCost(View view) {
        EditText costText = findViewById(R.id.costTextBox);
        int cost = Integer.parseInt(costText.getText().toString());
        costText.setText(String.valueOf(cost * 2));
    }

    public void clearAll(View view) {
        EditText title = findViewById(R.id.titleTextBox);
        EditText year = findViewById(R.id.yearTextBox);
        EditText country = findViewById(R.id.countryTextBox);
        EditText genre = findViewById(R.id.genreTextBox);
        EditText cost = findViewById(R.id.costTextBox);
        EditText keyword = findViewById(R.id.keywordsTextBox);
        title.setText("");
        year.setText("");
        country.setText("");
        genre.setText("");
        cost.setText("");
        keyword.setText("");
    }
}