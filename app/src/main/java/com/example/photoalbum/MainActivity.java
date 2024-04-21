package com.example.photoalbum;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class MainActivity extends AppCompatActivity {

    private ActivityResultLauncher<String[]> createAlbum;
    private static final int REQUEST_IMAGE_GET = 1;

    private ArrayList<Photo> photoList = new ArrayList<>();
    private String albumName = "";

    private ArrayList<Album> albums;
    private ArrayList<String> storedAlbumNames;

    private ListView albumsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // FIX LATER - load from memory
        albums = new ArrayList<>();
        storedAlbumNames = new ArrayList<>();
        ////////////////
        super.onCreate(savedInstanceState);
        // Test
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        Button show_add_album_dialog = findViewById(R.id.add_album_button);
        Button show_delete_album_dialog = findViewById(R.id.delete_album_button);
        Button show_rename_album_dialog = findViewById(R.id.rename_album_button);

        albumsListView = findViewById(R.id.album_list_view);

        //populating albumListView
        populateListView();

        //getting clicked item from list
        albumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 'i' is the index of the item clicked. this method will get that index.
                String s = adapterView.getItemAtPosition(i).toString();
                System.out.println("hi my name is "+ s);
            }
        });

        show_delete_album_dialog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showDeleteDialog();
            }
        });
        show_rename_album_dialog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showRenameDialog();
            }
        });
        show_add_album_dialog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showAddDialog();
            }
        });

        // For initializing Photo Chooser
        createAlbum = registerForActivityResult(
                new ActivityResultContracts.OpenMultipleDocuments(),
                new ActivityResultCallback<List<Uri>>() {
                    @Override
                    public void onActivityResult(List<Uri> uris) {
                        // Create an album
                        photoList.clear();
                        if (uris != null) {
                            for (Uri uri : uris) {
                                photoList.add(new Photo(uri));
                            }
                            Album a = new Album(albumName,photoList);
                            albums.add(a);
                            storedAlbumNames.add(albumName);
                            albumName = "";
                            populateListView();
                        }
                    }
                }
        );


    }



    /**
     * To DELETE an album.
     */
    public void showDeleteDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_album_dialog);

        Button btnClose = dialog.findViewById(R.id.close_button);
        Button deleteAlbum = dialog.findViewById(R.id.ok_button);
        EditText albumToBeDeleted = dialog.findViewById((R.id.current_album_name));

        deleteAlbum.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String temp = albumToBeDeleted.getText().toString();
                if(temp!="" && storedAlbumNames.contains(temp)){
                    storedAlbumNames.remove(temp);
                    Album tempAlb=null;
                    Optional<Album> a = albums.stream()
                            .filter(album -> {return album.getName().equals(temp);})
                            .findFirst();
                    if(a.isPresent()){
                        tempAlb = a.get();
                    }
                    albums.remove(tempAlb);
                    populateListView();
                }
                else{
                    CharSequence text = "Album Doesn't Exist!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                }
                dialog.dismiss();
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * To ADD an album.
     */
    public void showAddDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_album_dialog);
        Button btnClose = dialog.findViewById(R.id.close_button);
        Button addAlbum = dialog.findViewById(R.id.ok_button);
        EditText albumToBeAdded = dialog.findViewById((R.id.album_to_be_added));
        // Add Album

        addAlbum.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                albumName = albumToBeAdded.getText().toString();

                if(storedAlbumNames.contains(albumName)){
                    CharSequence text = "You Already Have An Album With That Name!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                }
                else if(!albumName.equals("")){
                    createAlbum.launch(new String[] {"image/*"});
                }
                else{
                    CharSequence text = "You Have To Name Your Album!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                }
                dialog.dismiss();
            }

        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * To RENAME an album.
     */
    public void showRenameDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.rename_album_dialog);

        Button btnClose = dialog.findViewById(R.id.close_button);
        Button renameAlbum = dialog.findViewById(R.id.ok_button);
        EditText currentAlbumName = dialog.findViewById((R.id.current_album_name));
        EditText newAlbumName = dialog.findViewById((R.id.new_album_name));
        renameAlbum.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String newName = currentAlbumName.getText().toString();
                String old = newAlbumName.getText().toString();
                if(storedAlbumNames.contains(newName)){
                    CharSequence text = "You Already Have An Album With That Name!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                }
                else if(storedAlbumNames.contains(old)) {
                    Optional<Album> temp = albums.stream()
                            .filter(a -> a.getName().equals(old))
                            .findFirst();
                    Album a = null;
                    if (temp.isPresent()) {
                        a = temp.get();
                    }
                    storedAlbumNames.remove(old);
                    storedAlbumNames.add(newName);
                    a.changeName(newName);
                    populateListView();
                }
                else{
                    CharSequence text = "That Current Album Doesn't Exist";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                }
                dialog.dismiss();
            }
        });
        btnClose.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
    public void populateListView(){
        // String[] names = {"David", "Tanesha"};
        String[] names = albums.stream()
                .map(a -> a.toString())
                .toArray(String[]::new);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, names);
        albumsListView.setAdapter(adapter);


    }

}