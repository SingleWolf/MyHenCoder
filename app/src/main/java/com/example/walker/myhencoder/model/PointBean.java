package com.example.walker.myhencoder.model;

/**
 * @author Walker
 * @date on 2018/6/14 0014 下午 14:38
 * @email feitianwumu@163.com
 * @desc 坐标点
 */
public class PointBean {
    private float x;
    private float y;

    public PointBean(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
