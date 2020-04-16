package com.azspc.stilnayagems;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.MotionEvent;

import static com.azspc.stilnayagems.Main.store;
import static com.azspc.stilnayagems.Storage.*;

public class TouchAct {
    void onMainTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                store.setDownPos(new int[]{(int) event.getX(), (int) event.getY()});
                store.setIsTouch(true);
                return;
            case MotionEvent.ACTION_UP:
                store.setUpPos(new int[]{(int) event.getX(), (int) event.getY()});
                store.setIsTouch(false);
                return;
            case MotionEvent.ACTION_MOVE:
                store.setMovePos(new int[]{(int) event.getX(), (int) event.getY()});
                return;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_CANCEL:
            default:
        }
    }

    boolean onPlayTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                store.setPlayUpSound(0.5);
                return true;
            case MotionEvent.ACTION_UP:
                store.getPlay().setChecked(false);
                if (store.getPlayUpSound() == 1 && store.isSoundOn()) {
                    store.getSoundManager().play(store.getSoundManager().getSoundGemDown(), 0.2f, 0.2f, 0, 0, 2.8f);
                    store.setPlayUpSound(0);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_CANCEL:
            default:
                return false;
        }
    }

    boolean onOverTouch(MotionEvent event, Resources r) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                store.initPlay(r);
                store.getDraw().setShowScreenType(st_play);
                return true;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_CANCEL:
            default:
                return false;
        }
    }
}
