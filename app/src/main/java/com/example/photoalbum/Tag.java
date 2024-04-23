package com.example.photoalbum;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Contains data for the tag. Tag is a key value pair attached to a photo.
 * @author David Fink
 * @author Tanesha More
 */
public class Tag implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String key;
    private String value;
    private ArrayList<String> values;
    /**
     * Constructs a Tag with the specified key and value.
     *
     * @param key The key of the tag, representing the category of the tag (e.g., "location").
     * @param value The value of the tag, representing the specific detail within the category (e.g., "Paris").
     */
    public Tag(String key, String value) {
        this.key=key.trim().toLowerCase();
        this.value=value.trim().toLowerCase();
    }
    public Tag(String key, ArrayList<String> values){
        this.key=key.trim().toLowerCase();
        this.values=values;
    }
    public ArrayList<String> getValues(){
        return values;
    }
    /**
     * Returns the key of the tag.
     *
     * @return The key of the tag.
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value of the tag.
     *
     * @return The value of the tag.
     */

    public String getValue() {
        return value;
    }
    /**
     * Returns a string of the tag.
     * Formats the tag as "key:value".
     *
     * @return A string representation of the tag.
     */
    @Override
    public String toString() {
        return key+":"+value;
    }
}
