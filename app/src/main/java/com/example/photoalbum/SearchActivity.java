package com.example.photoalbum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

public class SearchActivity extends AppCompatActivity {

    private ListView SearchListView;
    private TextView person_search_input;
    private TextView location_search_input;
    private Button apply_search_button;
    private RadioButton conjunction_radio;

    private ImageAdapter images;

    private MainUser mainUser;
    private ArrayList<Photo> foundPhotos;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.search_image);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar6);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SearchListView = findViewById(R.id.SearchListView);
        person_search_input = findViewById(R.id.person_search_input);
        location_search_input = findViewById(R.id.location_search_input);
        apply_search_button = findViewById(R.id.apply_search_button);
        conjunction_radio = findViewById(R.id.conjunction_radio);

        mainUser = MainUser.loadSession(SearchActivity.this);
        foundPhotos = new ArrayList<>();






        // Search
        apply_search_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                populateListView();
            }
        });
    }
    private void populateListView(){
        if(foundPhotos.isEmpty()){
            return;
        }
        ArrayList<Uri> uris = new ArrayList<>();
        foundPhotos.stream()
                .forEach(p -> uris.add(p.getUri()));
        images = new ImageAdapter(this, uris);
        SearchListView.setAdapter(images);
    }

}
