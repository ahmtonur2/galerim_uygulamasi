package com.example.ahmtonur.projedeneme4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    Button btnekle;
    public ListView listView;
    ArrayList<MyData> listData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.myList);
        btnekle =findViewById(R.id.btnekle);
        mDatabaseHelper = new DatabaseHelper(this);
        populateView();
        CustomAdapter adapter = new CustomAdapter(this,R.layout.custom_activity,listData);
        listView.setAdapter(adapter);

        btnekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddForm.class);
                startActivity(intent);

            }
        });


    }




    private void populateView()
    {
        Cursor data = mDatabaseHelper.getData();
        while(data.moveToNext())
        {
            String name = data.getString(1);
            byte[] image = data.getBlob(2);
            listData.add(new MyData(name,image));
        }
    }

    public class CustomAdapter extends BaseAdapter {

        private Context context;
        private int layout;
        ArrayList<MyData> textList;

        public CustomAdapter(Context context, int layout, ArrayList<MyData> textList) {
            this.context = context;
            this.layout = layout;
            this.textList = textList;
        }

        @Override
        public int getCount() {
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            ImageView imageView1;
            TextView textName;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            View row = view;
            ViewHolder holder;

            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(layout, null);
                holder = new ViewHolder();
                holder.textName = row.findViewById(R.id.textView);
                holder.imageView1 = row.findViewById(R.id.imageView);
                row.setTag(holder);

            } else {
                holder = (ViewHolder) row.getTag();
            }
            final MyData photo = textList.get(position);
            holder.textName.setText(photo.GetName());
            byte[] pImage = photo.GetImage();
            final Bitmap bitmap = BitmapFactory.decodeByteArray(pImage, 0, pImage.length);
            holder.imageView1.setImageBitmap(bitmap);

            //Satıra tıklanınca gerçekleşecek olayların yazıldığı fonksiyon
            row.setOnClickListener(new AdapterView.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Cursor data = mDatabaseHelper.getItemId(photo.GetName());
                    int itemId = -1;
                    String PhotoName = "";
                    byte[] PhotoImage = null;
                    while (data.moveToNext()) {

                        itemId = data.getInt(0);
                        PhotoName = data.getString(1);
                        PhotoImage = data.getBlob(2);

                        Intent intent = new Intent(getApplicationContext(),EditData.class);
                        intent.putExtra("id",itemId);
                        intent.putExtra("name",photo.GetName());
                        ByteArrayOutputStream bs = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,50,bs);
                        intent.putExtra("byteArray",bs.toByteArray());
                        startActivity(intent);


                    }


                }
            });


            return row;
        }

    }


    private void ToastMessage (String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
