package com.azspc.stilnayagems;

import static com.azspc.stilnayagems.Storage.*;

public class Level {
    public static final String all = "" +
            "lv0=gg:16,gr:8,gb:4,active:gg:gr:gb:gb,spawn:1;" +
            "lv1=gv:32,active:gg:gy:gv:gy:gv,spawn:1;" +
            "lv2=ga:16,gb:16,gv:16,active:ga:gb:gv,spawn:1;" +
            "lv3=gr:40,gy:40,active:gr:gy,spawn:2;" +
            "lv4=gb:218,active:gb:ga:ga,spawn:3;" +
            "lv5=go:20,ga:32,active:go:gp:ga:gg:gg,spawn:1;" +
            "lv6=gg:20,gb:15,active:gg:gp:gy:gv:gb,spawn:1;";

    public static String getCFG(int id) {
        switch (id) {
            default:
                return "gn";
            case gem_aqua:
                return "ga";
            case gem_blue:
                return "gb";
            case gem_green:
                return "gg";
            case gem_orange:
                return "go";
            case gem_pink:
                return "gp";
            case gem_red:
                return "gr";
            case gem_violet:
                return "gv";
            case gem_yellow:
                return "gy";
        }
    }

    public static int getGFC(String str) {
        switch (str) {
            default:
                return gem_null;
            case "ga":
                return gem_aqua;
            case "gb":
                return gem_blue;
            case "gg":
                return gem_green;
            case "go":
                return gem_orange;
            case "gp":
                return gem_pink;
            case "gr":
                return gem_red;
            case "gv":
                return gem_violet;
            case "gy":
                return gem_yellow;
        }
    }
}
