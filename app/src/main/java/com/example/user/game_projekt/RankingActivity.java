package com.example.user.game_projekt;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import static com.example.user.game_projekt.PopUp.active;
import static com.example.user.game_projekt.PopUp.active_pop;

public class RankingActivity extends AppCompatActivity {
    List<User> list;
    RankingAdapter rankingAdapter;
    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        rankingAdapter = new RankingAdapter(this);
        recyclerView.setAdapter(rankingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getBestUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                // Update the cached copy of the words in the adapter.
                rankingAdapter.setUsers(users);
            }
        });


        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }





                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        int userID = rankingAdapter.getUserId(position);



                        if (direction == ItemTouchHelper.LEFT) { //swipe left

                            userViewModel.deleteById(userID);

                        }else if(direction == ItemTouchHelper.RIGHT){//swipe right

                            userViewModel.deleteById(userID);


                        }
                        // Delete the word




                    }
                });

        helper.attachToRecyclerView(recyclerView);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.deleteall:
                userViewModel.deleteAll();
                return true;

            case R.id.showall:
                userViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
                    @Override
                    public void onChanged(@Nullable final List<User> users) {
                        // Update the cached copy of the words in the adapter.
                        rankingAdapter.setUsers(users);
                    }
                });

                return true;

            case R.id.showbest:
                userViewModel.getBestUsers().observe(this, new Observer<List<User>>() {
                    @Override
                    public void onChanged(@Nullable final List<User> users) {
                        // Update the cached copy of the words in the adapter.
                        rankingAdapter.setUsers(users);
                    }
                });

                return true;

            case R.id.back:
                onStop();

                finish();
                return true;
            default: return true;
        }
    }
    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
        active_pop = false;
    }
}
