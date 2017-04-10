package com.malarkey.glasgowcitycouncil;

/**
 * Created by 30179725 on 21/03/2016.
 */
public class Article {

    private String articleName;
    private String articleContents;

    // CONSTRUCTORS
    public Article() {

        articleName = "";
        articleContents = "";

    }

    public Article(String articleContents, String articleName) {

        this.articleContents = articleContents;
        this.articleName = articleName;
    }

    // GETTERS AND SETTERS
    public String getArticleContents() {

        return articleContents;
    }

    public void setArticleContents(String articleContents) {
        this.articleContents = articleContents;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }
}
