package com.azspc.stilnayagems;

import android.graphics.Canvas;

import com.azspc.stilnayagems.draw.Play;

import static com.azspc.stilnayagems.Main.store;
import static com.azspc.stilnayagems.Storage.*;

public class Gem {
    private int[] pos;
    private int move_role, image_id;
    private boolean selected;

    public Gem(int img_id, int x, int y, int move_role) {
        this.move_role = move_role;
        this.image_id = img_id;
        this.selected = false;
        this.pos = new int[]{x, y};
    }

    public void setMoveRole(int move_role) {
        this.move_role = move_role;
    }

    public int getImageID() {
        return image_id;
    }

    public void setImageID(int id) {
        image_id = id;
    }

    public void draw(Canvas c) {
        Play p = store.getPlay();
        if (isGemTouch(store.getPosD()[0], store.getPosD()[1], p) && !(image_id == gem_null || image_id == gem_ghost) && store.isTouch()) {
            if (store.getPlayUpSound() == 0.5) store.setPlayUpSound(1);
            selected = true;
            initGhosts(false);
            c.drawBitmap(store.getImage(image_id), (int) (store.getPosM()[0] - p.getPullSize() / 2), (int) (store.getPosM()[1] - p.getPullSize() / 2 - p.getPullSize() / 5), null);
        } else {
            if (selected) {
                putGem(p);
                p.checkTasks();
                p.checkCombos();
            }
            c.drawBitmap(store.getImage(image_id),
                    store.getScreenBounds() + p.getPullSize() * pos[0],
                    p.getSpaceForDesk() + p.getPullSize() * pos[1], null);
        }
    }

    private void putGem(Play p) {
        if (!store.isTouch()) {
            try {
                int[] putPos = new int[]{
                        (store.getPosU()[0] - store.getScreenBounds()) / p.getPullSize(),
                        (store.getPosU()[1] - p.getSpaceForDesk()) / p.getPullSize()};
                if (p.getGemsGhosts()[putPos[0]][putPos[1]]) {
                    p.setGem(putPos[0], putPos[1], image_id, move_role);
                    p.generateRandomGems(p.getGemSpawnCount());
                    image_id = gem_null;
                }
            } catch (Exception ignored) {
            }
            initGhosts(true);
        }
        selected = false;
    }

    private void initGhosts(boolean clear) {
        Gem[][] ga = store.getPlay().getGems();
        boolean[][] gg = store.getPlay().getGemsGhosts();
        int ix = pos[0];
        int iy = pos[1];
        if (clear || move_role == 0) for (int x = 0; x < ga.length; x++)
            for (int y = 0; y < ga.length; y++) gg[x][y] = false;
        else switch (move_role) {
            case 1: {
                for (int fx = ix - 1; fx > -1; fx--)
                    if (ga[fx][iy].getImageID() == gem_null) gg[fx][iy] = true;
                    else fx = -1;
                for (int fx = ix + 1; fx < ga.length; fx++)
                    if (ga[fx][iy].getImageID() == gem_null) gg[fx][iy] = true;
                    else fx = ga.length;
                for (int fy = iy - 1; fy > -1; fy--)
                    if (ga[ix][fy].getImageID() == gem_null) gg[ix][fy] = true;
                    else fy = -1;
                for (int fy = iy + 1; fy < ga.length; fy++)
                    if (ga[ix][fy].getImageID() == gem_null) gg[ix][fy] = true;
                    else fy = ga.length;
                break;
            }
            case 2: {
            }
            default:
                break;
        }
    }

    private boolean isGemTouch(int x, int y, Play p) {
        return x > pos[0] * p.getPullSize() + store.getScreenBounds() &&
                x < pos[0] * p.getPullSize() + store.getScreenBounds() + p.getPullSize() &&
                y > pos[1] * p.getPullSize() + p.getSpaceForDesk() &&
                y < pos[1] * p.getPullSize() + p.getSpaceForDesk() + p.getPullSize();
    }

}
