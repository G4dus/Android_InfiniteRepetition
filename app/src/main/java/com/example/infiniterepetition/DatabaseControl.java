package com.example.infiniterepetition;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseControl extends AppCompatActivity {
    SQLiteDatabase db;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_control);

        db = openOrCreateDatabase("Collection_", MODE_PRIVATE, null);
       String sqlDB = "CREATE TABLE IF NOT EXISTS TABLE1 (Id INTEGER PRIMARY KEY AUTOINCREMENT, Question VARCHAR, Answer VARCHAR, Sound VARCHAR )";
        db.execSQL(sqlDB);


        ////show id of insert record
       Cursor c = db.rawQuery("SELECT Id, Question, Answer, Sound FROM TABLE1",null);
        c.moveToLast();


        int lastID = c.getCount();
        String lastIDplusOne = String.valueOf(lastID);

        String id = c.getString(c.getColumnIndex("Id"));
        String Q = c.getString(c.getColumnIndex("Question"));
        String A = c.getString(c.getColumnIndex("Answer"));



    }



    public void SelItem(View view){

        dbEdit eddd = new dbEdit();
       // eddd.displaysth();


        Toast.makeText(this, eddd.displaysth(),Toast.LENGTH_SHORT).show();
    }


}
