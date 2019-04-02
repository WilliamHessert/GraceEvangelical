package com.twoorthree.graceevangelical;

/**
 * Created by williamhessert on 12/6/18.
 */

public class Highlight {

    private String id, name, week, desc;

    public Highlight(String id, String name, String week, String desc) {
        this.id = id;
        this.name = name;
        this.week = week;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getWeek() {
        return week;
    }
}
