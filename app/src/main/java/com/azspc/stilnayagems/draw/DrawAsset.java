package com.azspc.stilnayagems.draw;

import android.graphics.Canvas;

public class DrawAsset {

    private int color_back_ground;

    DrawAsset(int cbg) {
        color_back_ground = cbg;
    }

    void setBackGround(int color_back_ground) {
        this.color_back_ground = color_back_ground;
    }

    public void draw(Canvas c) {
        c.drawColor(color_back_ground);
    }
}
