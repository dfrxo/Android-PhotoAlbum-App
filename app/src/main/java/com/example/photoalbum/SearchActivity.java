package com.example.photoalbum;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

public class SearchActivity extends AppCompatActivity {

    private ListView filter_list;
    private TextView person_search_input;
    private TextView location_search_input;
    private Button apply_search_button;
    private RadioButton conjunction_radio;
    private Spinner search_spinner1;
    private Spinner search_spinner2;

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

        filter_list = findViewById(R.id.filter_list);
        person_search_input = findViewById(R.id.person_search_input);
        location_search_input = findViewById(R.id.location_search_input);
        apply_search_button = findViewById(R.id.apply_search_button);
        search_spinner1 = findViewById(R.id.search_spinner1);
        search_spinner2 = findViewById(R.id.search_spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.dropdown,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_spinner1.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.dropdown2,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_spinner2.setAdapter(adapter2);

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
        foundPhotos.forEach(p -> uris.add(p.getUri()));
        images = new ImageAdapter(this, uris);
        filter_list.setAdapter(images);
    }

}
