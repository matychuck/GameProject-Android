package com.example.user.game_projekt;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class User {

    public User()
    {
        this.name = "test";
        this.points = 69;
    }

    public User(@NonNull String nick, int score)
    {
        this.name = nick;
        this.points = score;
    }

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "user_id")
    private int id;

    @ColumnInfo(name = "user_name")
    private String name;

    @ColumnInfo(name = "user_points")
    private int points;

    @NonNull
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
