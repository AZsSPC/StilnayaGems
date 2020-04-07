package com.azspc.stilnayagems.draw;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.azspc.stilnayagems.Gem;
import com.azspc.stilnayagems.R;
import com.azspc.stilnayagems.SoundManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static com.azspc.stilnayagems.Main.store;
import static com.azspc.stilnayagems.Levels.*;
import static com.azspc.stilnayagems.Storage.*;

public class Play extends DrawAsset {
    // <editor-fold desc="Get&Set">
    public int getPullSize() {
        return pull_size;
    }

    public int getSpaceForDesk() {
        return space_for_desk;
    }

    public int getScore() {
        return score;
    }

    public int getScoreHeight() {
        return score_height;
    }

    public int getGemSpawnCount() {
        return gem_spawn_count;
    }

    public boolean[][] getGemsGhosts() {
        return ghosts;
    }

    public boolean isChecked() {
        return checked;
    }

    public Gem[][] getGems() {
        return gems;
    }

    public void setGem(int x, int y, int id, int mr) {
        gems[x][y] = new Gem(id, x, y, mr);
    }

    public int getScore_height() {
        return score_height;
    }

    public int getPull_size() {
        return pull_size;
    }

    public int getSpace_for_desk() {
        return space_for_desk;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setScore_height(int score_height) {
        this.score_height = score_height;
    }

    public void setGem_spawn_count(int gem_spawn_count) {
        this.gem_spawn_count = gem_spawn_count;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    // </editor-fold>
    // <editor-fold desc="help to build">
    private void initPull() {
        this.pulls = Bitmap.createBitmap(pull_size * gems.length, pull_size * gems.length, Bitmap.Config.ARGB_8888);
        for (int x = 0; x < gems.length; x++)
            for (int y = 0; y < gems.length; y++)
                for (int i = 0; i < pull_size; i++)
                    for (int j = 0; j < pull_size; j++)
                        pulls.setPixel(x * pull_size + i, y * pull_size + j, store.getImage(gem_pull).getPixel(i, j));

    }

    private void initGemBitmaps(Resources r) {
        store.putImage(gem_pull, getOB(r, R.drawable.gem_pull));
        store.putImage(gem_null, getOB(r, R.drawable.gem_null));
        store.putImage(gem_ghost, getOB(r, R.drawable.gem_ghost));
        store.putImage(gem_aqua, getOB(r, R.drawable.gem_aqua));
        store.putImage(gem_blue, getOB(r, R.drawable.gem_blue));
        store.putImage(gem_green, getOB(r, R.drawable.gem_green));
        store.putImage(gem_orange, getOB(r, R.drawable.gem_orange));
        //  store.putImage(gem_pink, getOB(r, R.drawable.gem_pink));
        store.putImage(gem_red, getOB(r, R.drawable.gem_red));
        store.putImage(gem_violet, getOB(r, R.drawable.gem_magenta));
        store.putImage(gem_yellow, getOB(r, R.drawable.gem_yellow));
    }

    private Bitmap getOB(Resources r, int id) {
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(r, id), pull_size, pull_size, false);
    }

    private void firstGeneration(int size) {
        gems = new Gem[size][size];
        ghosts = new boolean[size][size];
        for (int x = 0; x < gems.length; x++)
            for (int y = 0; y < gems.length; y++)
                gems[x][y] = new Gem(gem_null, x, y, 0);
        generateRandomGems(gems.length * 2);
    }

    public void generateRandomGems(int num) {
        ArrayList<int[]> bufpos = new ArrayList<>();
        for (int x = 0; x < gems.length; x++)
            for (int y = 0; y < gems.length; y++)
                if (gems[x][y].getImageID() == gem_null) bufpos.add(new int[]{x, y});
        while (num > 0) {
            int id = getRandomGem();
            if (id != gem_null) {
                int i = (int) (Math.random() * bufpos.size());
                gems[bufpos.get(i)[0]][bufpos.get(i)[1]].setImageID(id);
                gems[bufpos.get(i)[0]][bufpos.get(i)[1]].setMoveRole(1);
                bufpos.remove(i);
                num--;
            }
        }
    }

    private int getRandomGem() {
        switch ((int) (Math.random() * 8 * gems.length)) {
            default:
                return gem_null;
            case 0:
                return gem_green;
            case 1:
                return gem_blue;
            case 2:
                return gem_violet;
            case 3:
                return gem_red;
            case 4:
                return gem_aqua;
            case 5:
                return gem_orange;
            case 6:
                return gem_yellow;
            //case 7:
            //  return gem_pink;
        }
    }

    // </editor-fold>
    HashMap<String, Integer> gemDestroyed = new HashMap<>();
    String needs = "";
    private int pull_size, space_for_desk, score, score_height, gem_spawn_count, level_id;
    private boolean[][] ghosts;
    private boolean checked;
    private Bitmap pulls;
    private Gem[][] gems;

    public Play(int lvl_num, int pulls, Resources r, int cbg) {
        super(cbg);
        level_id = lvl_num;
        gem_spawn_count = 1;
        firstGeneration(pulls);
        pull_size = (store.getScreenSize(0) - store.getScreenBounds() * 2) / gems.length;
        space_for_desk = store.getScreenSize(1) - (store.getScreenBounds() + pull_size * gems.length);
        initGemBitmaps(r);
        initPull();
        for (String s : all.split("lv" + level_id + "=")[1].substring(0, all.split("lv" + level_id + "=")[1].indexOf(";")).split(",")) {
            gemDestroyed.put(s.split(":")[0], 0);
            needs += ", " + s;
        }
        needs = needs.substring(2);
    }

    public void draw(Canvas c) {
        super.draw(c);
        drawHints(c);
        drawPulls(c);
        drawGhosts(c);
        drawGemstone(c);
    }

    private void drawGhosts(Canvas c) {
        Bitmap ghost = store.getImage(gem_ghost);
        if (store.is_touch()) for (int x = 0; x < gems.length; x++)
            for (int y = 0; y < gems.length; y++)
                if (ghosts[x][y])
                    c.drawBitmap(ghost, store.getScreen_bounds() + pull_size * x, space_for_desk + pull_size * y, null);

    }

    private void drawPulls(Canvas c) {
        c.drawBitmap(pulls, store.getScreen_bounds(), space_for_desk, null);
    }

    private void drawGemstone(Canvas c) {
        for (Gem[] gb : gems) for (Gem g : gb) g.draw(c);
    }

    private void drawHints(Canvas c) {
        Paint p = new Paint();
        int ts = store.getText_size();
        int sb = store.getScreen_bounds();
        p.setTextSize(ts);
        c.drawText("Score: " + score, sb, sb + ts, p);
        c.drawText("Height score: " + score_height, sb, sb + ts * 2, p);
        c.drawText("Checked: " + checked, sb, sb + ts * 3, p);
        c.drawText("Gem spawn count: " + gem_spawn_count, sb, sb + ts * 4, p);
        p.setColor(Color.GRAY);
        p.setTextSize(ts / 3 * 2);
        c.drawText(" Application is under development.", sb, sb + ts * 6 , p);
        p.setTextSize(ts );
        p.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.BLACK);
        c.drawText("You need: " + needs, store.getScreenSize(0) / 2, space_for_desk - ts, p);
    }

