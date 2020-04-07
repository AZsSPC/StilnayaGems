package com.azspc.stilnayagems;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import static com.azspc.stilnayagems.Storage.st_over;

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
                store.setPlay_up_sound(0.5);
                store.setMovePos(new int[]{(int) event.getX(), (int) event.getY()});
                store.setDownPos(new int[]{(int) event.getX(), (int) event.getY()});
                store.setUpPos(new int[]{0, 0});
                store.setIs_touch(true);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                if (store.getDraw().getShowScreenType() == st_over) recreate();
                store.getPlay().setChecked(false);
                if (store.is_game_over()) store.writeFile();
                if (store.getPlay_up_sound() == 1 && store.is_sound_on()) {
                    store.getSound_manager().play(store.getSound_manager().getS_gem_down(), 0.4f, 0.4f, 0, 0, 2.8f);
                    store.setPlay_up_sound(0);
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
        if (Build.VERSION.SDK_INT < 19)
            getWindow().getDecorView().setSystemUiVisibility(View.GONE);
        else
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
