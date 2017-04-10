package com.malarkey.glasgowcitycouncil;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainMenu extends AppCompatActivity {

    private String username;

    private void backButtonClick(View v){
        this.finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        try {

            // GET USERNAME FROM INTENT
            Intent i = getIntent();
            username = i.getStringExtra("username");

            // IF THEY'RE LOGGED IN, DISPLAY THEIR LOGIN DETAILS
            if (!username.equals("")) {
                View usernameSection = findViewById(R.id.loggedInAs);
                usernameSection.setVisibility(View.VISIBLE);
                TextView UserName = (TextView) findViewById(R.id.lblUserName);
                UserName.setText(username);
                i.putExtra("username", username);
            }

            // PARK BUTTON
            ImageButton btnParks = (ImageButton) findViewById(R.id.btnParks);
            btnParks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getApplicationContext(), ContentList.class);
//                // sending data to new activity
                    i.putExtra("listpage", "park_list");
                    i.putExtra("username", username);
                    i.putExtra("breadcrumb", "Parks");
                    startActivity(i);
                }
            });

            // CULTURE BUTTON
            ImageButton btnCulture = (ImageButton) findViewById(R.id.btnCulture);
            btnCulture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getApplicationContext(), ContentList.class);
//                // sending data to new activity
                    i.putExtra("listpage", "museum_list");
                    i.putExtra("username", username);
                    i.putExtra("breadcrumb", "Culture");
                    startActivity(i);
                }
            });

            // EVENTS BUTTON
            ImageButton btnEvents = (ImageButton) findViewById(R.id.btnEvents);
            btnEvents.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getApplicationContext(), ContentList.class);
//                // sending data to new activity
                    i.putExtra("listpage", "event_list");
                    i.putExtra("username", username);
                    i.putExtra("breadcrumb", "Events");
                    startActivity(i);
                }
            });

            // SPORT BUTTON
            ImageButton btnSport = (ImageButton) findViewById(R.id.btnSport);
            btnSport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getApplicationContext(), ContentList.class);
                    // sending data to new activity
                    i.putExtra("listpage", "sport_list");
                    i.putExtra("username", username);
                    i.putExtra("breadcrumb", "Sport");
                    startActivity(i);
                }
            });

            // TOP RATED BUTTON
            ImageButton btnTopRated = (ImageButton) findViewById(R.id.btnTopRated);
            btnTopRated.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Launching new Activity on selecting single List Item
                    Intent i = new Intent(getApplicationContext(), ContentList.class);

                    // sending data to new activity
                    i.putExtra("listpage", "toprated");
                    i.putExtra("breadcrumb", "Top Rated");
                    i.putExtra("username", username);
                    startActivity(i);

                }
            });

        } catch(Exception e){

        }

    }

    // LOGOUT BUTTON, SEND TO LOGIN SCREEN
    public void btnLogout(View v){
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        i.putExtra("logout",true);
        startActivity(i);
    }
}
