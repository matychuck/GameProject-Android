package com.example.user.game_projekt;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("DELETE FROM users WHERE user_id = :id")
    void deleteByID(int id);

    @Query("SELECT * from users ORDER BY user_points DESC")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * from users ORDER BY user_points DESC limit 5")
    LiveData<List<User>> getBestUsers();
    @Query("DELETE FROM users")
    void deleteAll();
}
