package com.example.photoalbum;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
        ArrayList<Photo> foundPhotos=new ArrayList<>();
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

            foundPhotos = albums.stream() // Stream<Album>
                    .flatMap(i -> i.getPhotos().stream())      // Stream<Photo>
                    .filter(p->p.getLocation().startsWith(val))  // Stream<Photo>
                    .collect(Collectors.toCollection(ArrayList::new));



        }
        return foundPhotos;
    }
    public ArrayList<Photo> search(String key, String val, ArrayList<Photo> temp){
        ArrayList<Photo> foundPhotos=new ArrayList<>();
        if(key.equals("Person")){
            foundPhotos = temp.stream() // Stream<Photos>
                    .filter(xx -> xx.getPerson().stream()  // Filtering PHOTOS.
                            .anyMatch(tt -> tt.startsWith(val)))
                    .collect(Collectors.toCollection(ArrayList::new));

        }
        else if(key.equals("Location")) {
            foundPhotos = temp.stream() // Stream<Photo>
                    .filter(xxx -> xxx.getLocation().startsWith(val)) // Filtering PHOTOS
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return foundPhotos;
    }
    public ArrayList<Photo> search(String key1,String val1, String key2, String val2,String operation){
        ArrayList<Photo> foundPhotos;
        ArrayList<Photo> t2=null;

        if(operation.equals("OR")){

            foundPhotos = search(key1,val1);
            ArrayList<Photo> t3 = foundPhotos;
            t2 = search(key2, val2);

            t2.stream()
                    .filter(xx -> !t3.contains(xx))
                    .forEach(yy -> t3.add(yy));
            foundPhotos = t3;
        }
        else{
            foundPhotos = search(key1, val1);
            foundPhotos = search(key2, val2, foundPhotos);

        }

        return foundPhotos;
    }

}
