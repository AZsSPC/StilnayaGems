package com.azspc.stilnayagems;

import android.view.*;

import static com.azspc.stilnayagems.Main.store;

 class TouchActions {
     boolean onTouch(MotionEvent event) {
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
}
