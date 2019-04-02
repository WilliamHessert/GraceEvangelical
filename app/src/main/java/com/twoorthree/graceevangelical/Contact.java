package com.twoorthree.graceevangelical;

/**
 * Created by williamhessert on 8/18/18.
 */

public class Contact {

    String key;
    String email;
    String name;
    String number;
    String role;

    public Contact(String key, String email, String name, String number, String role) {
        this.key = key;
        this.email = email;
        this.name = name;
        this.number = number;
        this.role = role;
    }

    public String getKey() { return key; }

    public String getEmail() { return email; }

    public String getName() { return name; }

    public String getNumber() { return number; }

    public String getRole() { return role; }
}
