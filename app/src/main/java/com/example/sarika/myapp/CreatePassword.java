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


public class CreatePassword extends ActionBarActivity {

    private static final String TAG7 = "Create Password";
    private EditText setPass;
    private Button createPass;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        setPass = (EditText) findViewById(R.id.createNewPass);
        createPass = (Button) findViewById(R.id.createPassButton);

        createDatabase();

        if (savedInstanceState != null) {
            String createNewPass = savedInstanceState.getString(TAG7);
            setPass.setText(createNewPass);
        }

        if (checkDatabase()) {
            Intent in = new Intent(CreatePassword.this, ModifyPassword.class);
            startActivity(in);
        } else {
            //setting new password
            createPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String pass = setPass.getText().toString();

                    if (pass.equals("")) {
                        fillAllFieldsToast();
                    } else {
                        String query = "INSERT INTO password VALUES('" + pass + "');";
                        db.execSQL(query);
                        System.out.println("password set successfully");
                    }
                }
            });
        }
    }

    protected void fillAllFieldsToast() {
        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT);
    }

    protected void createDatabase() {
        db = openOrCreateDatabase("MarksTable", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(RollNo INTEGER PRIMARY KEY,marks INTEGER);");
        db.execSQL("CREATE TABLE IF NOT EXISTS password(Password VARCHAR PRIMARY KEY);");
    }

    protected boolean checkDatabase() {
        Cursor c = db.rawQuery("SELECT * FROM password", null);
        if (c != null && c.getCount() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_password, menu);
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
        String savePass = setPass.getText().toString();
        savedInstanceState.putString(TAG7, savePass);
        super.onSaveInstanceState(savedInstanceState);
    }
}
