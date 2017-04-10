package com.malarkey.glasgowcitycouncil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContentPage extends Activity {

    private Context myContext = this;
    private SQLiteDatabase myDatabase;
    private String articleName;
    private String username;
    private String breadcrumb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_content);


        // GET DATA FROM INTENT
        Intent i = getIntent();
        articleName = i.getStringExtra("article");
        username = i.getStringExtra("username");
        breadcrumb = i.getStringExtra("breadcrumb");


        // BUILD THE MAIN IMAGE RESOURCE
        Resources res = getResources();
        String mDrawableName = articleName.replace(" ", "_").toLowerCase();
        int resID = res.getIdentifier(mDrawableName, "drawable", getPackageName());
        Drawable drawable = res.getDrawable(resID);
        ImageView articleImage = (ImageView) findViewById(R.id.imageView);
        articleImage.setImageDrawable(drawable);


        // SETUP THE DATABASE INSTANCE
        DBHandler db = new DBHandler(myContext);
        myDatabase = db.getReadableDatabase();

        // GET THE RATING
        double avgRating = db.getAverage(articleName);
        Article currentArticle = db.getArticle(articleName);

        // GET THE COMMENTS FROM THE DB
        ArrayList<Comment> arrayOfComments = new ArrayList<Comment>();
        arrayOfComments = db.getComments(articleName);

        // IF THERE ARE COMMENTS
        if (arrayOfComments.size() > 0) {


            // CREATE ADAPTER TO LIST COMMENTS
            CommentsAdapter adapter = new CommentsAdapter(this, arrayOfComments);

            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.commentListing);
            listView.setAdapter(adapter);

        } else {

            // HIDE THE VIEW COMMENTS BUTTONS
            View btnViewComments = findViewById(R.id.btnViewAllComments);
            btnViewComments.setVisibility(View.GONE);

            // OUTPUT TO UI RE: NO COMMENTS
            TextView lblOutput = (TextView) findViewById(R.id.lblOutput);
            lblOutput.setText("There are no reviews yet.");

        }

        // CHECK IF USER HAS ALREADY COMMENTED
        if (db.alreadyCommented(username, articleName) == true) {
            View commentSection = findViewById(R.id.commentSection);
            commentSection.setVisibility(View.GONE);
        }


        // FIND AND UPDATE THE UI ELEMENTS WITH CONTENT
        TextView contentHeading = (TextView) findViewById(R.id.lblContentHeading);
        TextView contentBody = (TextView) findViewById(R.id.lblBody);
        TextView lblStarRating = (TextView) findViewById(R.id.lblStarRating);
        TextView lblBreadCrumb = (TextView) findViewById(R.id.lblBreadCrumb);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.avgRating);

        contentHeading.setText(currentArticle.getArticleName());
        contentBody.setText(currentArticle.getArticleContents());
        lblStarRating.setText(Double.toString(avgRating).substring(0, 3));
        ratingBar.setRating(Float.parseFloat(Double.toString(avgRating)));
        lblBreadCrumb.setText(breadcrumb + " > " + articleName);


    }


    // ADD A COMMENT TO THE DATABASE FOR THIS PAGE
    public void addNewComment(View v) {

        try {

            // SETUP THE LAYOUT ELEMENTS AND GET THE VALUES
            EditText txtCommentInput = (EditText) findViewById(R.id.txtCommentInput);
            RatingBar commentRating = (RatingBar) findViewById(R.id.commentRating);
            String comment = txtCommentInput.getText().toString();
            Double rating = Double.valueOf(commentRating.getRating());

            // SO LONG AS COMMENT HAS BEEN FILLED IN
            if (!comment.equals("")) {

                // BUILD THE COMMENT OBJECT AND ADD TO THE DATABASE
                Comment newComment = new Comment(comment, rating, username, articleName);

                DBHandler db = new DBHandler(myContext);
                myDatabase = db.getReadableDatabase();
                db.addComment(newComment, myDatabase);

                // ONE ADDED, HIDE THE SECTION SO THEY CAN'T COMMENT AGAIN
                View commentSection = findViewById(R.id.commentSection);
                commentSection.setVisibility(View.GONE);

                // LET USER KNOW RATING WAS ADDED
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                TextView text = (TextView) layout.findViewById(R.id.text);
                text.setText("Thanks for your review, " + username);

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            } else {

                // THE INPUT CANNOT BE BLANK
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout_error,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                TextView heading = (TextView) layout.findViewById(R.id.txtHeading);
                heading.setText("Oops...");
                text.setText("Your review cannot be blank.");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            }


        } catch (Exception ex) {
//            TextView txt = (TextView) findViewById(R.id.lblOutput);
//            txt.setText("Problem submitting Comment");
        }

    }

    public void backButton(View v) {
        this.finish();
    }

    public void viewComments(View v) {

        // SEND THE DATA TO THE NEW ACTIVITY
        Intent i = new Intent(getApplicationContext(), CommentListing.class);
        i.putExtra("article", articleName);
        startActivity(i);

    }
}
