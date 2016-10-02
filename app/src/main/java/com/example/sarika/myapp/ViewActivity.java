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
import android.widget.TextView;


public class ViewActivity extends ActionBarActivity {

    private static final String TAG6 = "ViewActivity";
    private EditText rollNo;
    private Button viewData;
    private TextView marks;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        rollNo = (EditText) findViewById(R.id.rollNoEditView);
        marks = (TextView) findViewById(R.id.displayMarks);
        viewData = (Button) findViewById(R.id.viewData);

        createDatabase();
        if (savedInstanceState != null) {
            String rollNumber = savedInstanceState.getString(TAG6);
            rollNo.setText(rollNumber);
        }

        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rollNumber = rollNo.getText().toString();
                int rollNum = Integer.parseInt(rollNumber);
                //Retrieving data from database
                Cursor c = db.rawQuery("SELECT * FROM student WHERE RollNo=" + rollNum, null);

                if (c != null && c.getCount() > 0) {
                    if (c.moveToFirst()) {
                        do {
                            int num = c.getInt(1);

                            marks.setText("Marks : " + String.valueOf(num));
                        } while (c.moveToNext());
                    }
                }

            }
        });
    }

    //method to create database or open database if exists
    protected void createDatabase() {
        db = openOrCreateDatabase("MarksTable", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(RollNo INTEGER PRIMARY KEY,marks INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS password(Password VARCHAR PRIMARY KEY);");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String saveRoll = rollNo.getText().toString();
        savedInstanceState.putString(TAG6, saveRoll);
        super.onSaveInstanceState(savedInstanceState);
    }
}
