package com.azspc.stilnayagems;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.azspc.stilnayagems.draw.Draw;
import com.azspc.stilnayagems.draw.Meet;
import com.azspc.stilnayagems.draw.Over;
import com.azspc.stilnayagems.draw.Play;

import java.util.HashMap;

import static com.azspc.stilnayagems.Level.all;
import static com.azspc.stilnayagems.Main.pm;
import static com.azspc.stilnayagems.Main.store;

public class Storage {
    public static final int //gem types
            gem_pull = -2,
            gem_ghost = -1,
            gem_null = 0,
            gem_aqua = 1,
            gem_blue = 2,
            gem_green = 3,
            gem_orange = 4,
            gem_pink = 5,
            gem_red = 6,
            gem_violet = 7,
            gem_yellow = 8;
    public static final int //screen types
            st_play = 1,
            st_over = 2,
            st_levels = 3,
            st_meet = 4,
            st_lvl_info = 5;
    private int screen_bounds, text_size;
    private double play_up_sound;
    private int[] screen_size, mPos, dPos, uPos;
    private boolean touch, sound_on;
    private HashMap<Integer, Bitmap> images = new HashMap<>();
    private SoundManager sound_manager;
    private Draw draw;
    private Play play;
    private Over over;
    private Meet meet;
    private Paint paint;


    Storage(Context c, int w, int h) {
        paint = new Paint();
        mPos = dPos = uPos = new int[]{0, 0};
        touch = false;
        sound_on = pm.getBoolean("sound_on", true);
        play_up_sound = 0;
        screen_size = new int[]{w, h};
        text_size = screen_size[0] / 12;
        screen_bounds = screen_size[0] / 32;
        sound_manager = new SoundManager(c);
    }

    void setFont(Typeface f) {
        paint.setTypeface(f);
    }

    void initPlay(Resources r) {
        play = new Play((int) (Math.random() * (all.split(";").length - 0.01)), 5, r, r.getColor(R.color.play_bg), paint);
    }

    void initDraw(Context c) {
        initPlay(c.getResources());
        meet = new Meet(c.getResources().getColor(R.color.meet_bg), paint, BitmapFactory.decodeResource(c.getResources(), R.drawable.logo));
        over = new Over(c.getResources().getColor(R.color.win_bg), c.getResources().getColor(R.color.lose_bg), paint);
        draw = new Draw(c);
    }

    public Meet getMeet() {
        return meet;
    }


    public void putImage(int id, Bitmap img) {
        images.put(id, img);
    }

    public SoundManager getSoundManager() {
        return sound_manager;
    }

    public Over getOver() {
        return over;
    }

    public void setOver(Over over) {
        this.over = over;
    }

    public void setPlayUpSound(double play_up_sound) {
        this.play_up_sound = play_up_sound;
    }

    public void setMovePos(int[] mPos) {
        this.mPos = mPos;
    }

    public void setDownPos(int[] dPos) {
        this.dPos = dPos;
    }

    public void setUpPos(int[] uPos) {
        this.uPos = uPos;
    }

    public void setIsTouch(boolean is_touch) {
        this.touch = is_touch;
    }

    public void setIsSoundOn(boolean is_sound_on) {
        this.sound_on = is_sound_on;
    }

    public int getTextSize() {
        return text_size;
    }

    public double getPlayUpSound() {
        return play_up_sound;
    }


    public int[] getPosM() {
        return mPos;
    }

    public int[] getPosD() {
        return dPos;
    }

    public int[] getPosU() {
        return uPos;
    }

    public boolean isTouch() {
        return touch;
    }


    public boolean isSoundOn() {
        return sound_on;
    }

    public Play getPlay() {
        return play;
    }

    public Bitmap getImage(int key) {
        return images.get(key);
    }

    public int getScreenSize(int i) {
        return screen_size[i];
    }

    public int getScreenBounds() {
        return screen_bounds;
    }

    public Draw getDraw() {
        return draw;
    }

    public void writeFile() {
        SharedPreferences.Editor save = pm.edit();
        // if (score > pm.getInt("height_score", 0)) save.putInt("height_score", s.score);
        save.putBoolean("sound_on", store.sound_on);
        save.apply();
    }

}
