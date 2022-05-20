package com.example.movielibrary.login_provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LoginRepository {
    private LoginDao loginDao;

    public LoginRepository(Application application) {
        LoginDatabase db = LoginDatabase.getDatabase(application);
        this.loginDao = db.loginDao();
    }

    void signUp(LoginData loginData) {
        LoginDatabase.databaseWriteExecutor.execute(() -> loginDao.signUp(loginData));
    }

    LoginData getUserCredentials(String email) {
        return loginDao.getUserCredentials(email);
    }

    void deleteAllData() {
        LoginDatabase.databaseWriteExecutor.execute(() -> loginDao.deleteAllData());
    }

}
