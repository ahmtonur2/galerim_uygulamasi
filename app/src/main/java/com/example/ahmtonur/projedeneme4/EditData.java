package com.example.ahmtonur.projedeneme4;

import android.app.WallpaperManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;

public class EditData extends AppCompatActivity {

    private TextView edit_text;
    ImageView edit_image;
    int SelectId;
    String SelectedName;
    Button btnsil,btnarka;
    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        edit_text=findViewById(R.id.edit_text_name);
        edit_image = findViewById(R.id.edit_image);
        btnarka =findViewById(R.id.btnarkaplan);
        btnsil=findViewById(R.id.btnsil);
        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedTent =getIntent();
        SelectId = receivedTent.getIntExtra("id",-1);
        SelectedName= receivedTent.getStringExtra("name");

        if(getIntent().hasExtra("byteArray"))
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
            edit_image.setImageBitmap(bitmap);
        }
        edit_text.setText(SelectedName);

        btnarka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"),0,getIntent().getByteArrayExtra("byteArray").length);
                WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
                try {
                    manager.setBitmap(bitmap);
                    Toast.makeText(EditData.this, "Duvar kagidi olarak ayarlandi", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Toast.makeText(EditData.this,"Duvar kagidi ayarlanamadi!!!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


        btnsil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mDatabaseHelper.deleteData(SelectId);
                edit_text.setText("");
                edit_image.setImageResource(R.mipmap.ic_launcher);

                Toast.makeText(EditData.this,"Fotograf Silindi!!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditData.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }

}
