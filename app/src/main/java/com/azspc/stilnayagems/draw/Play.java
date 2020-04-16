package com.azspc.stilnayagems.draw;

import android.content.SharedPreferences;
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

import static com.azspc.stilnayagems.Level.getGFC;
import static com.azspc.stilnayagems.Main.lang;
import static com.azspc.stilnayagems.Main.pm;
import static com.azspc.stilnayagems.Main.store;
import static com.azspc.stilnayagems.Level.*;
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

    public int getScoreBest() {
        return score_best;
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


    public void setScore(int score) {
        this.score = score;
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
        store.putImage(gem_pink, getOB(r, R.drawable.gem_pink));
        store.putImage(gem_red, getOB(r, R.drawable.gem_red));
        store.putImage(gem_violet, getOB(r, R.drawable.gem_violet));
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
        try {
            return active_gems[(int) (Math.random() * active_gems.length + 1)];
        } catch (Exception ignored) {
        }
        return gem_null;
    }

    // </editor-fold>
    HashMap<String, Integer> gemDestroyed = new HashMap<>();
    private String gem_needs, level_id;
    private int pull_size, space_for_desk, score, score_best, gem_spawn_count;
    private boolean[][] ghosts;
    private boolean checked;
    private Bitmap pulls;
    private Gem[][] gems;
    private Paint paint;
    private int[] active_gems;

    public Play(Object lvl_id, int pulls, Resources r, int cbg, Paint p) {
        super(cbg);
        level_id = lvl_id + "";
        gem_needs = "";
        score_best = pm.getInt("lv" + level_id + "score", 0);
        for (String sector : all.split("lv" + level_id + "=")[1].substring(0, all.split("lv" + level_id + "=")[1].indexOf(";")).split(",")) {
            String[] s = sector.split(":");
            if (getGFC(s[0] + "") != gem_null) {
                gem_needs += "," + sector;
                gemDestroyed.put(s[0], 0);
            } else if (s[0].equals("active")) {
                active_gems = new int[s.length];
                for (int i = 0; active_gems.length > i; i++) active_gems[i] = getGFC(s[i]);
            } else if (s[0].equals("spawn")) {
                gem_spawn_count = Integer.parseInt(s[1]);
            } else if (s[0].equals("ag")) {
                active_gems = new int[s.length];
                for (int i = 0; active_gems.length > i; i++) active_gems[i] = getGFC(s[i]);
            }
        }
        gem_needs = gem_needs.substring(1);
        paint = p;
        firstGeneration(pulls);
        pull_size = (store.getScreenSize(0) - store.getScreenBounds() * 2) / gems.length;
        space_for_desk = store.getScreenSize(1) - (store.getScreenBounds() + pull_size * gems.length);
        initGemBitmaps(r);
        initPull();
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
        if (store.isTouch()) for (int x = 0; x < gems.length; x++)
            for (int y = 0; y < gems.length; y++)
                if (ghosts[x][y])
                    c.drawBitmap(ghost, store.getScreenBounds() + pull_size * x, space_for_desk + pull_size * y, null);

    }

    private void drawPulls(Canvas c) {
        c.drawBitmap(pulls, store.getScreenBounds(), space_for_desk, null);
    }

    private void drawGemstone(Canvas c) {
        for (Gem[] gb : gems) for (Gem g : gb) g.draw(c);
    }

    private void drawHints(Canvas c) {
        int ts = store.getTextSize();
        int sb = store.getScreenBounds();
        paint.setTextAlign(Paint.Align.CENTER);
        int[] needs_rect = {sb, sb * 2 + ts * 2, store.getScreenSize(0) - sb, space_for_desk - sb};
        paint.setTextSize(ts);
        paint.setColor(Color.argb(255, 255, 220, 200));
        c.drawRect(needs_rect[0], needs_rect[1], needs_rect[2], needs_rect[3], paint);
        paint.setColor(Color.BLACK);
        c.drawText(lang.translate("level") + ": " + level_id + " | " + lang.translate("score") + ": " + score, store.getScreenSize(0) >> 1, sb + ts, paint);
        c.drawText(lang.translate("score_best") + ": " + score_best, store.getScreenSize(0) >> 1, sb + ts * 2, paint);
        Bitmap img;
        int i = 0;
        paint.setTextAlign(Paint.Align.LEFT);
        for (String id : gem_needs.split(",")) {
            int n[] = {gemDestroyed.get(id.split(":")[0]), Integer.parseInt(id.split(":")[1])};
            img = store.getImage(getGFC(id.split(":")[0]));
            c.drawBitmap(img, (int) (store.getScreenSize(0) / 2) - img.getWidth(), needs_rect[1] + img.getHeight() * i, paint);
            c.drawText((n[0] >= n[1]) ? lang.translate("completed") : n[0] + " " + lang.translate("of") + " " + n[1],
                    (int) (store.getScreenSize(0) / 2), (float) (needs_rect[1] * 1.5 + img.getHeight() * i++), paint);

        }
    }

    private boolean checkOverPlaced() {
        for (Gem[] gb : gems) for (Gem g : gb) if (g.getImageID() == gem_null) return true;
        if (score_best < score) pm.edit().putInt("lv" + level_id + "score", score).apply();
        store.getDraw().setShowScreenType(st_over);
        store.getOver().setFinType(false);
        return false;
    }

    public void checkTasks() {
        boolean tasksCompleted = true;
        for (String task : gem_needs.split(","))
            tasksCompleted &= Integer.parseInt(task.split(":")[1])
                    <= gemDestroyed.get(task.split(":")[0]);
        //Log.e("lvl-" + level_id + " task", task + " | " + gemDestroyed.get(task.split(":")[0]));

        if (tasksCompleted) {
            if (score_best < score) pm.edit().putInt("lv" + level_id + "score", score).apply();
            store.getDraw().setShowScreenType(2);
            store.getOver().setFinType(true);
        }
    }

    public void checkCombos() {
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
            SoundManager sm = store.getSoundManager();
            for (int[] i : to_del) {
                try {
                    store.getDraw().addPlaceholder(new Placeholder(
                            store.getImage(gems[i[0]][i[1]].getImageID()),
                            store.getScreenBounds() + pull_size * i[0],
                            space_for_desk + pull_size * i[1],
                            0, 0, 32));
                    String id = getCFG(gems[i[0]][i[1]].getImageID());
                    gemDestroyed.put(id, 1 + gemDestroyed.get(id));
                } catch (Exception ignored) {
                }
                gems[i[0]][i[1]].setImageID(gem_null);
            }
            score += (int) (to_del.size() * (Math.random() * 3 + 7));
           /*  int td = (int) (to_del.size() * (Math.random() * 3 + 7));
            String text = "+" + td;
            store.getDraw().addPlaceholder(new Placeholder(text,
                    store.getScreenSize(0) - store.getScreenBounds() - (store.getScreenSize(0) - store.getScreenBounds() * 2) / 5 * text.length(),
                    store.getScreenSize(1) / 2 - pull_size, 0, 0, 32));*/
            if (sm.isEnable()) sm.play(sm.getSoundScoreUp(), 1, 1, 0, 0, 1.2f);
           /*if (score > score_height) {
                  if (sm.isEnable()) sm.play(sm.getS_score_up(), 1, 1, 0, 1, 2f);
                   store.getDraw().addPlaceholder(new Placeholder("New height score!", screen_size[0] / 2, screen_size[1] / 2, screen_size[0] / 4, screen_size[1] / 4, 32, this));
                   score_height = score;
                  writeFile(this);
               }*/
        }
        checked = true;
        checkOverPlaced();
    }

}
