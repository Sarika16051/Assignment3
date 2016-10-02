package com.example.sarika.myapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RemoveDetail extends ActionBarActivity {

    private static final String TAG5 = "RemoveActivity";
    private EditText rollNo;
    private Button removeButton;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_detail);

        rollNo = (EditText) findViewById(R.id.rollNoEditRemove);
        removeButton = (Button) findViewById(R.id.removeData);

        createDatabase();

        if (savedInstanceState != null) {
            String rollNumber = savedInstanceState.getString(TAG5);
            rollNo.setText(rollNumber);
        }

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rollNumber = rollNo.getText().toString();
                int rollNum = Integer.parseInt(rollNumber);

                //retrieving data from database corresponding to given roll number
                Cursor c = db.rawQuery("SELECT * FROM student WHERE RollNo=" + rollNum, null);

                if (c != null && c.getCount() > 0) {
                    if (c.moveToFirst()) {
                        do {
                            //delete the record from database
                            db.execSQL("DELETE FROM student WHERE RollNo=" + rollNum);
                            deleteToast();
                            rollNo.setText("");
                        } while (c.moveToNext());
                    }
                }
            }
        });

    }

    protected void deleteToast() {
        Toast.makeText(this, "Deleted successfully", Toast.LENGTH_SHORT);
    }

    //method to create database or open database if exists
    protected void createDatabase() {
        db = openOrCreateDatabase("MarksTable", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(RollNo INTEGER PRIMARY KEY,marks INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS password(Password VARCHAR PRIMARY KEY);");
    }

    //Saving instance
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String saveRoll = rollNo.getText().toString();
        savedInstanceState.putString(TAG5, saveRoll);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_remove_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
