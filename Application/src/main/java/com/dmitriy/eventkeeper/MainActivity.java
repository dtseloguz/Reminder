package com.dmitriy.eventkeeper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    DBHelper DB;
    SQLiteDatabase db;
    ListView list;
    Cursor cursor;
    ArrayAdapter adapter;
    AlertDialog.Builder ad;
    Calendar cal;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB=new DBHelper(this);
        db = DB.getWritableDatabase();
        list=findViewById(R.id.list);
        cal=Calendar.getInstance();
        show();

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                delete(position);
                return true;

            }
        });
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        show();

    }

    protected void delete(final int pos){
        ad = new AlertDialog.Builder(MainActivity.this);
        //ad.setTitle(title);  // заголовок
        ad.setMessage("Delete?"); // сообщение
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                cursor = db.query(DB.TABLE_NAME, null, null, null, null, null, null);
                cursor.moveToPosition(pos);
                int idColIndex=cursor.getColumnIndex(DB.COLUMN_ID);
                db.delete(DB.TABLE_NAME, "_id = " + Integer.parseInt(cursor.getString(0)), null);
                cursor.close();
                adapter.clear();
                show();
            }
        });
        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {

            }
        });
        ad.show();

    }

   public void onClickAdd (View v){
        Intent intent = new Intent(this, EventCreator.class);
        startActivity(intent);
    }

    public void show(){

        ArrayList<String> items;
        cursor = db.query(DB.TABLE_NAME, null, null, null, null, null, null);
        items= new ArrayList<>();

        if (cursor.moveToFirst()) {

            int nameColIndex = cursor.getColumnIndex(DB.COLUMN_NAME);

            do {

                items.add(cursor.getString(nameColIndex));

            } while (cursor.moveToNext());

        }
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,items);
        list.setAdapter(adapter);

        cursor.close();
    }


}

