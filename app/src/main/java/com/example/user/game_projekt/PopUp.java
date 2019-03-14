package com.example.user.game_projekt;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopUp extends Activity  {
    static boolean active_pop = true;
    static boolean active = true;
    TextView score;
    Button back;
    Button ranking;
    public static final String EXTRA_REPLY = "com.example.user.game_projekt.REPLY";
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        DisplayMetrics display = new DisplayMetrics(); getWindowManager().getDefaultDisplay().getMetrics(display);
        int points = getIntent().getIntExtra("points",0);
        int width = display.widthPixels;
        int height = display.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.6));
        score = findViewById(R.id.score);
        score.setText(String.valueOf(points));
        ranking = findViewById(R.id.ranking);
        back = findViewById(R.id.back);

        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopUp.this,RankingActivity.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStop();
                finish();
            }
        });

        final Handler handler=new Handler();
        handler.post(new Thread() {
            @Override
            public void run() {
                if(!active_pop)
                {
                    active=false;
                    finish();
                }
                handler.postDelayed(this,10);
            }

        });

    }
    @Override
    public void onStart() {
        super.onStart();
        active = true;

    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }
}
