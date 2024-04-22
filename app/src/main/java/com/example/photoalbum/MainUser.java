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
    private static final String storeDir = "";
    private static final String storeFile = "userFile.dat";

    private ArrayList<Album> albums;
    private ArrayList<String> storedAlbumNames;

    private MainUser(){
        albums = new ArrayList<>();
        storedAlbumNames = new ArrayList<>();
    }

    public ArrayList<Album> getAlbums(){ return albums; }
    public ArrayList<String> getStoredAlbumNames(){ return storedAlbumNames; }
    public void setStoredAlbumNames(ArrayList<String> storedAlbumNames) { this.storedAlbumNames = storedAlbumNames; }
    public void setAlbums(ArrayList<Album> albums) { this.albums = albums; }

    public void saveSession(Context context) {
        try {

            //FileOutputStream fileOut = new FileOutputStream(storeDir + "/" + storeFile);
            FileOutputStream fileOut = context.openFileOutput(storeFile, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static MainUser loadSession(Context context) {
        MainUser mainUser = null;
//        try {
//            File file = new File(storeDir + "/" + storeFile);
//            if (file.exists()) {
//                FileInputStream fileIn = new FileInputStream(file);
//                ObjectInputStream in = new ObjectInputStream(fileIn);
//                mainUser = (MainUser) in.readObject();
//                in.close();
//                fileIn.close();
//            } else {
//                mainUser = new MainUser();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        try {
            FileInputStream fileIn = context.openFileInput(storeFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mainUser = (MainUser) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            mainUser = new MainUser();
        }
        return mainUser;
    }
}
