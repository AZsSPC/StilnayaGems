package com.azspc.stilnayagems;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class SoundManager extends SoundPool implements SoundPool.OnLoadCompleteListener {
    public int getS_score_up() {
        return s_score_up;
    }

    public int getS_lvl_start() {
        return s_lvl_start;
    }

    public int getS_gem_up() {
        return s_gem_up;
    }

    public int getS_gem_down() {
        return s_gem_down;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    private boolean enable;
    private int s_score_up, s_lvl_start, s_gem_up, s_gem_down;

    public SoundManager(Context c) {
        super(10, AudioManager.STREAM_MUSIC, 0);
        s_score_up = load(c, R.raw.score_up, 2);
        s_lvl_start = load(c, R.raw.level_start, 2);
        s_gem_up = load(c, R.raw.gem_up, 1);
        s_gem_down = load(c, R.raw.gem_down, 1);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        Log.println(Log.VERBOSE, "onLoadComplete", "sampleId = " + sampleId + ", status = " + status);
    }
}
