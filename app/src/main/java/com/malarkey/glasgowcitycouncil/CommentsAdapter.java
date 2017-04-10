package com.malarkey.glasgowcitycouncil;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by chrisconnor on 5/16/16.
 */
public class CommentsAdapter extends ArrayAdapter<Comment> {

    public CommentsAdapter(Context context, ArrayList<Comment> comments) {
        super(context, 0, comments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Get the data item for this position
        Comment comment = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.comment_list, parent, false);
        }
        // Lookup view for data population
        TextView commentLine = (TextView) convertView.findViewById(R.id.lblCommentLine);
        TextView userName = (TextView) convertView.findViewById(R.id.lblCommentUsername);
        RatingBar ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

        // Populate the data into the template view using the data object
        commentLine.setText("\"" + comment.getUsersComment() + "\"");
        userName.setText(comment.getUsername());
        ratingBar.setRating(Float.parseFloat(comment.getUsersRating().toString()));
        // Return the completed view to render on screen
        return convertView;
    }

}
