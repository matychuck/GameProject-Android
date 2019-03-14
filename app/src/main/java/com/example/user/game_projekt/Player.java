package com.example.user.game_projekt;

import android.widget.ImageView;

public class Player {

    private float x;
    private float y;
    private int width;
    private int height;
    public ImageView image;
    public Player(){ }
    public Player(float x, float y,ImageView image)
    {
        this.x = x;
        this.y = y;
        this.image = image;
        this.image.setX(x);
        this.image.setY(y);
        width = 90;
        height = 70;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void moveX(float x)
    {
        float tmpx = getX()+x;
        image.setX(getX()+x);
        this.x = tmpx;

    }
    public void moveY(float y)
    {
        image.setY(getY()+y);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
