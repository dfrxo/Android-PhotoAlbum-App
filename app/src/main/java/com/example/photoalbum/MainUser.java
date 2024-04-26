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

public class MainUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String storeFile = "userFile.dat";

    private ArrayList<Album> albums;
    private ArrayList<String> storedAlbumNames;
    private ArrayList<Photo> allPhotos;

    private Photo currPhoto;

    private MainUser(){
        albums = new ArrayList<>();
        storedAlbumNames = new ArrayList<>();
        allPhotos = new ArrayList<>();
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
    public void addPhoto(Photo p){
          //allPhotos.add(p);
    }
    public void addPhotos(ArrayList<Photo> ps){
        //allPhotos.addAll(ps);
    }
    public ArrayList<Photo> search(Tag a){

        return null;
    }
    public ArrayList<Photo> search(Tag a,Tag b){

        return null;
    }

}
