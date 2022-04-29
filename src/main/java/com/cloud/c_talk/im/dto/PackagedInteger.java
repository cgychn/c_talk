package com.cloud.c_talk.im.dto;

public class PackagedInteger {
    private int i = 0;

    public int getI() {
        return i;
    }

    public synchronized void add() {
        i ++;
    }

    public synchronized void reset () {
        i = 0;
    }
}
