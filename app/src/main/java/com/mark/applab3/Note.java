package com.mark.applab3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
//import java.sql.Date;

public class Note {
    public final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss");
    private long id;
    private String title;
    private String tags;
    private String text;
    private Date datetime;

    public Note (long id, String title, String tags, String text) {
        this(title, tags, text);
        this.id = id;
    }

    public Note (String title, String tags, String text) {
        this.title = title;
        this.tags = tags;
        this.text = text;
        this.datetime = new Date(); // this object contains the current date value
        //this.datetime = new Date(System.currentTimeMillis());
    }

    public Note (long id, String title, String tags, String text, Date datetime) {
        this(title, tags, text);
        this.id = id;
        this.datetime = datetime;
    }
    public Note (long id, String title, String tags, String text, String stringDatetime) {
        this(title, tags, text);
        this.id = id;
        setDatetimeFromString(stringDatetime);
    }

    public Note () {
        this.title = "";
        this.tags = "";
        this.text = "";
        //this.datetime = new Date(); // this object contains the current date value
        this.datetime = new Date(System.currentTimeMillis());
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDatetime() {
        return datetime;
    }

    public String getDatetimeToString() {
        return formatter.format(datetime);
    }

    public void setDatetimeFromString(String date) {
        try {
            this.datetime = (Date) formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void updateDatetime() {
        this.datetime = new Date();
    }

    @Override
    public String toString() {
        return "Title: " + this.title;
    }

}
