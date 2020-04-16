package com.azspc.stilnayagems;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

import static com.azspc.stilnayagems.Storage.*;

public class Main extends AppCompatActivity {
    public static Storage store;
    public static SharedPreferences pm;
    public static Lang lang;
    TouchAct tact = new TouchAct();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pm = PreferenceManager.getDefaultSharedPreferences(this);
        if (pm.getString("lang", Locale.getDefault().getLanguage()).equals("ru")
                || pm.getString("lang", Locale.getDefault().getLanguage()).equals("ua"))
            lang = new Lang(getResources().getString(R.string.lang_ru));
        else
            lang = new Lang(getResources().getString(R.string.lang_en));
        onResume();
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        store = new Storage(this, p.x, p.y);
        store.setFont(ResourcesCompat.getFont(this, R.font.dom));
        store.initDraw(this);
        setContentView(store.getDraw());
    }

    public boolean onTouchEvent(MotionEvent e) {
        tact.onMainTouch(e);
        switch (store.getDraw().getShowScreenType()) {
            default:
                return false;
            case st_play:
                return tact.onPlayTouch(e);
            case st_over:
                return tact.onOverTouch(e, getResources());
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
