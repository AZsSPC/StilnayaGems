package com.azspc.stilnayagems.draw;

import android.graphics.Canvas;

public class Over extends DrawAsset {
    int[] colors;

    public void setFinType(boolean torf) {
        if (torf) setBackGround(colors[0]);
        else setBackGround(colors[1]);
    }

    public Over(int w_c, int l_c) {
        super(0);
        colors = new int[]{w_c, l_c};
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
    }
}
