package com.azspc.stilnayagems.draw;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import static com.azspc.stilnayagems.Main.*;
import static com.azspc.stilnayagems.Storage.*;

public class Meet extends DrawAsset {
    Paint paint;
    int i = 0;
    Bitmap logo;

    public Meet(int cbg, Paint p, Bitmap lst) {
        super(cbg);
        paint = p;
        logo = Bitmap.createScaledBitmap(lst,
                (int) (store.getScreenSize(0) - store.getScreenBounds() * 2),
                (int) (store.getScreenSize(0) - store.getScreenBounds() * 2),
                false);
    }

    @Override
    public void draw(Canvas c) {
        super.draw(c);
        c.drawBitmap(logo, store.getScreenBounds(), (int) ((store.getScreenSize(1) - logo.getHeight()) / 2), paint);
        if (i++ >= 100) {
            store.getDraw().setShowScreenType(st_play);
            i = 0;
        }
    }
}
