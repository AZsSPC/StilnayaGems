package com.azspc.stilnayagems.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import static com.azspc.stilnayagems.Main.store;

public class Over extends DrawAsset {
    private int[] colors;
    private String out;
    private String[] win = {"You great!", "Success!", "Nice score!", "Good job", "You win!"};
    private String[] lose = {"Try again...", "Lose", "Bad score", "You can replay it!"};
    private Paint paint;

    public void setFinType(boolean torf) {
        if (torf) {
            setBackGround(colors[0]);
            out = win[(int) (Math.random() * win.length - 0.1)];
        } else {
            setBackGround(colors[1]);
            out = lose[(int) (Math.random() * lose.length - 0.1)];
        }
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
        c.drawText("Game over", (int) (store.getScreenSize(0) / 2), (int) ((store.getScreenSize(1) - ts) / 2), paint);
        c.drawText("Score: " + store.getPlay().getScore(), (int) (store.getScreenSize(0) / 2), (int) ((store.getScreenSize(1) + ts) / 2), paint);
        paint.setTextSize(ts >> 1);
        c.drawText(out, (int) (store.getScreenSize(0) / 2), (int) ((store.getScreenSize(1) + ts * 4) / 2), paint);
    }
}
