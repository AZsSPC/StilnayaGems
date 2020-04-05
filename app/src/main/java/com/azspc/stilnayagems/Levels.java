package com.azspc.stilnayagems;

import static com.azspc.stilnayagems.Storage.*;

public class Levels {
    public static final String all = "" +
            "lv0=r:1;" +
            "lv1=r:5,g:3;" +
            "lv2=b:2,a:4;";

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
