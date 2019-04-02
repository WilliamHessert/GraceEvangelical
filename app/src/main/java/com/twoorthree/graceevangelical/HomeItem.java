package com.twoorthree.graceevangelical;

import android.graphics.drawable.Drawable;

/**
 * Created by williamhessert on 9/1/18.
 */

public class HomeItem {

    String name;
    String desc;
    Drawable pic;

    public HomeItem (String name, String desc, Drawable pic) {
        this.name = name;
        this.desc = desc;
        this.pic = pic;
    }

    public String getName() { return name; }

    public String getDesc() { return desc; }

    public Drawable getPic() { return pic; }
}
