package com.example.photoalbum;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlbumImagesActivity extends AppCompatActivity{
    private Album a;
    private ArrayList<Photo> photos;
    private ArrayList<Uri> uris = new ArrayList<>();
    private ArrayList<String> uriStrings = new ArrayList<>();
    private String albumName;
    private MainUser mainUser;
    private ListView photoListView;
    private Button add_image_button;
    private Button slideshow_button;
    private int num=0;
    private int size;
    private ImageView imageView;
    private Uri uri;
    private ActivityResultLauncher<String[]> addToAlbum;
    private ImageAdapter images;

    public static final String PHOTO_NAME = "photo_name";
    public static final String ALBUM_NAME = "album_name";


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
        addToAlbum = registerForActivityResult(new ActivityResultContracts.OpenMultipleDocuments(),
                new AddToAlbum());

        Optional<Album> temp = mainUser.getAlbums()
                .stream()
                .filter(x -> x.getName().equals(albumName))
                .findAny();
        a = temp.get();
        photos = a.getPhotos();
        size = photos.size();
        for(Photo p: photos){
            uris.add(p.getUri());
            uriStrings.add(p.getUri().toString());
        }
        photoListView = findViewById(R.id.photoListView);
        images = new ImageAdapter(this,uris);
        photoListView.setAdapter(images);

        add_image_button = findViewById(R.id.add_image_button);
        slideshow_button = findViewById(R.id.slideshow_button);

        // ListView handler
        photoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mainUser.changePhoto(photos.get(i));
                mainUser.saveSession(AlbumImagesActivity.this);
                PhotoViewActivity(i);
            }
        });
        // Slideshow handler
        slideshow_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showSlideshowDialog();
            }
        });
        add_image_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                addToAlbum.launch(new String[] {"image/*"});
            }
        });
    }
    private void PhotoViewActivity(int pos) {
        Bundle bundle = new Bundle();
        bundle.putString(PHOTO_NAME,photos.get(pos).getUri().toString());
        bundle.putString(ALBUM_NAME,albumName);
        Intent intent = new Intent(this, PhotoViewActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void showSlideshowDialog(){
        if(size==0){

            CharSequence text = "Your Album Is Empty!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(AlbumImagesActivity.this, text, duration);
            toast.show();

            return;
        }
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.slideshow);
        Button X_button = dialog.findViewById(R.id.X_button);
        Button prev_button = dialog.findViewById(R.id.prev_button);
        Button next_button = dialog.findViewById(R.id.next_button);
        imageView = dialog.findViewById(R.id.image_preview);
        num =0;
        uri = photos.get(num).getUri();
        imageView.setImageURI(uri);

        next_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(num<size-1)
                    num++;
                else
                    num=0;
                uri = photos.get(num).getUri();
                imageView.setImageURI(uri);
            }
        });
        prev_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if(num>0)
                    num--;
                else
                    num=size-1;
                uri = photos.get(num).getUri();
                imageView.setImageURI(uri);
            }
        });
        X_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public class AddToAlbum implements ActivityResultCallback<List<Uri>> {
        @Override
        public void onActivityResult(List<Uri> newUris) {
            if (newUris != null) {
                for (Uri uri : newUris) {
                    if (uriStrings.contains(uri.toString())) {
                        continue;
                    }
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
                    if (getIntent() != null && (getIntent().getFlags() & takeFlags) == takeFlags) {
                        getContentResolver().takePersistableUriPermission(uri, takeFlags);
                    }                    a.addPhoto(new Photo(uri));
                    uris.add(uri);
                    uriStrings.add(uri.toString());
                }
                images = new ImageAdapter(AlbumImagesActivity.this, uris);
                photoListView.setAdapter(images);
                mainUser.saveSession(AlbumImagesActivity.this);
            }
        }
    }
}
