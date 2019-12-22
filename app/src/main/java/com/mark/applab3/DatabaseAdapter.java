package com.mark.applab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DatabaseAdapter {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private String sortString;
    private String filterString = "";

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context.getApplicationContext());
    }

    public DatabaseAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    private Cursor getAllEntries(){
        String[] columns = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_TAGS, DatabaseHelper.COLUMN_TEXT, DatabaseHelper.COLUMN_DATE};
        return  database.query(DatabaseHelper.TABLE, columns, DatabaseHelper.COLUMN_TAGS + " LIKE '%" + filterString + "%'", null, null, null, sortString);
    }

    public ArrayList<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<>();
        Cursor cursor = getAllEntries();
        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID));
                String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
                String tags = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TAGS));
                String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
                String datetime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
                notes.add(new Note(id, title, tags, text, datetime));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  notes;
    }

    public long getCount(){
        return DatabaseUtils.queryNumEntries(database, DatabaseHelper.TABLE);
    }

    public void setSort(int sort) {
        if(sort == 1)
            this.sortString = DatabaseHelper.COLUMN_TITLE + " ASC";
        else if (sort == 2) {
            this.sortString = DatabaseHelper.COLUMN_DATE + " ASC";
        }

    }

    public Note getNote(long id){
        Note note = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?",DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(id)});
        if(cursor.moveToFirst()){
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
            String tags = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TAGS));
            String text = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TEXT));
            String datetime = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_DATE));
            note = new Note(id, title, tags, text, datetime);
        }
        cursor.close();
        return  note;
    }

    public long insert(Note user){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TITLE, user.getTitle());
        cv.put(DatabaseHelper.COLUMN_TAGS, user.getTags());
        cv.put(DatabaseHelper.COLUMN_TEXT, user.getText());
        cv.put(DatabaseHelper.COLUMN_DATE, user.getDatetimeToString());

        return  database.insert(DatabaseHelper.TABLE, null, cv);
    }

    public long delete(long noteId){
        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(noteId)};
        return database.delete(DatabaseHelper.TABLE, whereClause, whereArgs);
    }

    public long update(Note note){
        String whereClause = DatabaseHelper.COLUMN_ID + "=" + String.valueOf(note.getId());
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_TITLE, note.getTitle());
        cv.put(DatabaseHelper.COLUMN_TAGS, note.getTags());
        cv.put(DatabaseHelper.COLUMN_TEXT, note.getText());
        cv.put(DatabaseHelper.COLUMN_DATE, note.getDatetimeToString());
        return database.update(DatabaseHelper.TABLE, cv, whereClause, null);
    }

    public void setFilter(String filter) {
        this.filterString = filter;
    }
}