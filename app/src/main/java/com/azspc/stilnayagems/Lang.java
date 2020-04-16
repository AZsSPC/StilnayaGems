package com.azspc.stilnayagems;

import android.util.Log;

import java.util.HashMap;

public class Lang {
    HashMap<String, String> issues = new HashMap<>();

    public Lang(String lang) {
        setLang(lang);
    }

    public void setLang(String lang) {
        for (String issue : lang.split(";"))
            issues.put(issue.split("=")[0].replaceAll(" ",""), issue.split("=")[1]);
        }


    public String translate(String in) {
        try {
            return issues.get(in);
        } catch (Exception ignored) {
        }
        return "?error?";
    }
}
