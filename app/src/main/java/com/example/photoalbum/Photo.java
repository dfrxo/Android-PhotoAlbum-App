package com.example.photoalbum;


import java.io.Serializable;
import android.net.Uri;

import java.util.ArrayList;
import java.util.HashSet;
/**
 * Contains photo data. Stores info about a photo: name, path, caption, data, and tags;
 * @author David Fink
 * @author Tanesha More
 */
public class Photo implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private transient Uri uri;
    private Tag location;
    private Tag person;
    private String uriString;

    /**
     * Constructs a Photo object with the specified file path.
     * Creates a new hashSet and updates the data for the photo.
     *
     * @param uri The Uri path of the photo.
     */
    public Photo(Uri uri) {
        this.uri = uri;
        this.uriString = uri.toString();

        HashSet<String> ppp = new HashSet<>();
        ppp.add("none");
        person = new Tag("person",ppp);
        location = new Tag("location","None");
    }
    public void changeLocation(String s){ location.changeValue(s); }
    public void removePerson(String s){ person.getValues().remove(s); }
    public void addPerson(String s){ person.getValues().add(s); }

    /**
     * Returns the file path of the photo.
     *
     * @return The file path of the photo.
     */
    public Uri getUri() {
        return uri;
    }
    /**
     * Returns the set of tags associated with the photo.
     *
     * @return A HashSet of Tag objects.
     */
    public HashSet<String> getPerson() {
        return person.getValues();
    }
    public String getLocation() {
        return location.getValue();
    }



    public void restoreUri() {
        this.uri = Uri.parse(uriString);
    }
}