package com.example.user.game_projekt;

import android.widget.ImageView;

public class SlowElement {
    private float x;
    private float y;
    private int width;
    private int height;
    public ImageView image;
    public SlowElement(float x, float y,ImageView image)
    {
        this.x = x;
        this.y = y;
        this.image = image;
        this.image.setX(x);
        this.image.setY(y);
        width = 60;
        height = 80;
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
        float tmpy = getY()-y;
        image.setY(getY()-y);
        this.y = tmpy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setY(float y)
    {
        this.y = y;
        image.setY(y);
    }

    public void setX(float x) {
        this.x = x;
        image.setX(x);
    }
}
