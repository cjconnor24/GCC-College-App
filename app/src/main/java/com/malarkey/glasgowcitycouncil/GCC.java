package com.malarkey.glasgowcitycouncil;

import android.app.Application;

/**
 * Created by chrisconnor on 5/22/16.
 */
public class GCC extends Application {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
