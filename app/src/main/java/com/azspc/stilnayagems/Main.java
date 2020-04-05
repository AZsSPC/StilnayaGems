package com.azspc.stilnayagems;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.azspc.stilnayagems.draw.Draw;

public class Main extends AppCompatActivity {
    public static Storage store;
    public static SharedPreferences pm;
    TouchActions touch_actions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onResume();
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        store = new Storage(this, p.x, p.y);
        store.initDraw(this);
        touch_actions = new TouchActions();
        setContentView(store.getDraw());

    }

    public boolean onTouchEvent(MotionEvent event) {
        return touch_actions.onTouch(event);
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
