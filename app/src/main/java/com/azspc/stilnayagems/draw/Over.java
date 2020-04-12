package com.azspc.stilnayagems.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import static com.azspc.stilnayagems.Main.store;

public class Over extends DrawAsset {
    private int[] colors;
    private String out;
    private String[][] msg = {
            {"You great!", "Success!", "Nice score!", "Good job", "You win!"},
            {"Try again...", "Lose", "Bad score", "You can replay it!"}};
    private Paint paint;

     void setFinType(boolean type) {
        setBackGround(colors[type ? 0 : 1]);
        out = msg[type ? 0 : 1][(int) (Math.random() * msg.length - 0.1)];
    }

    public Over(int w_c, int l_c, Paint p) {
        super(0);
        paint = p;
        out = "that's an a error, lol";
        colors = new int[]{w_c, l_c};
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        int ts = store.getTextSize() * 2;
        paint.setTextSize(ts);
        paint.setTextAlign(Paint.Align.CENTER);
        c.drawText("Game over", store.getScreenSize(0) >> 1, (store.getScreenSize(1) - ts) >> 1, paint);
        c.drawText("Score: " + store.getPlay().getScore(), store.getScreenSize(0) >> 1, (store.getScreenSize(1) + ts) >> 1, paint);
        paint.setTextSize(ts >> 1);
        c.drawText(out, store.getScreenSize(0) >> 1, (store.getScreenSize(1) + ts * 4) >> 1, paint);
    }
}
