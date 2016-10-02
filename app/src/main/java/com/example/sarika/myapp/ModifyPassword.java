package com.example.sarika.myapp;

import android.content.Context;
import android.content.Intent;
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


public class ModifyPassword extends ActionBarActivity {

    private static final String TAG8 = "Current Password";
    private static final String TAG9 = "New Password";
    private EditText currPass;
    private EditText newPass;
    private Button createPass;
    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);


        currPass = (EditText) findViewById(R.id.currentPass);
        newPass = (EditText) findViewById(R.id.newPass);
        createPass = (Button) findViewById(R.id.donePassChange);

        createDatabase();

        if (savedInstanceState != null) {
            String currentPass = savedInstanceState.getString(TAG8);
            String changePass = savedInstanceState.getString(TAG9);
            currPass.setText(currentPass);
            newPass.setText(changePass);
        }

        createPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPass = currPass.getText().toString();
                String changePass = newPass.getText().toString();

                if (currentPass.equals("") || changePass.equals("")) {
                    fillAllFieldsToast();
                } else {
                    //retrieve data from database
                    Cursor c = db.rawQuery("SELECT * FROM password", null);

                    if (c != null && c.getCount() > 0) {
                        if (c.moveToFirst()) {
                            if (c.getString(0).equals(currentPass)) {
                                db.execSQL("DELETE FROM password");
                                String query = "INSERT INTO password VALUES('" + changePass + "');";
                                db.execSQL(query);
                            }

                        } else {
                            wrongPasswordToast();
                        }
                    }
                }
            }
        });


    }

    protected void fillAllFieldsToast() {
        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT);
    }

    protected void wrongPasswordToast() {
        Toast.makeText(this, "Wrong current password entered", Toast.LENGTH_SHORT);
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
        getMenuInflater().inflate(R.menu.menu_modify_password, menu);
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

    //Saving instance
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        String saveCurr = currPass.getText().toString();
        String saveNew = newPass.getText().toString();
        savedInstanceState.putString(TAG8, saveCurr);
        savedInstanceState.putString(TAG9, saveNew);
        super.onSaveInstanceState(savedInstanceState);
    }

}
