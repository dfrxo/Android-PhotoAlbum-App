package com.example.photoalbum;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PhotoViewActivity extends AppCompatActivity {
    private String uriString;
    private MainUser mainUser;
    private ImageView image_preview;
    private TextView person_tag;
    private TextView location_tag;
    private Button move_button;
    private Button apply_changes_button;
    private Photo photo;
    private String albumName;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.details_page);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar3);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        image_preview = findViewById(R.id.image_preview);
        person_tag = findViewById(R.id.person_tag);
        location_tag = findViewById(R.id.location_tag);
        move_button = findViewById(R.id.move_button);
        apply_changes_button = findViewById(R.id.apply_changes_button);

        mainUser = MainUser.loadSession(PhotoViewActivity.this);
        photo = mainUser.getPhoto();
        Bundle bundle = getIntent().getExtras();
        albumName = bundle.getString(AlbumImagesActivity.ALBUM_NAME);
    //    uriString = bundle.getString(AlbumImagesActivity.PHOTO_NAME);
        image_preview.setImageURI(photo.getUri());
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, AlbumImagesActivity.class);
        intent.putExtra(MainActivity.ALBUM_NAME, albumName);
        startActivity(intent);
        finish();
        return true;
    }
}
