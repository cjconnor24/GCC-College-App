package com.malarkey.glasgowcitycouncil;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private DBHandler db;
    private SQLiteDatabase myDatabase;
    private Context myContext = this;

    public void btnSubmit_onClick(View v) {


        // GET THE TEXT FIELDS FROM THE LAYOUT
        EditText username = (EditText) findViewById(R.id.txtUsername);
        EditText password = (EditText) findViewById(R.id.txtPassword);

        // GET THE VALUES FROM THE TEXT BOX OBJECTS
        String user = username.getText().toString();
        String pass = password.getText().toString();

        // CREATE LOGIN OBJECT
        Login login = new Login(user, pass);

        // GET DATABASE CONNECTION
        db = new DBHandler(myContext);
        myDatabase = db.getReadableDatabase();

        // CHECK TO SEE IF VALID DETAILS
        if (db.validateLogin(user, pass)) {

            // PROCEED TO MAIN MENU
            Intent myIntent = new Intent(LoginActivity.this, MainMenu.class);
            myIntent.putExtra("username", user);
            startActivity(myIntent);

        } else {

            // DETAILS DON'T EXIST OR ARE INCORRECT
            // UPDATE USER
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout_error,
                    (ViewGroup) findViewById(R.id.toast_layout_root));

            TextView text = (TextView) layout.findViewById(R.id.text);
            TextView heading = (TextView) layout.findViewById(R.id.txtHeading);
            heading.setText("Oops...");
            text.setText("Your username and / or password weren't right.\nDouble check your credentials");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

        }

    }

    public void btnRegister_onClick(View v) {

        // GO TO REGISTRATION
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent);

    }

//    public void runDBQuery(View v){
//
//        // GET DATABASE CONNECTION
//        db = new DBHandler(myContext);
//        myDatabase = db.getReadableDatabase();
//        db.populateDbDebug(myDatabase);
//
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent i = getIntent();
        boolean logout;
        logout = i.getBooleanExtra("logout", false);

        // IF USER HAS BEEN REDIRECTED FROM BEING LOGGED IN
        // UPDATE THEM THAT THEY'VE BEEN LOGGED OUT

        if (logout) {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.toast_layout,
                    (ViewGroup) findViewById(R.id.toast_layout_root));
            TextView text = (TextView) layout.findViewById(R.id.text);
            TextView header = (TextView) layout.findViewById(R.id.txtHeading);
            header.setText("Logged Out");
            text.setText("You have been successfully logged out.");

            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        }

    }
}
