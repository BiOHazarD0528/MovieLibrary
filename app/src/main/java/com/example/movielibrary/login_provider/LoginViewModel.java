package com.example.movielibrary.login_provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository(application);
    }

    public void signUp(LoginData loginData) {
        loginRepository.signUp(loginData);
    }

    public LoginData getUserCredentials(String email) {
        return loginRepository.getUserCredentials(email);
    }

    public void deleteAllData() {
        loginRepository.deleteAllData();
    }
}
