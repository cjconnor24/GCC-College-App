package com.malarkey.glasgowcitycouncil;

public class User {

    private Login loginDetails;
    private String firstName;
    private String lastName;
    private String emailAddress;

    // CONSTRUCTORS
    public User() {
        emailAddress = "";
        firstName = "";
        lastName = "";
        loginDetails = null;
    }

    public User(Login loginDetails) {
        emailAddress = "";
        firstName = "";
        lastName = "";
        this.loginDetails = loginDetails;
    }

    public User(Login loginDetails, String firstName, String lastName, String emailAddress) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginDetails = loginDetails;
    }

    // GETTERS AND SETTERS
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Login getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(Login loginDetails) {
        this.loginDetails = loginDetails;
    }
}
