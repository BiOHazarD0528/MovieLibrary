package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler uiHandler=new Handler(Looper.getMainLooper());

        LoginViewModel loginViewModel = MainActivity.loginViewModel;

        EditText emailTextBox = findViewById(R.id.emailTextBoxLogin);
        EditText passwordTextBox = findViewById(R.id.passwordTextBoxLogin);

        String email = emailTextBox.getText().toString();
        String password = passwordTextBox.getText().toString();

        executor.execute(() -> {
            LoginData loginData = loginViewModel.getUserCredentials(email);
            uiHandler.post(() -> {
                if (loginData == null) {
                    Toast.makeText(getApplicationContext(), "Email Does Not Exist", Toast.LENGTH_LONG).show();
                }
                else if (!password.equals(loginData.getPassword())) {
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_LONG).show();
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