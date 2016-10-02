package com.example.sarika.myapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "MainActivity";
    public static final String MY_PREF = "MyPref";
    private String pass;
    private EditText password;
    private Button next_button;
    private Button createPass;
    private Button modifyPass;
    private SharedPreferences shared;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = (EditText) findViewById(R.id.passText);
        next_button = (Button) findViewById(R.id.nextButton);
        createPass = (Button) findViewById(R.id.createPass);
        modifyPass = (Button) findViewById(R.id.modifyPass);

        createDatabase();
        if (savedInstanceState != null) {
            String restoreInstance = savedInstanceState.getString(TAG);
            password.setText(restoreInstance);
        } else {
            shared = getSharedPreferences(MY_PREF, MODE_PRIVATE);
            if (shared.contains("pass")) {
                String restoredText = shared.getString("pass", " ");
                password.setText(restoredText);
            }
        }

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pass = password.getText().toString();
                //using shared preferences to store value for password field
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("pass", pass);
                editor.commit();

                if (checkDatabase(pass)) {
                    Intent in = new Intent(MainActivity.this, Check.class);
                    startActivity(in);
                } else {
                    printIncorrectToast();
                }
            }
        });

        createPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, CreatePassword.class);
                startActivity(in);
            }
        });
        modifyPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(MainActivity.this, ModifyPassword.class);
                startActivity(in);
            }
        });

    }

    protected void printIncorrectToast() {
        Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT);
    }

    //method to check whether entered password occurs in database
    protected boolean checkDatabase(String checkPass) {
        Cursor c = db.rawQuery("SELECT * FROM password", null);
        if (c != null && c.getCount() > 0) {
            if (c.moveToFirst()) {
                if (c.getString(0).equals(checkPass)) {
                    return true;
                }
            }
        }
        return false;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        String savePass = password.getText().toString();
        savedInstanceState.putString(TAG, savePass);
        super.onSaveInstanceState(savedInstanceState);
    }
}
