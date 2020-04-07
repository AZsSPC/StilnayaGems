package com.azspc.stilnayagems;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.azspc.stilnayagems.draw.Placeholder;

import static com.azspc.stilnayagems.Storage.st_over;
import static com.azspc.stilnayagems.Storage.st_play;

public class Main extends AppCompatActivity {
    public static Storage store;
    public static SharedPreferences pm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onResume();
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        store = new Storage(this, p.x, p.y);
        store.initDraw(this);
        setContentView(store.getDraw());

    }

    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                store.setPlayUpSound(0.5);
                store.setMovePos(new int[]{(int) event.getX(), (int) event.getY()});
                store.setDownPos(new int[]{(int) event.getX(), (int) event.getY()});
                store.setUpPos(new int[]{0, 0});
                store.setIs_touch(true);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                if (store.getDraw().getShowScreenType() == st_over) {
                    store.preInitDraw(this);
                    store.getDraw().setShowScreenType(st_play);
                    store.getDraw().addPlaceholder(new Placeholder("Enjoy again", store.getScreenSize(0) / 3, store.getScreenSize(1) / 2, store.getScreenSize(0) / 3, 0, 50));
                }
                store.getPlay().setChecked(false);
                if (store.isGameOver()) store.writeFile();
                if (store.getPlayUpSound() == 1 && store.isSoundOn()) {
                    store.getSoundManager().play(store.getSoundManager().getSoundGemDown(), 0.5f, 0.5f, 0, 0, 2.8f);
                    store.setPlayUpSound(0);
                }
                store.setDownPos(new int[]{0, 0});
                store.setUpPos(new int[]{(int) event.getX(), (int) event.getY()});
                store.setIs_touch(false);
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                store.setMovePos(new int[]{(int) event.getX(), (int) event.getY()});
                return true;
            }
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_CANCEL:
            default:
                return false;
        }
    }

    protected void onResume() {
        super.onResume();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        try {
            store.getSoundManager().play(store.getSoundManager().getSoundLvlStart(), 0.3f, 0.3f, 0, 0, 1);
        } catch (Exception ignored) {
        }
    }
}
