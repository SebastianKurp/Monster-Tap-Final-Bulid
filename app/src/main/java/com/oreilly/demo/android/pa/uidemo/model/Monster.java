package com.oreilly.demo.android.pa.uidemo.model;

/**
 * Created by Sebastian on 4/27/2017.
 */

public final class Monster{

    private final float x, y; //graph cordinates
    private final int radius;
    private final boolean protectedstate;

    /**
     * @param x x-axis
     * @param y y-axis
     * @param radius monster radius. should be diameter but i'm too lazy to change it all
     */

    public Monster(final float x, final float y, final int radius, boolean protectedstate) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.protectedstate = protectedstate;
    }

    public float getX() { //getter method for x
        return x;
    }

    public float getY() {// getter method for y
        return y;
    }

    public int getRadius() { //getter method for radius
        return radius;
    }

    public boolean getProtectedState(){ //getter method for protectedstate
        return protectedstate;
    }

}
