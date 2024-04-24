package com.example.photoalbum;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

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
    private ArrayList<Album> alb;
    private ListView albums_list;

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

        person_tag.setText("");
        location_tag.setText("");


        move_button = findViewById(R.id.move_button);
        apply_changes_button = findViewById(R.id.apply_changes_button);

        mainUser = MainUser.loadSession(PhotoViewActivity.this);
        photo = mainUser.getPhoto();
        alb = mainUser.getAlbums();

        HashSet<String> personSet = photo.getPerson();
        String persons = String.join(", ", personSet);
        person_tag.setText(persons);
        location_tag.setText(photo.getLocation());

        Bundle bundle = getIntent().getExtras();
        albumName = bundle.getString(AlbumImagesActivity.ALBUM_NAME);
    //    uriString = bundle.getString(AlbumImagesActivity.PHOTO_NAME);
        image_preview.setImageURI(photo.getUri());

        move_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showMoveDialog();
            }
        });
        apply_changes_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showEditDialog();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, AlbumImagesActivity.class);
        intent.putExtra(MainActivity.ALBUM_NAME, albumName);
        startActivity(intent);
        finish();
        return true;
    }
    public void showEditDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.edit_details_page);

        ImageView image_preview = dialog.findViewById(R.id.image_preview);
        Button delete_image_button = dialog.findViewById(R.id.delete_image_button);
        TextView person_tag_input = dialog.findViewById(R.id.person_tag_input);
        TextView location_tag_input = dialog.findViewById(R.id.location_tag_input);
        Button apply_changes_button = dialog.findViewById(R.id.apply_changes_button);

        person_tag_input.setText("");
        location_tag_input.setText("");

        Photo curr = mainUser.getPhoto();
        image_preview.setImageURI(curr.getUri());



        delete_image_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Album curr=null;
                for(Album a: alb){
                    if(albumName.equals(a.getName())){
                        curr = a;
                        break;
                    }
                }
                curr.removePhoto(curr.getPhotos().indexOf(photo));

                mainUser.saveSession(PhotoViewActivity.this);
                onSupportNavigateUp();
                dialog.dismiss();
            }
        });
        apply_changes_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String person = person_tag_input.getText().toString();
                String location = location_tag_input.getText().toString();

                String[] persons = person.split(",");
                persons = Stream.of(persons)
                        .map(str -> str.trim())
                        .toArray(String[]::new);
                if(!location.isEmpty()){
                    photo.changeLocation(location);
                }
                if(persons.length!=0) {
                    photo.addPerson(persons);
                }
                mainUser.saveSession(PhotoViewActivity.this);
                dialog.dismiss();
            }

        });

        dialog.show();
    }
    public void showMoveDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.move_image_to_album);
        albums_list = dialog.findViewById(R.id.albums_list);
        populateListView();

        albums_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayList<Album> alb = mainUser.getAlbums();
                Album prev=null;
                for(Album a: alb){
                    if(albumName.equals(a.getName())){
                        prev = a;
                        break;
                    }
                }
                Album next = alb.get(i);
                next.addPhoto(photo);
                prev.removePhoto(prev.getPhotos().indexOf(photo));
                mainUser.saveSession(PhotoViewActivity.this);
                onSupportNavigateUp();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    public void populateListView(){
        // String[] names = {"David", "Tanesha"};
        String[] names = alb.stream()
                .map(Album::getName)
                .toArray(String[]::new);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, names);
        albums_list.setAdapter(adapter);
    }
}
