package fi.jamk.sgkygolfcourses;

import java.io.Serializable;

/**
 * Created by Sabina on 29.10.2016.
 */
public class Course implements Serializable{
    String name;
    Double lat;
    Double lng;
    String address;
    String email;
    String phone;
    String photo;
    String url;
    String description;

    public Course(String name, Double lat, Double lng, String address, String email, String phone, String photo, String url, String description) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.lat = lat;
        this.lng = lng;
        this.url = url;
        this.description = description;
    }
}
