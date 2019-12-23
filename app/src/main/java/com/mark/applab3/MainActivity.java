package com.mark.applab3;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener {

    static public ArrayList<Note> notes;
    private ArrayAdapter<Note> adapter;
    private DatabaseAdapter adapterDB;
    private Boolean sortAsc = true;

    private Runnable run = new Runnable() {
        public void run() {
            adapter.notifyDataSetChanged();
            noteView.invalidateViews();
            noteView.refreshDrawableState();
        }
    };

    static {
        notes = new ArrayList<Note>();
        notes.add(new Note("Картофель", "кг.", "text1"));
        notes.add(new Note("Чай", "шт.", "text2"));
        notes.add(new Note("Яйца", "шт.", "text3"));
        notes.add(new Note("Молоко", "л.", "tex3"));
        notes.add(new Note("Макароны", "кг.", "text4"));

        notes.add(new Note("пельмени \"Бабушка Аня\"", "шт.", "минск"));
        notes.add(new Note("Дилдо", "шт.", "XXX"));
        notes.add(new Note("сосиски", "шт.", "Zzz"));
        notes.add(new Note("Лук", "шт.", "гродно"));
        notes.add(new Note("Чеснок", "шт.", "hdpi"));
    }

    ListView noteList;
    GridView noteGrid;
    AbsListView noteView;
    EditText searchTags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("isNew", 1);
                startActivity(intent);
            }
        });

        searchTags = (EditText) findViewById(R.id.searchTagsEditText);
        searchTags.addTextChangedListener(new TextWatcher() {

            // the user's changes are saved here
            public void onTextChanged(CharSequence c, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                adapterDB.setFilter(c.toString());
                UpdateData();
                runOnUiThread(run);
            }
        });

        adapterDB = new DatabaseAdapter(this);
        UpdateData();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            noteView = noteList = (ListView) findViewById(R.id.list);
            adapter = new NotesProvider(this, R.layout.note_list_item, notes);
        }
        else{
            noteView = noteGrid = (GridView) findViewById(R.id.grid);
            adapter = new NotesProvider(this, R.layout.note_grid_item, notes, true);
        }

        noteView.setAdapter(adapter);
        noteView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // по позиции получаем выбранный элемент
                Note selectedItem = notes.get(position);
                //Toast.makeText(getApplicationContext(),selectedItem.getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        Log.wtf(TAG, "onCreate");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.sort_by_date :
                Toast.makeText(getApplicationContext(),"Сортировка по датам", Toast.LENGTH_SHORT).show();
                adapterDB.setSort(1);
                UpdateData();
                runOnUiThread(run);
                return true;
            case R.id.sort_by_title:
                Toast.makeText(getApplicationContext(),"Сортировка по заголовкам",Toast.LENGTH_SHORT).show();
                adapterDB.setSort(2);
                UpdateData();
                runOnUiThread(run);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void openEditorForNewNote(View view) {
    }

    private void UpdateData() {
        adapterDB.open();
        notes.clear();
        notes.addAll(adapterDB.getNotes());
        adapterDB.close();
    }

    private final static String TAG = "MainActivity";
    @Override
    protected void onPause(){
        super.onPause();
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
        UpdateData();
        runOnUiThread(run);
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
