package com.example.photoalbum;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class MainActivity extends AppCompatActivity {


    //private List<String> albumsList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Test
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button show_add_album_dialog = findViewById(R.id.add_album_button);
        Button show_delete_album_dialog = findViewById(R.id.delete_album_button);
        Button show_rename_album_dialog = findViewById(R.id.rename_album_button);

        ListView albumsListView = findViewById(R.id.album_list_view);

        albumsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 'i' is the index of the item clicked. this method will get that index.
                String s = adapterView.getItemAtPosition(i).toString();
//

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
                System.out.println(albumToBeDeleted.getText());
                //whatever function that deletes this album...

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

        addAlbum.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                System.out.println(albumToBeAdded.getText());
                //whatever function that deletes this album...

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
                System.out.println(currentAlbumName.getText());
                System.out.println(newAlbumName.getText());
                //whatever function that deletes this album...

            }
        });
        btnClose.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}