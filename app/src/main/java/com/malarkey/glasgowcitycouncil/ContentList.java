package com.malarkey.glasgowcitycouncil;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContentList extends ListActivity {

    // GLOBAL VARS FOR USE IN VARIOUS METHODS
    String username;
    String listpage;
    String breadcrumb;
    SQLiteDatabase myDatabase;
    Context myContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // GET THE DATA FROM THE PREVIOUS INTENTS
        Intent i = getIntent();
        listpage = i.getStringExtra("listpage");
        username = i.getStringExtra("username");
        breadcrumb = i.getStringExtra("breadcrumb");

        // SETUP ARRAY TO STORE PAGES
        String[] page_list;

        // IF TOP RATED
        if (listpage.equals("toprated")) {

            // GOT THE HIGHEST 5 RATED PAGES FROM THE DATABASE BASED ON USER RATING
            DBHandler db = new DBHandler(myContext);
            myDatabase = db.getReadableDatabase();
            ArrayList<String> topRated = db.getTopRated();
            page_list = topRated.toArray(new String[topRated.size()]);

        } else {

            // GET THE LISTINGS FROM THE RESOURCES FILES - BUILD THE NAME USING THE intent String
            int listResource = getResources().getIdentifier(listpage, "array", getPackageName());
            page_list = getResources().getStringArray(listResource);

        }

        // CREATE THE LIST VIEW
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.content_list, R.id.lblArticleName, page_list));
        ListView lv = getListView();

        // EVENT LISTENER FOR WHICH ELEMENT IS BEING CLICKED
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // GET THE NAME SO IT CAN BE PASSED TO THE CONTENT ACTIVITY
                TextView textView = (TextView) view.findViewById(R.id.lblArticleName);
                String articleName = textView.getText().toString();

                // SEND THE DATA TO THE NEW ACTIVITY
                Intent i = new Intent(getApplicationContext(), ContentPage.class);

                // SEND VALUES TO NEW ACTIVITY
                i.putExtra("username", username);
                i.putExtra("article", articleName);
                i.putExtra("breadcrumb", breadcrumb);

                startActivity(i);

            }
        });

    }
}
