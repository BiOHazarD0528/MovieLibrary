package com.example.movielibrary.login_provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LoginDao {
    @Insert
    void signUp(LoginData loginData);

    @Query("select * from loginTable where email = :email")
    LoginData getUserCredentials(String email);

    @Query("delete from loginTable")
    void deleteAllData();
}
