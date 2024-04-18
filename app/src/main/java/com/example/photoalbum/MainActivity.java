package com.example.photoalbum;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        show_delete_album_dialog.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showDeleteDialog();
            }
        });

    }

    public void showDeleteDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.delete_album_dialog);

        //dialog.getWindow().setBackgroundDrawableResource(R.layout.activity_main);

        Button btnClose = dialog.findViewById(R.id.close_button);

        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }

        });

        dialog.show();
    }
}