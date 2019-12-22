package com.mark.applab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NotesGridProvider extends ArrayAdapter<Note> {

    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Note> productList;

    NotesGridProvider(Context context, int resource, ArrayList<Note> products) {
        super(context, resource, products);
        this.productList = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Note note = productList.get(position);

        viewHolder.titleView.setText(note.getTitle());
        viewHolder.tagsView.setText(note.getTags());
        viewHolder.dateView.setText(note.getDatetimeToString());

        return convertView;
    }

    private String formatValue(int count, String unit){
        return String.valueOf(count) + " " + unit;
    }
    private class ViewHolder {
        final TextView titleView, dateView, tagsView;
        ViewHolder(View view){
            titleView = (TextView) view.findViewById(R.id.textViewNoteGrid);
            tagsView = (TextView) view.findViewById(R.id.textViewTagsGrid);
            dateView = (TextView) view.findViewById(R.id.textViewDateTimeGrid);
        }
    }
}
