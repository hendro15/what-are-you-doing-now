package com.example.sonic.whatdoyoudo.model;

/**
 * Created by sonic on 02/04/2017.
 */

public class MenuElement {

    String title;
    int icon;

    public MenuElement(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }
}
