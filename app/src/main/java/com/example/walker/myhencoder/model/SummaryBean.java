package com.example.walker.myhencoder.model;

/**
 * @author Walker
 * @date on 2018/6/11 0011 下午 16:46
 * @email feitianwumu@163.com
 * @desc 概要数据结构
 */
public class SummaryBean {
    /**
     * class文件名
     */
    private String clazz;
    /**
     * 描述
     */
    private String desc;
    /**
     * 类型
     */
    private int type;

    public SummaryBean(String clazz, String desc, int type) {
        this.clazz = clazz;
        this.desc = desc;
        this.type = type;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
