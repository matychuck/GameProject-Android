package com.example.user.game_projekt;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository mRepository;

    private LiveData<List<User>> mAllUsers;
    public LiveData<List<User>> mBestUsers;

    public UserViewModel(Application application) {
        super(application);
        mRepository = new UserRepository(application);
        mAllUsers = mRepository.getAllUsers();
        mBestUsers = mRepository.getBestUsers();

    }


    public void deleteAll() {mRepository.deleteAll();}
    public void deleteById(int id) {mRepository.deleteById(id);}
    LiveData<List<User>> getAllUsers() { return mAllUsers; }
    LiveData<List<User>> getBestUsers() { return mBestUsers; }
    public void insert(User user) { mRepository.insert(user); }

}