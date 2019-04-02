package com.twoorthree.graceevangelical;

/**
 * Created by williamhessert on 9/3/18.
 */

public class OfferItem {

    private String oid, name, pic, desc, contact, dateTime, link;

    public OfferItem(String oid, String name,
                     String pic, String desc, String contact, String dateTime, String link) {
        this.oid = oid;
        this.name = name;
        this.pic = pic;
        this.desc = desc;
        this.contact = contact;
        this.dateTime = dateTime;
        this.link = link;
    }

    public String getId() {
        return oid;
    }

    public String getName() { return name; }

    public String getDesc() { return desc; }

    public String getPic() { return pic; }

    public String getContact() { return contact; }

    public String getDateTime() { return dateTime; }

    public String getLink() { return link; }
}
