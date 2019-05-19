package com.example.ahmtonur.projedeneme4;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by on 10.05.2019.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Database_Name = "names6.db";
    public static final String Table_Name = "mytable";
    public DatabaseHelper(Context context){ super(context,Database_Name,null,1);}
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ Table_Name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, NewImage blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        onCreate(db);

    }
    public boolean addData(String name ,byte[] img){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("newimage",img);
        long result = db.insert(Table_Name,null,contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public void deleteData(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql ="DELETE FROM " + Table_Name + " WHERE id " + " = " + id ;
        db.execSQL(sql);
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM mytable";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    //ListView'ın satılarına tıklama olayı için
    public Cursor getItemId(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM mytable WHERE NAME" + " =  '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;

    }


}
