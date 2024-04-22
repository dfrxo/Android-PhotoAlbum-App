package com.example.photoalbum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
/**
 * Stores album information. Contains photos and other information.
 * @author David Fink
 * @author Tanesha More
 */
public class Album implements Serializable{

    private static final long serialVersionUID = 1L;
    private String name;
    private ArrayList<Photo> photos;
    /**
     * Constructs an Album with a specified name.
     *
     * @param name The name of the album.
     */
    public Album(String name) {
        this.name = name.trim().toLowerCase();
        photos = new ArrayList<>();
    }
    public Album(String name, ArrayList<Photo> photos) {
        this.name = name;
        this.photos = photos;
    }
    /**
     * Removes a photo from the album at the specified index.
     *
     * @param i The index of the photo to be removed.
     */
    public void removePhoto(int i) {
        photos.remove(i);
    }
    /**
     * Returns a list of all photos in the album.
     *
     * @return An ArrayList of Photo objects.
     */
    public ArrayList<Photo> getPhotos(){

        return photos;
    }
    /**
     * Returns a set of names for all photos in the album.
     *
     * @return A HashSet containing the names of all photos.
     */
    public HashSet<String> getPhotoNames(){
        HashSet<String> names=new HashSet<>();
        for(Photo p: photos) {
            names.add(p.getUri().toString());
        }
        return names;
    }
    /**
     * Updates a specific photo in the album. If the photo exists, it is replaced.
     *
     * @param p The new photo object to update.
     */
    public void updatePhoto(Photo p) {
        for (int i = 0; i < photos.size(); i++) {
            Photo old = photos.get(i);
            if(old.getUri().equals(p.getUri())) {
                photos.remove(old);
                photos.add(p);
            }
        }
    }
    /**
     * Sets a new list of photos for the album.
     *
     * @param lis The new list of photos.
     */
    public void changePhotos(ArrayList<Photo> lis) {
        photos = lis;
    }
    /**
     * Adds a photo to the album.
     *
     * @param p The photo to add.
     */
    public void addPhoto(Photo p) {
        photos.add(p);
    }
    /**
     * Returns the number of photos in the album.
     *
     * @return The size of the photos list.
     */
    public int size() {
        return photos.size();
    }
    /**
     * Returns the name of the album.
     *
     * @return The name of the album.
     */
    public String getName() {
        return name;
    }
    /**
     * Changes the name of the album.
     *
     * @param name The new name for the album.
     */
    public void changeName(String name) {
        this.name = name;
    }

    public String toString(){
        return name + " - Size: " + size();
    }
}