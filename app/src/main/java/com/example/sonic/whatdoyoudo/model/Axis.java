package com.example.sonic.whatdoyoudo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sonic on 02/04/2017.
 */

public class Axis {

    private float x, y, z;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    private ArrayList<Float> xList = new ArrayList<>();
    private ArrayList<Float> yList = new ArrayList<>();
    private ArrayList<Float> zList = new ArrayList<>();

    public ArrayList<Float> getxList() {
        return xList;
    }

    public ArrayList<Float> getyList() {
        return yList;
    }

    public ArrayList<Float> getzList() {
        return zList;
    }

    public Axis(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        xList.add(x);
        yList.add(y);
        zList.add(z);
    }



}
