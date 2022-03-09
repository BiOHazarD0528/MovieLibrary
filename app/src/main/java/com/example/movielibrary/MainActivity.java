package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void confirmTitle(View view) {
        EditText editText = findViewById(R.id.TitleTextBox);
        String title = editText.getText().toString();
        showToast(title);
    }

    public void showToast(String title) {
        Toast toast = Toast.makeText(this, "Movie " + title + " has been added.",
                Toast.LENGTH_SHORT);
        toast.show();
    }

    public void doubleCost(View view) {
        EditText costText = findViewById(R.id.costTextBox);
        int cost = Integer.parseInt(costText.getText().toString());
        costText.setText(cost * 2 + "");
    }

    public void clearAll(View view) {
        EditText title = findViewById(R.id.TitleTextBox);
        EditText year = findViewById(R.id.yearTextBox);
        EditText country = findViewById(R.id.CountryTextBox);
        EditText genre = findViewById(R.id.GenreTextBox);
        EditText cost = findViewById(R.id.costTextBox);
        EditText keyword = findViewById(R.id.KeywordTextBox);
        title.getText().clear();
        year.getText().clear();
        country.getText().clear();
        genre.getText().clear();
        cost.getText().clear();
        keyword.getText().clear();
    }
}