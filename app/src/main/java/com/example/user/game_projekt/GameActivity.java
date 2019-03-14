package com.example.user.game_projekt;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.user.game_projekt.PopUp.active;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    UserViewModel userViewModel;
    public static int END_ACTIVITY_REQUEST_CODE = 0;
    private SensorManager mSensorManager;
    private Sensor mAccelerator;
    private Player player;
    private SlowElement slowElement;
    private LightBulb lightBulb;
    private TextView score;
    private float proximityValue=1;
    private WindowManager.LayoutParams params;
    ImageView playerView;
    ImageView lightbulb;
    ImageView slow;
    //ImageView playerView;
    ImageView[] enemyView = new ImageView[6];
    List<Enemy> enemies;
    ProgressBar progressBar;
    ProgressBar progressBarHealth;
    float width,height;
    int slow_pointer=6;
    int distance = 400;
    int life = 90;
    int speed = 3;
    boolean missedSlow = false;
    int slow_speed=2;
    int guard=0;
    int points=0;
    volatile boolean running = true;
    Random generator = new Random();
    Context context;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        name = getIntent().getStringExtra("nick");
        context = getApplicationContext();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

               playerView = findViewById(R.id.player);
               enemyView[0] = findViewById(R.id.enemy1);
               enemyView[1] = findViewById(R.id.enemy2);
               enemyView[2] = findViewById(R.id.enemy3);
               enemyView[3] = findViewById(R.id.enemy4);
               enemyView[4] = findViewById(R.id.enemy5);
               enemyView[5] = findViewById(R.id.enemy6);
               lightbulb = findViewById(R.id.lightbulb);
               slow = findViewById(R.id.slow);
               score = findViewById(R.id.score);
               progressBar = findViewById(R.id.progressBar);
               progressBarHealth = findViewById(R.id.progressBarHealth);
               Drawable progressDrawable = progressBarHealth.getProgressDrawable().mutate();
               progressDrawable.setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
               progressBarHealth.setProgressDrawable(progressDrawable);
               progressBar.setProgress(100);
               Display display = getWindowManager().getDefaultDisplay();
               Point size = new Point();
               display.getSize(size);
               width = size.x;
               height = size.y;
               player = new Player(width/2 - 25,75,playerView);
               enemies = new ArrayList<>();
               for(int i=0;i<6;i++)
               {
                   enemies.add(new Enemy(generator.nextFloat()*(width - 100) ,height+i*400,enemyView[i]));
               }
               lightBulb = new LightBulb(width/2,height+3000,lightbulb);
               slowElement = new SlowElement(width/2,height+10000,slow);









        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mAccelerator = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        params = getWindow().getAttributes();
        params.screenBrightness = proximityValue;
        getWindow().setAttributes(params);


        final Handler handler=new Handler();

        handler.post(new Thread(){


            @Override
            public void run() {
                if (!running) return;
                for(int i=0;i<6;i++) {
                    enemies.get(i).moveY(speed);
                    ;
                    if(enemies.get(i).getY()+enemies.get(i).getHeight()<0)
                    {
                        enemies.get(i).setY(maxEnemyY(enemies)+distance);
                        enemies.get(i).setX(generator.nextFloat()*(width-enemies.get(i).getWidth()));
                        if(life>0)
                        {
                            life-=30;
                            progressBarHealth.setProgress(life);
                        }
                        if(life==0)
                        {
                                running=false;
                                userViewModel.insert(new User(name,points));
                                Intent scoreIntent = new Intent(GameActivity.this,PopUp.class);
                                scoreIntent.putExtra("points",points);
                                startActivity(scoreIntent);

                        }

                       // guard++;
                       // if(guard%6==0)
                        //{
                        //    distance--;
                           // speed++;
                          //  points++;
                           // score.setText(String.valueOf(points));
                       // }

                    }
                }
                lightBulb.moveY(4);
                slowElement.moveY(4);
                if(lightBulb.getY()+lightBulb.getHeight()<0)
                {
                    lightBulb.setY((generator.nextInt(1000)+height*2) + generator.nextFloat()*1000);
                    lightBulb.setX(generator.nextFloat()*(width-lightBulb.getWidth()));
                }
                if(slowElement.getY()+slowElement.getHeight()<0)
                {
                    slowElement.setY((generator.nextInt(1000)+height*2) + generator.nextFloat()*3000);
                    slowElement.setX(generator.nextFloat()*(width-slowElement.getWidth()));
                    slow_speed++;
                    missedSlow=true;
                }

                handler.postDelayed(this,10); // set time here to refresh textView
            }
        });

        handler.post(new Thread()
        {
            @Override
            public void run()
            {
                for(int j=0;j<6;j++)
                {
                    if(collision(player.getX(),player.getY(),player.getWidth(),player.getHeight(),enemies.get(j).getX(),enemies.get(j).getY(),enemies.get(j).getWidth(),enemies.get(j).getHeight()))
                    {
                        points++;
                        score.setText(String.valueOf(points));
                        enemies.get(j).setY(maxEnemyY(enemies)+distance);
                        enemies.get(j).setX(generator.nextFloat()*(width-enemies.get(j).getWidth()));
                        guard++;

                        if(guard%slow_pointer==0)
                        {
                            distance--;
                            speed++;
                            slow_pointer+=2;
                            guard=0;

                        }
                        if(points%5==0)
                        {
                            if(proximityValue>0.1)
                            {
                                proximityValue+=-0.10;
                                params.screenBrightness = proximityValue;
                                getWindow().setAttributes(params);
                                progressBar.setProgress(Math.round(proximityValue*100));

                            }
                            else
                            {
                                proximityValue=0;
                                params.screenBrightness = proximityValue;
                                getWindow().setAttributes(params);
                                progressBar.setProgress(0);

                            }
                        }
                    }

                }
                if(collision(player.getX(),player.getY(),player.getWidth(),player.getHeight(),slowElement.getX(),slowElement.getY(),slowElement.getWidth(),slowElement.getHeight()))
                {
                    if(speed>4)
                    {
                        speed-=slow_speed; distance++;
                    }

                    slowElement.setY((generator.nextInt(1000)+height*3) + generator.nextFloat()*3000);
                    slowElement.setX(generator.nextFloat()*(width-slowElement.getWidth()));
                    if(missedSlow)
                    {
                        slow_speed--;
                        missedSlow=false;
                    }

                }
                if(collision(player.getX(),player.getY(),player.getWidth(),player.getHeight(),lightBulb.getX(),lightBulb.getY(),lightBulb.getWidth(),lightBulb.getHeight()))
                {
                    if(proximityValue<1)
                    {
                        proximityValue+=0.2;
                        params.screenBrightness = proximityValue;
                        getWindow().setAttributes(params);
                        progressBar.setProgress(Math.round(proximityValue*100));

                    }

                    lightBulb.setY((generator.nextInt(1000)+height*3) + generator.nextFloat()*3000);
                    lightBulb.setX(generator.nextFloat()*(width-lightBulb.getWidth()));


                }
                handler.postDelayed(this,1); // set time here to refresh textView
            }
        });

        handler.post(new Thread() {
            @Override
            public void run() {
                if(!active)
                {
                    finish();
                }
                handler.postDelayed(this,10);
            }

        });


    }

    public boolean collision(float px, float py,float p_width,float p_height,float ex, float ey,float e_width,float e_height)
    {
        if((ex>px && px+p_width>ex)&&(ey<=py+p_height && ey+e_height>py+p_height))
        {
            return true;
        }
        else if((ex>px && ex+e_width<px + p_width)&&(ey<=py+p_height && ey+e_height>py+p_height))
        {
            return true;
        }
        else if ((ex<px && ex+e_width>px)&&(ey<=py+p_height && ey+e_height>py+p_height))
        {
            return true;
        }
        else
        {
            return false;
        }

    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {

       if(player.getX()-20>0 && player.getX()+player.getWidth()<width)
        {
            player.moveX(sensorEvent.values[0]*(-1));
        }
        else
        {
            if(sensorEvent.values[0]<0)
            {
                player.moveX((-1)*10);
            }
            else
            {
                player.moveX(10);
            }

        }

    }

    public float maxEnemyY(List<Enemy> list)
    {
        float max = height;
        for(Enemy e : list)
        {
            if(e.getY()>max)
            {
                max = e.getY();
            }
        }
        return max;
    }

    @Override
    protected void onPause()
    { super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerator, SensorManager.SENSOR_DELAY_FASTEST);
    }


}

