package com.azspc.stilnayagems.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;

import static com.azspc.stilnayagems.Main.store;
import static com.azspc.stilnayagems.Storage.*;
import static com.azspc.stilnayagems.draw.Placeholder.*;

public class Draw extends View {
    ArrayList<Placeholder> placeholders = new ArrayList<>();
    int background_color;

    public void addPlaceholder(Placeholder pl) {
        placeholders.add(pl);
    }

    public int getShowScreenType() {
        return show_screen_type;
    }

    public void setShowScreenType(int show_screen_type) {
        this.show_screen_type = show_screen_type;
    }

    int show_screen_type;

    public Draw(Context context, int bg_c) {
        super(context);
        show_screen_type = 1;
        background_color = bg_c;
        //score_height = pm.getInt("height_score", 0);
    }

    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);
        try {
            switch (show_screen_type) {
                case st_play:
                    store.getPlay().draw(c);
                    break;
                case st_over:
                    store.getOver().draw(c);
                    break;
            }
            drawPlaceholders(c);
            Thread.sleep(20);
            invalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void drawPlaceholders(Canvas c) {
        Paint p = new Paint();
        p.setTextSize((int) ((store.getScreenSize(0) - store.getScreenBounds() * 2) / 5));
        for (int i = 0; i < placeholders.size(); i++) {
            Placeholder pl = placeholders.get(i);
            int plus = pl.getTime() - pl.getI() * 2;
            p.setColor(Color.argb(255, 200 + plus, 150 + plus, 50 + plus));
            if (pl.getI() <= 0) placeholders.remove(i);
            if (pl.getType().equals(ph_from_to))
                c.drawText(pl.getText(), pl.getPosX(), pl.getPosY(), p);
            else if (pl.getType().equals(ph_from_to_img))
                c.drawBitmap(pl.getImg(), pl.getPosX(), pl.getPosY(), p);
        }
    }

}
