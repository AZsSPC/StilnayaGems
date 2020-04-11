package com.azspc.stilnayagems;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.preference.PreferenceManager;

import com.azspc.stilnayagems.draw.Draw;
import com.azspc.stilnayagems.draw.Over;
import com.azspc.stilnayagems.draw.Play;

import java.util.HashMap;

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
    private boolean touch, game_over, sound_on;
    private HashMap<Integer, Bitmap> images = new HashMap<>();
    private SoundManager sound_manager;
    private Draw draw;
    private Play play;
    private Over over;

    Storage(Context c, int w, int h) {
        mPos = dPos = uPos = new int[]{0, 0};
        pm = PreferenceManager.getDefaultSharedPreferences(c);
        game_over = false;
        touch = false;
        sound_on = pm.getBoolean("sound_on", true);
        play_up_sound = 0;
        screen_size = new int[]{w, h};
        text_size = screen_size[0] / 12;
        screen_bounds = screen_size[0] / 32;
        sound_manager = new SoundManager(c);
    }

    public void initDraw(Context c) {
        preInitDraw(c);
        draw = new Draw(c, Color.CYAN);
    }

    public void preInitDraw(Context c) {
        Paint p = new Paint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            p.setTypeface(c.getResources().getFont(R.font.dom));
        play = new Play((int) (Math.random() * 6), 5, c.getResources(), c.getResources().getColor(R.color.play_bg), p);
        over = new Over(c.getResources().getColor(R.color.win_bg), c.getResources().getColor(R.color.lose_bg), p);
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

    public void setIs_touch(boolean is_touch) {
        this.touch = is_touch;
    }

    public void setIsGameOver(boolean is_game_over) {
        this.game_over = is_game_over;
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

    public boolean isGameOver() {
        return game_over;
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
        save.commit();
    }

}
