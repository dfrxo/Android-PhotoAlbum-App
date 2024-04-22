package com.example.photoalbum;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

public class AlbumImagesActivity extends AppCompatActivity{
    private Album a;
    private ArrayList<Photo> photos;
    private ArrayList<Uri> uris = new ArrayList<>();
    private String albumName;
    private MainUser mainUser;
    private ListView photoListView;
    private Button add_image_button;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.images_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Get chosen album
        Bundle bundle = getIntent().getExtras();
        albumName = bundle.getString(MainActivity.ALBUM_NAME);
        mainUser = MainUser.loadSession(this);
        Optional<Album> temp = mainUser.getAlbums()
                .stream()
                .filter(x -> x.getName().equals(albumName))
                .findAny();
        a = temp.get();
        photos = a.getPhotos();
        for(Photo p: photos){
            uris.add(p.getUri());
        }


        photoListView = findViewById(R.id.photoListView);
        ImageAdapter images = new ImageAdapter(this,uris);

        photoListView.setAdapter(images);

    }
    private void populateListView(){
        ArrayList<Photo> photos1 = a.getPhotos();

    }
}
