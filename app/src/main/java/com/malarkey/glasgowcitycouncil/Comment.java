package com.malarkey.glasgowcitycouncil;

public class Comment {

    private String usersComment;
    private Double usersRating;
    private String username;
    private String articleName;

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // CONSTRUCTORS
    public Comment(String usersComment, Double usersRating,  String username, String articleName) {
        this.usersComment = usersComment;
        this.usersRating = usersRating;
        this.username = username;
        this.articleName = articleName;
    }

    public Comment() {
        this.usersComment = "";
        this.usersRating = 0.0;
    }

    // GETTERS AND SETTER
    public String getUsersComment() {
        return usersComment;
    }

    public void setUsersComment(String usersComment) {
        this.usersComment = usersComment;
    }

    public Double getUsersRating() {
        return usersRating;
    }

    public void setUsersRating(Double usersRating) {
        this.usersRating = usersRating;
    }
}
