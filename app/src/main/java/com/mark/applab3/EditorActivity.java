package com.mark.applab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditorActivity extends AppCompatActivity {

    private ArrayList<Note> notes;
    private Note note;
    private TextView dateTimeTextView;
    private EditText titleEditText, tagsEditText, mainEditText;
    private Boolean isNew;
    private DatabaseAdapter adapterDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        notes = MainActivity.notes;
        adapterDB = new DatabaseAdapter(this);

        dateTimeTextView = (TextView) findViewById(R.id.dateTimeTextView);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        tagsEditText = (EditText) findViewById(R.id.tagsEditText);
        mainEditText = (EditText) findViewById(R.id.mainEditText);

        Bundle arguments = getIntent().getExtras();
        isNew = arguments.containsKey("isNew");

        if (isNew) {
            note = new Note();
        }
        else {
            int position = arguments.getInt("position");
            note = notes.get(position);
            Log.wtf(TAG, "position: " + position);
        }

        dateTimeTextView.setText(note.getDatetimeToString());
        titleEditText.setText(note.getTitle());
        tagsEditText.setText(note.getTags());
        mainEditText.setText(note.getText());

        Log.wtf(TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // one inherited from android.support.v4.app.FragmentActivity
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_delete :
                if(!isNew) {
                    adapterDB.open();
                    adapterDB.delete(note.getId());
                    adapterDB.close();
                }
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private final static String TAG = "EditorActivity";
    @Override
    protected void onPause(){
        super.onPause();
        if (!note.getTitle().equals(titleEditText.getText().toString()) ||
                !note.getTags().equals(tagsEditText.getText().toString()) ||
                !note.getText().equals(mainEditText.getText().toString()))
        {
            note.setTitle(titleEditText.getText().toString());
            note.setTags(tagsEditText.getText().toString());
            note.setText(mainEditText.getText().toString());
            note.updateDatetime();
            adapterDB.open();
            if(isNew){
                if(!note.getTitle().isEmpty()) {
                    notes.add(0, note);
                    adapterDB.insert(note);
                }
            }
            else {
                adapterDB.update(note);
            }
            adapterDB.close();
        }

        Log.wtf(TAG, "onPause");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.wtf(TAG, "onDestroy");
    }
    @Override
    protected void onStop(){
        super.onStop();
        Log.wtf(TAG, "onStop");
    }
    @Override
    protected void onStart(){
        super.onStart();
        Log.wtf(TAG, "onStart");
    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.wtf(TAG, "onResume");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.wtf(TAG, "onRestart");
    }

    @Override
    protected  void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.wtf(TAG, "onSaveInstanceState");
    }

    @Override
    protected  void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.wtf(TAG, "onRestoreInstanceState");
    }
}
