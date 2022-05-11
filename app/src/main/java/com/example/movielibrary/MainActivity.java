package com.example.movielibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.example.movielibrary.provider.MovieData;
import com.example.movielibrary.provider.MovieViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {

    EditText titleText, yearText, countryText, genreText, costText, keywordText;
    ArrayList<String> list = new ArrayList<>();
    //ArrayList<MovieData> movieData = new ArrayList<>();
    ArrayAdapter adapter;
    DrawerLayout drawer;
    static MovieViewModel movieViewModel;
    RecyclerViewAdapter recyclerViewAdapter;
    DatabaseReference myRef;
    View myFrame;
    int x_down;
    int y_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myFrame = findViewById(R.id.frameLayout);

        myFrame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        x_down = (int) event.getX();
                        y_down = (int) event.getY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (x_down >= myFrame.getWidth()-100 && x_down <= myFrame.getWidth()
                                && y_down >= 0 && y_down <= 100) {
                            EditText cost = findViewById(R.id.costTextBox);
                            int newCost = Integer.parseInt(cost.getText().toString()) + 50;
                            cost.setText(String.valueOf(newCost));
                        }
                        else if (x_down >= 0 && x_down <= 100 && y_down >= 0 && y_down <= 100) {
                            EditText cost = findViewById(R.id.costTextBox);
                            int newCost = Integer.parseInt(cost.getText().toString()) - 50;
                            if (newCost < 0) {
                                newCost = 0;
                            }
                            cost.setText(String.valueOf(newCost));
                        }
                        else if (Math.abs(x_down - event.getX()) < 40) {
                            if (y_down - event.getY() < 0) {
                                clearAll(v);
                            }
                        }
                        else if (Math.abs(y_down - event.getY()) < 40) {
                            if (x_down - event.getX() < 0) {
                                addMovie(v);
                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        recyclerViewAdapter = new RecyclerViewAdapter();
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.getAllMovies().observe(this, newData -> {
            recyclerViewAdapter.setMovieData(newData);
            recyclerViewAdapter.notifyDataSetChanged();
        });

        ListView listView = findViewById(R.id.listview);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMovie(view);
            }
        });

        drawer = findViewById(R.id.drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationListener());

        titleText = findViewById(R.id.titleTextBox);
        yearText = findViewById(R.id.yearTextBox);
        countryText = findViewById(R.id.countryTextBox);
        genreText = findViewById(R.id.genreTextBox);
        costText = findViewById(R.id.costTextBox);
        keywordText = findViewById(R.id.keywordsTextBox);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS}, 0);

        IntentFilter intentFilter = new IntentFilter(SMSReceiver.SMS_FILTER);
        registerReceiver(myBroadcastReceiver, intentFilter);
    }

    class NavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.addmovie) {
                addMovie(findViewById(R.id.constraintlayout));
            }
            else if (id == R.id.removelastmovie) {
                list.remove(list.size() - 1);
                adapter.notifyDataSetChanged();
            }
            else if (id == R.id.removeallmovies) {
                list.clear();
                adapter.notifyDataSetChanged();
                movieViewModel.deleteAll();
                myRef.removeValue();
            }
            else if (id == R.id.listmovies) {
//                Gson gson = new Gson();
//                String dbStr = gson.toJson(movieData);
//
//                SharedPreferences sharedPreferences = getSharedPreferences("movieData", 0);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("json", dbStr);
//                editor.apply();

                Intent intent = new Intent(getApplicationContext(), ListAllMovies.class);
                startActivity(intent);
            }
            else if (id == R.id.closeapp) {
                finish();
            }
            drawer.closeDrawers();
            return true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clearall) {
            clearAll(findViewById(R.id.constraintlayout));
        }
        else if (id == R.id.totalmovies) {
            Toast toast = Toast.makeText(this,  "Total Movies: " + list.size(),
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    BroadcastReceiver myBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);

            StringTokenizer stringTokenizer = new StringTokenizer(msg, ";");
            titleText.setText(stringTokenizer.nextToken());
            yearText.setText(stringTokenizer.nextToken());
            countryText.setText(stringTokenizer.nextToken());
            genreText.setText(stringTokenizer.nextToken());
            int cost = Integer.parseInt(stringTokenizer.nextToken());
            keywordText.setText(stringTokenizer.nextToken());
            int hiddenCost = Integer.parseInt(stringTokenizer.nextToken());
            int totalCost = cost + hiddenCost;
            costText.setText(String.valueOf(totalCost));
        }
    };

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
        //outState.putStringArrayList("arrayList", list);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText title = findViewById(R.id.titleTextBox);
        EditText genre = findViewById(R.id.genreTextBox);
        title.setText(title.getText().toString().toUpperCase());
        genre.setText(savedInstanceState.getString("genre"));
        //list.addAll(savedInstanceState.getStringArrayList("arrayList"));
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
        list.add(title + " | " + year);
        adapter.notifyDataSetChanged();
        MovieData movie = new MovieData(title, year, country, genre, cost, keyword);
        movieViewModel.insert(movie);
        myRef.push().setValue(movie);

        //this.movieData.add(movie);

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
        SharedPreferences sp = getSharedPreferences("movieData", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public void clearSP(View view) {
        SharedPreferences data = getSharedPreferences("movieData", 0);
        SharedPreferences.Editor editor = data.edit();
        editor.clear();
        editor.apply();
    }

    public void loadX2(View view) {
        doubleCost(view);
        SharedPreferences data = getSharedPreferences("movieData", 0);
        int cost = Integer.parseInt(data.getString("cost", "")) * 2;
        SharedPreferences.Editor editor = data.edit();
        editor.putString("cost", String.valueOf(cost));
        editor.apply();
    }
}