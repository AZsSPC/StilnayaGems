package com.azspc.stilnayagems;

import static com.azspc.stilnayagems.Storage.*;

public class Level {
    public static final String all = "" +
            "lv0=r:2,b:7;" +
            "lv1=r:5,g:3,b:7;" +
            "lv2=b:15,a:10;" +
            "lv3=y:9,o:9,r:9;" +
            "lv4=b:2,a:4;" +
            "lv5=v:16,b:4,a:7;" +
            "lv6=p:1;";//second this level IMPOSSIBLE!

    public static String getCFG(int id) {
        switch (id) {
            default:
                return " ";
            case gem_aqua:
                return "a";
            case gem_blue:
                return "b";
            case gem_green:
                return "g";
            case gem_orange:
                return "o";
            case gem_pink:
                return "p";
            case gem_red:
                return "r";
            case gem_violet:
                return "v";
            case gem_yellow:
                return "y";
        }
    }

    public static int getGFC(String str) {
        switch (str) {
            default:
                return gem_null;
            case "a":
                return gem_aqua;
            case "b":
                return gem_blue;
            case "g":
                return gem_green;
            case "o":
                return gem_orange;
            case "p":
                return gem_pink;
            case "r":
                return gem_red;
            case "v":
                return gem_violet;
            case "y":
                return gem_yellow;
        }
    }
}
