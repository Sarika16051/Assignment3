package com.example.sarika.myapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;


public class FileSaving extends ActionBarActivity {

    public static final String TAG3 = "FileText";
    private static final String filepath = "myInternalDir";
    private static final String filename = "MyFile.txt";
    private static final String LOG_TAG = "FileSaving";
    private Button saveInternal;
    private Button saveExternal;
    private EditText addText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_saving);


        addText = (EditText) findViewById(R.id.noteEdit);
        saveInternal = (Button) findViewById(R.id.saveInternal);
        saveExternal = (Button) findViewById(R.id.saveExternal);

        if (savedInstanceState != null) {
            String savedText = savedInstanceState.getString(TAG3);
            addText.setText(savedText);
        }

        //Saving to internal storage when Save on Internal storage button clicked
        saveInternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContextWrapper context = new ContextWrapper(getApplicationContext());
                File dir = context.getDir(filepath, Context.MODE_PRIVATE);
                File internalFile = new File(dir, filename);
                try {
                    FileOutputStream fos = new FileOutputStream(internalFile);
                    fos.write(addText.getText().toString().getBytes());
                    fos.close();
                    System.out.println("Added to internal storage successfully");
                } catch (Exception e) {
                    System.out.println("Exception occurred..." + e);
                }

            }
        });

        //Saving to External storage when save on external storage button clicked
        saveExternal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = addText.getText().toString();
                //checking if app has permission to write to external storage
                if (isExternalStorageWritable()) {
                    File sdcard = Environment.getExternalStorageDirectory();
                    File dir = new File(sdcard.getAbsolutePath() + filepath);
                    dir.mkdir();
                    File myExternalFile = new File(getExternalFilesDir(filepath), filename);

                    try {
                        FileOutputStream fos = new FileOutputStream(myExternalFile);
                        System.out.println("I am in save on external file");
                        fos.write(data.getBytes());
                        fos.close();
                        System.out.println("Added successfully to external storage");
                    } catch (Exception e) {
                        System.out.println("Exception occurred..." + e);
                    }
                    //checking if file created successfully
                    if (myExternalFile.exists()) {
                        System.out.println("File exists");
                    }
                } else {
                    System.out.println("external storage is not writable");
                }


            }
        });


    }


    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


    public File getAlbumStorageDir(String albumName) {
// Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e(LOG_TAG, "Directory not created");
        }
        return file;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_file_saving, menu);
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
        String saveText = addText.getText().toString();
        savedInstanceState.putString(TAG3, saveText);
        super.onSaveInstanceState(savedInstanceState);
    }


}
