package com.twoorthree.graceevangelical;

public class GraceEvent {

    String key;
    String contact;
    String desc;
    String elimDate;
    String endTime;
    String link;
    String name;
    String pic;
    String startTime;

    public GraceEvent(String key, String contact, String desc, String elimDate,
                      String endTime, String link, String name, String pic, String startTime) {
        this.key = key;
        this.contact = contact;
        this.desc = desc;
        this.elimDate = elimDate;
        this.endTime = endTime;
        this.link = link;
        this.name = name;
        this.pic = pic;
        this.startTime = startTime;
    }

    public String getKey() { return key; }

    public String getContact() { return contact; }

    public String getDesc() { return desc; }

    public String getElimDate() { return elimDate; }

    public String getEndTime() { return endTime; }

    public String getLink() { return link; }

    public String getName() { return name; }

    public String getPic() { return pic; }

    public String getStartTime() { return startTime; }
}
