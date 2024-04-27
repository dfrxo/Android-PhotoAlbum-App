package com.example.photoalbum;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String storeFile = "userFile.dat";

    private ArrayList<Album> albums;
    private ArrayList<String> storedAlbumNames;

    private Photo currPhoto;

    private MainUser(){
        albums = new ArrayList<>();
        storedAlbumNames = new ArrayList<>();
    }

    public ArrayList<Album> getAlbums(){ return albums; }
    public ArrayList<String> getStoredAlbumNames(){ return storedAlbumNames; }
    public void setStoredAlbumNames(ArrayList<String> storedAlbumNames) { this.storedAlbumNames = storedAlbumNames; }
    public void setAlbums(ArrayList<Album> albums) { this.albums = albums; }

    public Photo getPhoto(){
        return currPhoto;
    }
    public void changePhoto(Photo p){
        currPhoto=p;
    }
    public void saveSession(Context context) {
        try {
            FileOutputStream fileOut = context.openFileOutput(storeFile, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static MainUser loadSession(Context context) {
        MainUser mainUser = null;
        try {
            FileInputStream fileIn = context.openFileInput(storeFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mainUser = (MainUser) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            mainUser = new MainUser();
        }
        mainUser.setUri();

        return mainUser;
    }
    public void setUri(){
        albums.stream().flatMap(a -> a.getPhotos().stream()).
                forEach(Photo::restoreUri);
    }
    public ArrayList<Photo> search(String key, String val){
        ArrayList<Photo> foundPhotos=null;
        if(key.equals("Person")){
            foundPhotos = albums.stream()  //Stream<Albums>
                    .flatMap(i-> i.getPhotos().stream())  // Stream<Photo>
                    .filter(p-> p.getPerson().stream()
                            .anyMatch(x-> x.startsWith(val)))
                    .collect(Collectors.toCollection(ArrayList::new));














        }
        else if(key.equals("Location")) {

            // ArrayList<Albums> albums ;
            // each album contains an ArrayList of Photos.

            // String value

            albums.stream() // Stream<Album>
                    .flatMap(i -> i.getPhotos().stream())      // Stream<Photo>
                    .filter(p->p.getLocation().startsWith(val))  // Stream<Photo>
                    .collect(Collectors.toCollection(ArrayList::new));









//            foundPhotos = albums.stream()
//                    .flatMap(a -> a.getPhotos().stream())
//                    .filter(c ->
//                            c.getPerson().stream()
//                                    .anyMatch(d -> d.startsWith(val)))
//                    .collect(Collectors.toCollection(ArrayList::new));





        }
        return foundPhotos;
    }
    public ArrayList<Photo> search(String key1,String val1, String key2, String val2,String operation){

        return null;
    }

}