    private boolean checkOverPlaced() {
        for (Gem[] gb : gems) for (Gem g : gb) if (g.getImageID() == gem_null) return true;
        store.getDraw().setShowScreenType(2);
        store.getOver().setFinType(false);
        return false;
    }

    public void checkTasks() {
        boolean tasksCompleted = true;
        for (String task : all.split("lv" + level_id + "=")[1].substring(0, all.split("lv" + level_id + "=")[1].indexOf(";")).split(",")) {
            //Log.e("lvl-" + level_id + " task", task + " | " + gemDestroyed.get(task.split(":")[0]));
            tasksCompleted &= Integer.parseInt(task.split(":")[1]) <= gemDestroyed.get(task.split(":")[0]);
        }

        if (tasksCompleted) {
            store.getDraw().setShowScreenType(2);
            store.getOver().setFinType(true);
        }
    }

    public void checkCombos() {
        if (checkOverPlaced()) {
            HashSet<int[]> to_del = new HashSet<>();
            for (int x = 0; x < gems.length; x++)
                for (int y = 0; y < gems.length; y++)
                    if (!(gems[x][y].getImageID() == gem_null || gems[x][y].getImageID() == gem_ghost)) {
                        int img_id = gems[x][y].getImageID();
                        try {
                            if (img_id == gems[x + 1][y].getImageID() && img_id == gems[x + 2][y].getImageID())
                                for (int i = 0; i < 3; i++) to_del.add(new int[]{x + i, y});
                        } catch (Exception ignored) {
                        }
                        try {
                            if (img_id == gems[x][y + 1].getImageID() && img_id == gems[x][y + 2].getImageID())
                                for (int i = 0; i < 3; i++) to_del.add(new int[]{x, y + i});
                        } catch (Exception ignored) {
                        }
                    }
            if (to_del.size() > 0) {
                SoundManager sm = store.getSound_manager();
                for (int[] i : to_del) {
                    store.getDraw().addPlaceholder(new Placeholder(store.getImage(gems[i[0]][i[1]].getImageID()),
                            store.getScreenBounds() + pull_size * i[0], space_for_desk + pull_size * i[1], 0, 0, 32));
                    try {
                        String id = getCFG(gems[i[0]][i[1]].getImageID());
                        gemDestroyed.put(id, gemDestroyed.get(id) + 1);
                    } catch (Exception ignored) {
                    }
                    gems[i[0]][i[1]].setImageID(gem_null);
                }
                String text = "+" + to_del.size();
                store.getDraw().addPlaceholder(new Placeholder(text,
                        store.getScreenSize(0) - store.getScreenBounds() - (store.getScreenSize(0) - store.getScreenBounds() * 2) / 5 * text.length(),
                        store.getScreenSize(1) / 2 - pull_size, 0, 0, 32));
                score += to_del.size();
                if (sm.isEnable()) sm.play(sm.getS_score_up(), 1, 1, 0, 0, 1.2f);
// if (score > score_height) {
//                    if (sm.isEnable()) sm.play(sm.getS_score_up(), 1, 1, 0, 1, 2f);
//                    store.getDraw().addPlaceholder(new Placeholder("New height score!", screen_size[0] / 2, screen_size[1] / 2, screen_size[0] / 4, screen_size[1] / 4, 32, this));
//                    score_height = score;
//                    writeFile(this);
//                }
            }
            checked = true;
        }
    }

}
