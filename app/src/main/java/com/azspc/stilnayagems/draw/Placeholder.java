package com.azspc.stilnayagems.draw;

import android.graphics.Bitmap;

public class Placeholder extends Thread {
    static final String
            ph_from_to = "from_to",
            ph_from_to_img = "from_to_img";
    private int time, posX, posY, posX_end, posY_end, i;
    private int[] move;
    private String text, type;
    private Bitmap img;

    Placeholder(Bitmap image, int pX, int pY, int pXf, int pYf, int time) {
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
                    stay();
                }
                this.posX = posX_end;
                this.posY = posY_end;
                break;
            }
        }
        i = -1;
    }

    int getI() {
        return i;
    }

    String getType() {
        return type;
    }

    Bitmap getImg() {
        return img;
    }

    int getPosX() {
        return posX;
    }

    int getPosY() {
        return posY;
    }

    int getTime() {
        return time;
    }

    String getText() {
        return text;
    }

    private void stay() {
        try {
            sleep(24);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
