package com.example.photoalbum;


import java.io.Serializable;
import android.net.Uri;
import java.util.HashSet;
/**
 * Contains photo data. Stores info about a photo: name, path, caption, data, and tags;
 * @author David Fink
 * @author Tanesha More
 */
public class Photo implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private Uri uri;
    private HashSet<Tag> tags;

    /**
     * Constructs a Photo object with the specified file path.
     * Creates a new hashSet and updates the data for the photo.
     *
     * @param uri The Uri path of the photo.
     */
    public Photo(Uri uri) {
        this.uri = uri;
        tags = new HashSet<>();
    }
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
    public HashSet<Tag> getTags() {
        return tags;
    }
    /**
     * Adds a tag to the photo.
     *
     * @param t The tag to be added.
     */
    public void addTag(Tag t) {
        tags.add(t);
    }
    /**
     * Removes a tag from the photo.
     *
     * @param t The tag to be removed.
     */
    public void removeTag(Tag t) {
        tags.remove(t);
    }

}