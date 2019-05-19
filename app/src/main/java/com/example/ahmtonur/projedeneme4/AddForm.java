package com.example.ahmtonur.projedeneme4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by on 10.05.2019.
 */

public class AddForm extends AppCompatActivity{

    final int REQUEST_CODE_GALLERY = 999;
    DatabaseHelper mDatabaseHelper;
    Button btnadd,btnchoose,btngal;
    EditText editText;
    ImageView myImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);

        btnadd = findViewById(R.id.btnadd);
        btnchoose = findViewById(R.id.btnchoose);
        btngal=findViewById(R.id.btngaleri);
        myImg = findViewById(R.id.imageView);
        editText = findViewById(R.id.addText);
        mDatabaseHelper = new DatabaseHelper(this);

        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(AddForm.this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);

            }
        });


        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NewEntry = editText.getText().toString();
                byte[] NewEntryImg = imageViewTobyte(myImg);

                //Listeleme işini farklı bir tuşa almak için değişecek

                if(editText.length()!=0)
                {
                    AddData(NewEntry,NewEntryImg);
                    editText.setText("");
                    myImg.setImageResource(R.mipmap.ic_launcher);

                }
                else
                {
                    Toast("Zorunlu alan");
                }
            }

            private byte[] imageViewTobyte(ImageView image) {
                Bitmap bitmapp=((BitmapDrawable) image.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmapp.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray = stream.toByteArray();
                return byteArray;
            }
        });

        btngal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddForm.this,MainActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode ==REQUEST_CODE_GALLERY)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else
            {
                Toast.makeText(getApplicationContext(),"İzniniz yok!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        Uri uri = data.getData();
        try
        {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            myImg.setImageBitmap(bitmap);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        super.onActivityResult(requestCode,resultCode,data);

    }
    private void AddData(String newEntry,byte[] newEntryImg)
    {
        boolean insertData = mDatabaseHelper.addData(newEntry,newEntryImg);
        if(insertData)
        {
            Toast("Kayıt Başarılı");
        }
        else
        {
            Toast("Kayıt Başarısız!!")  ;
        }
    }


    private void Toast(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
}
