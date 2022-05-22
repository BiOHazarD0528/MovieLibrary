package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movielibrary.login_provider.LoginData;
import com.example.movielibrary.login_provider.LoginViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUp extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    public void signUp(View view) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler uiHandler=new Handler(Looper.getMainLooper());

        EditText emailTextBox = findViewById(R.id.emailTextBox);
        EditText passwordTextBox = findViewById(R.id.passwordTextBox);

        String email = emailTextBox.getText().toString();
        String password = passwordTextBox.getText().toString();

        LoginData loginData = new LoginData(email, password);


        executor.execute(() -> {
            LoginData test = loginViewModel.getUserCredentials(email);
            uiHandler.post(() -> {
                if (test != null) {
                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                }
                else {
                    loginViewModel.signUp(loginData);
                    Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("email", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("emailName", loginData.getEmail());
                    editor.apply();
                    finish();
                }
            });
        });
    }
}