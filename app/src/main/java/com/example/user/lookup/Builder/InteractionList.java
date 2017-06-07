package com.example.user.lookup.Builder;

/**
 * Created by USER on 31/5/2017.
 */

public class InteractionList {

    public String name;
    public String icon;
    public String rating;
    public String address;
    public String lat;
    public String lng;
    public String photo_url;

    public InteractionList(String name, String icon, String rating, String address, String lat, String lng, String photo_url){

        this.name = name;
        this.icon = icon;
        this.rating = rating;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.photo_url = photo_url;

    }
}
