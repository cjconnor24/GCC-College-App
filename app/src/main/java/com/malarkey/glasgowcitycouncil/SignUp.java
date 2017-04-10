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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    DBHandler db;
    private SQLiteDatabase myDatabase;
    private Context myContext = this;

    public void btnConfirm_onClick(View v) {

        try {

            EditText username = (EditText) findViewById(R.id.txtUsername);
            EditText password = (EditText) findViewById(R.id.txtPassword);
            EditText confPassword = (EditText) findViewById(R.id.txtConfPassword);
            EditText firstName = (EditText) findViewById(R.id.txtFirstName);
            EditText lastName = (EditText) findViewById(R.id.txtLastName);
            EditText email = (EditText) findViewById(R.id.txtEmail);

            TextView confirm = (TextView) findViewById(R.id.lblErrorMessage);


            String userName = (username.getText()).toString();
            String Password = (password.getText()).toString();
            String ConfPassword = (confPassword.getText()).toString();
            String FirstName = (firstName.getText()).toString();
            String LastName = (lastName.getText()).toString();
            String Email = (email.getText()).toString();
            ArrayList<String> errors = new ArrayList<>();

            if (userName.equals("") || Password.equals("") || ConfPassword.equals("") || FirstName.equals("") || LastName.equals("")
                    || Email.equals("")) {
                errors.add("Please make sure all fields have been completed.");
            }


            db = new DBHandler(myContext);
            myDatabase = db.getWritableDatabase();

            // CHECK TO SEE IF USER EXISTS
            if (db.doesUserExist(userName)) {
                errors.add("That username already exists");
            }

            // CHECK PASSWORD IS ENTERED AND FOLLOWS SPECIFICATION
            if (!ConfPassword.equals(Password)) {
                errors.add("Your passwords do not match");
            } else {

                Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$");
                Matcher passwordMatch = passwordPattern.matcher(Password);

                if (!passwordMatch.find()) {
                    errors.add("Your password must contain at least one capital and one number.");
                }
            }

            if (errors.size() == 0) {


                Login newLogin = new Login(userName, Password);
                User newUser = new User(newLogin, FirstName, LastName, Email);

                db = new DBHandler(myContext);

                myDatabase = db.getWritableDatabase();
                db.addLogin(newLogin, myDatabase);
                db.addUser(newUser, myDatabase);

                username.setText("");
                password.setText("");
                confPassword.setText("");
                firstName.setText("");
                lastName.setText("");
                email.setText("");
                confirm.setText("");


                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.toast_layout_root));
                TextView text = (TextView) layout.findViewById(R.id.text);
                TextView header = (TextView) layout.findViewById(R.id.txtHeading);
                header.setText("Account Created");
                text.setText("You have been successfully registered. Please continue to the login screen.");

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();


            } else {

                //errors.add("Your passwords do not match");

                String errorString = "";

                for (String errorLine : errors) {
                    errorString += errorLine + "\n\n";
                }
                confirm.setText(errorString);

                // THE INPUT CANNOT BE BLANK
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout_error,
                        (ViewGroup) findViewById(R.id.toast_layout_root));

                TextView text = (TextView) layout.findViewById(R.id.text);
                TextView heading = (TextView) layout.findViewById(R.id.txtHeading);
                heading.setText("Oops...");
                text.setText(errorString);

                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();


            }

        } catch (Exception ex) {
            TextView confirm = (TextView) findViewById(R.id.lblErrorMessage);
            confirm.setText("Problem Signing Up");
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void btnGoBack_onClick(View v) {
        Intent i = new Intent(SignUp.this, LoginActivity.class);
        startActivity(i);
    }

}
