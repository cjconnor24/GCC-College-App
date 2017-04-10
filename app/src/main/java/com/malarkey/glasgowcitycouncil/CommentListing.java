package com.malarkey.glasgowcitycouncil;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CommentListing extends AppCompatActivity {

    private Context myContext = this;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_listing);

        // GET INTENT, PREVIOUS DATA
        Intent i = getIntent();

        // getting attached intent data
        String userName = i.getStringExtra("username");
        String articleName = i.getStringExtra("article");
        String breadcrumb = i.getStringExtra("breadcrumb");

        // UPDATE THE UI BASED ON THE ARTICLE NAME
        TextView lblarticleName = (TextView)findViewById(R.id.lblArticleName);
        lblarticleName.setText(articleName);


        // GET THE LIST OF COMMENTS FOR THAT ARTICLE FROM THE DATABASE
        DBHandler db = new DBHandler(myContext);
        myDatabase = db.getReadableDatabase();
        ArrayList<Comment> arrayOfComments = new ArrayList<Comment>();
        arrayOfComments = db.getComments(articleName);

        // IF THERE ARE COMMENTS STORED
        if(arrayOfComments.size() > 0) {


            // Create the adapter to convert the array to views
            CommentsAdapter adapter = new CommentsAdapter(this, arrayOfComments);

            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.commentListing);
            listView.setAdapter(adapter);

            TextView lblOutput = (TextView)findViewById(R.id.lblOutput);
            lblOutput.setText("Here is what other people are saying about " + articleName + ".");

        } else {

            // UPDATE UI TO SAY THERE AREN"T ANY YET
            TextView lblOutput = (TextView)findViewById(R.id.lblOutput);
            lblOutput.setText("There are no comments yet. Be the first to review " + articleName + "!");

        }

    }

    public void backButton(View v) {
        this.finish();
    }
}
