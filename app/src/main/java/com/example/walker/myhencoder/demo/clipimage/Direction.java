package com.example.walker.myhencoder.demo.clipimage;

/**
 * @Author Walker
 * @Date 2019-12-12 10:09
 * @Summary 方向控制
 */
public class Direction {
    public static final int LEFT = 1;
    public static final int TOP = 2;
    public static final int RIGHT = 3;
    public static final int BOTTOM = 4;

    public static final int DEFAULT = TOP;
    private int mDirection = DEFAULT;

    public Direction() {
    }

    public Direction(int orientation) {
        mDirection = calculateDirection(orientation);
    }

    private int calculateDirection(int orientation) {
        int direction = DEFAULT;
        if (315 <= orientation || orientation <= 45) {
            direction = TOP;
        } else if (45 < orientation && orientation < 135) {
            direction = LEFT;
        } else if (135 <= orientation && orientation <= 225) {
            direction = BOTTOM;
        } else if (225 < orientation && orientation < 315) {
            direction = RIGHT;
        }
        return direction;
    }

    public int getDirection() {
        return mDirection;
    }

    public void setLeft() {
        mDirection = LEFT;
    }

    public void setTop() {
        mDirection = TOP;
    }

    public void setRight() {
        mDirection = RIGHT;
    }

    public void setBottom() {
        mDirection = BOTTOM;
    }

    public void setOrientation(int orientation) {
        mDirection = calculateDirection(orientation);
    }
}
