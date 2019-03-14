package com.example.user.game_projekt;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class UserRepository {
    private UserDao mUserDao;
    public LiveData<List<User>> mAllUsers;
    public LiveData<List<User>> mBestUsers;


    UserRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        mUserDao = db.userDao();
        mAllUsers = mUserDao.getAllUsers();
        mBestUsers = mUserDao.getBestUsers();
    }



    LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public void deleteAll() {new deleteUserAsyncTask(mUserDao).execute();}
    public void insert (User p) {
        new insertAsyncTask(mUserDao).execute(p);
    }
    public void deleteById(int id)  {
        new deleteUserByIDAsyncTask(mUserDao).execute(id);
    }
    LiveData<List<User>> getBestUsers() { return mBestUsers; }


    private static class deleteUserByIDAsyncTask extends AsyncTask<Integer, Void, Void> {
        private UserDao mAsyncTaskDao;

        deleteUserByIDAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteByID(params[0]);
            return null;
        }
    }



    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteUserAsyncTask extends AsyncTask<Integer, Void, Void> {
        private UserDao mAsyncTaskDao;

        deleteUserAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
