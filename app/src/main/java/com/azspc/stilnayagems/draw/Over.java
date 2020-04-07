package com.azspc.stilnayagems.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

import org.w3c.dom.Text;

import static com.azspc.stilnayagems.Main.store;

public class Over extends DrawAsset {
    int[] colors;
    String out;
    String[] win = {"You great!", "Success!", "Nice score!", "Good job", "You win!"};
    String[] lose = {"Try again...", "Lose", "Bad score", "You can replay it!"};

    public void setFinType(boolean torf) {
        if (torf) {
            setBackGround(colors[0]);
            out = win[(int) (Math.random() * win.length - 0.1)];
        } else {
            setBackGround(colors[1]);
            out = lose[(int) (Math.random() * lose.length - 0.1)];
        }
    }

    public Over(int w_c, int l_c) {
        super(0);
        out = "You great!";
        colors = new int[]{w_c, l_c};
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        Paint p = new Paint();
        int ts = store.getText_size() * 2;
        p.setTextSize(ts);
        p.setTextAlign(Paint.Align.CENTER);
        c.drawText("Game over", store.getScreenSize(0) / 2, (store.getScreenSize(1) - ts) / 2, p);
        c.drawText("Score: " + store.getPlay().getScore(), store.getScreenSize(0) / 2, (store.getScreenSize(1) + ts) / 2, p);
        p.setTextSize(ts / 2);
        c.drawText(out, store.getScreenSize(0) / 2, (store.getScreenSize(1) + ts * 4) / 2, p);
    }
}
