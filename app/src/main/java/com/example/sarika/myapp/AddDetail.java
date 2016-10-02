package com.example.sarika.myapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class AddDetail extends ActionBarActivity {

    public static final String TAG1 = "AddRollNo";
    public static final String TAG2 = "AddMarks";
    private EditText rollNo;
    private EditText marks;
    private Button add_button;
    private Button modify_button;
    //private Button view_button;
    //private Button remove_button;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail);

        rollNo = (EditText) findViewById(R.id.rollNoEdit);
        marks = (EditText) findViewById(R.id.markEdit);
        add_button = (Button) findViewById(R.id.addButton);
        modify_button = (Button) findViewById(R.id.modifyButton);

        //calling method to create or open database
        createDatabase();

        //retrieving data on rotation of screen
        if (savedInstanceState != null) {
            String saveRollNo = savedInstanceState.getString(TAG1);
            String savedMarks = savedInstanceState.getString(TAG2);

            rollNo.setText(saveRollNo);
            marks.setText(savedMarks);

        }

        //adding listener to button to insert values into database
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rollNumber = rollNo.getText().toString();
                int rollNum = Integer.parseInt(rollNumber);
                String m1 = marks.getText().toString();
                int marksAdd = Integer.parseInt(m1);

                if (rollNumber.equals("") || m1.equals("")) {
                    System.out.println("Please fill all fields");
                } else {
                    String query = "INSERT INTO student VALUES(" + rollNum + "," + marksAdd + ");";
                    db.execSQL(query);
                    gotoToast();
                }

            }
        });


        //adding listener to button to update marks of given roll number in database
        modify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rollNumber = rollNo.getText().toString();
                int rollNum;
                rollNum = Integer.parseInt(rollNumber);
                String m1 = marks.getText().toString();
                int marksAdd;
                marksAdd = Integer.parseInt(m1);
                if (rollNumber.equals("") || m1.equals("")) {
                    System.out.println("Please fill all fields");
                } else {
                    String query = "UPDATE student SET marks=" + marksAdd + " WHERE RollNo=" + rollNum + ";";
                    db.execSQL(query);
                    gotoModifyToast();
                }
            }
        });
    }

    //method to display toast when data inserted successfully
    public void gotoToast() {
        Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show();
    }

    //method to display toast when data updated successfully
    protected void gotoModifyToast() {
        Toast.makeText(this, "Data modified successfully", Toast.LENGTH_SHORT).show();
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
        getMenuInflater().inflate(R.menu.menu_add_detail, menu);
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


    //method to save instance
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String saveRoll = rollNo.getText().toString();
        String saveMarks = marks.getText().toString();
        savedInstanceState.putString(TAG1, saveRoll);
        savedInstanceState.putString(TAG2, saveMarks);
        super.onSaveInstanceState(savedInstanceState);
    }

}
