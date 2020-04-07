package com.azspc.stilnayagems;

import static com.azspc.stilnayagems.Storage.*;

public class Levels {
    public static final String all = "" +
            "lv0=r:2,b:7;" +
            "lv1=r:5,g:3,b:7;" +
            "lv2=p:1;" +
            "lv3=b:15,a:10;" +
            "lv4=y:9,o:9,r:9;" +
            "lv5=b:2,a:4;" +
            "lv6=v:16,b:4,a:7;";

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
}
