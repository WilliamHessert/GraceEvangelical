package com.twoorthree.graceevangelical;

/**
 * Created by williamhessert on 8/18/18.
 */

public class PrayerItem {

    String key;
    String name;
    String request;

    public PrayerItem(String key, String name, String request) {
        this.key = key;
        this.name = name;
        this.request = request;
    }

    public String getKey() { return key; }

    public String getName() { return name; }

    public String getRequest() { return request; }
}
