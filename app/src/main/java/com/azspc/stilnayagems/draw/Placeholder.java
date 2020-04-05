package com.azspc.stilnayagems.draw;

import android.graphics.Bitmap;

public class Placeholder extends Thread {
    public static final String
            ph_from_to = "from_to",
            ph_from_to_img = "from_to_img";
    private int time, posX, posY, posX_end, posY_end, i, move[];
    private String text, type;
    private Bitmap img;

    public Placeholder(Bitmap image, int pX, int pY, int pXf, int pYf, int time) {
        this.type = ph_from_to_img;
        this.img = image;
        this.posX = pX;
        this.posY = pY;
        this.posX_end = pXf;
        this.posY_end = pYf;
        this.time = time;
        this.move = new int[]{(pXf - pX) / time, (pYf - pY) / time};
        this.start();
    }

    public Placeholder(String text, int pX, int pY, int pXf, int pYf, int time) {
        this.type = ph_from_to;
        this.text = text;
        this.posX = pX;
        this.posY = pY;
        this.posX_end = pXf;
        this.posY_end = pYf;
        this.time = time;
        this.move = new int[]{(pXf - pX) / (time), (pYf - pY) / (time)};
        this.start();
    }

    @Override
    public void run() {
        switch (type) {
            case ph_from_to:
            case ph_from_to_img: {
                for (i = time; i >= 0; i--) {
                    this.posX += move[0];
                    this.posY += move[1];
                    sl(24);
                }
                this.posX = posX_end;
                this.posY = posY_end;
                break;
            }
        }
        i = -1;
    }

    public int getI() {
        return i;
    }

    public String getType() {
        return type;
    }

    public Bitmap getImg() {
        return img;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    void sl(int t) {
        try {
            sleep(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
