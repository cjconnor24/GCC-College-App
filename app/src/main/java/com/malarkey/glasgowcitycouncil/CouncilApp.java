package com.malarkey.glasgowcitycouncil;

import java.util.HashMap;

public class CouncilApp {

    private HashMap<String, User> users;
    private HashMap<String, Article> articles;

    // CONSTRUCTORS
    public CouncilApp() {
        users = new HashMap<String, User>();
        articles = new HashMap<String, Article>();
    }

    public CouncilApp(HashMap<String, User> users) {

        this.users = users;
    }

    // GETTERS AND SETTERS
    public HashMap<String, Article> getArticles() {
        return articles;
    }

    public void setArticles(HashMap<String, Article> articles) {
        this.articles = articles;
    }

    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

}
