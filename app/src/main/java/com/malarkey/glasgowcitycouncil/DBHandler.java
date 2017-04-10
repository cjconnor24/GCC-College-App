package com.malarkey.glasgowcitycouncil;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by 30179725 on 25/04/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    // DB SETUP CONFIG
    private final static String DATABASE_NAME = "GCCDatabase";
    //private final String TABLE_LOGIN = "Login";

    private final String TABLE_LOGIN = "Login";
    private final String LOGIN_USERNAME = "Username";
    private final String LOGIN_PASSWORD = "Password";

    private final String TABLE_USER = "User";
    private final String USER_LOGINDETAILS = "Username";
    private final String USER_FIRSTNAME = "FirstName";
    private final String USER_LASTNAME = "LastName";
    private final String USER_EMAILADDRESS = "EmailAddress";

    private final String TABLE_ARTICLE = "Article";
    private final String ARTICLE_ARTICLENAME = "ArticleName";
    private final String ARTICLE_ARTICLECONTENT = "ArticleContent";

    private final String TABLE_COMMENT = "Comment";
    private final String COMMENT_USERCOMMENT = "UserComment";
    private final String COMMENT_USERRATING = "UserRating";
    private final String COMMENT_USERNAME = "Username";
    private final String COMMENT_ARTICLENAME = "ArticleName";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + " (" +
                LOGIN_USERNAME + " TEXT PRIMARY KEY, " +
                LOGIN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_LOGIN_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                USER_LOGINDETAILS + " TEXT PRIMARY KEY, " +
                USER_FIRSTNAME + " TEXT, " +
                USER_LASTNAME + " TEXT, " +
                USER_EMAILADDRESS + " TEXT)";
        db.execSQL(CREATE_USER_TABLE);

        String CREATE_ARTICLE_TABLE = "CREATE TABLE " + TABLE_ARTICLE + " (" +
                ARTICLE_ARTICLENAME + " TEXT PRIMARY KEY, " +
                ARTICLE_ARTICLECONTENT + " TEXT)";
        db.execSQL(CREATE_ARTICLE_TABLE);

        String CREATE_COMMENT_TABLE = "CREATE TABLE " + TABLE_COMMENT + " (" +
                COMMENT_USERCOMMENT + " TEXT, " +
                COMMENT_USERRATING + " DECIMAL(2,1), " +
                COMMENT_USERNAME + " TEXT, " +
                COMMENT_ARTICLENAME + " TEXT, PRIMARY KEY (Username, ArticleName))";
        db.execSQL(CREATE_COMMENT_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {
//        String deleteTable = "DROP TABLE IF EXISTS " + TABLE_LOGIN;
//        db.execSQL(deleteTable);
//        onCreate(db);
    }

    // ADD LOGIN TO DB
    public void addLogin(Login login, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(LOGIN_USERNAME, login.getUsername());
        values.put(LOGIN_PASSWORD, login.getPassword());

        database.insert(TABLE_LOGIN, null, values);
    }

    // ADD USER TO DATABASE
    public void addUser(User user, SQLiteDatabase database) {
        ContentValues values = new ContentValues();
        values.put(USER_LOGINDETAILS, user.getLoginDetails().getUsername());
        values.put(USER_FIRSTNAME, user.getFirstName());
        values.put(USER_LASTNAME, user.getLastName());
        values.put(USER_EMAILADDRESS, user.getEmailAddress());

        database.insert(TABLE_USER, null, values);
    }

    // ADD COMMENTS TO DATABASE
    public void addComment(Comment comment, SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(COMMENT_USERCOMMENT, comment.getUsersComment());
        values.put(COMMENT_USERRATING, comment.getUsersRating());
        values.put(COMMENT_USERNAME, comment.getUsername());
        values.put(COMMENT_ARTICLENAME, comment.getArticleName());

        database.insert(TABLE_COMMENT, null, values);

    }

    // VALIDATE THE USER LOGIN AND MAKE SURE EXISTS IN DATABASE
    public boolean validateLogin(String username, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Login WHERE Username='" +
                username + "' AND Password= '" + password + "'", null);

        if (cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }

    }

    public boolean doesUserExist(String username) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Username FROM " + TABLE_USER + " WHERE " + LOGIN_USERNAME + " = '" + username + "'", null);

        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }

    }

    public ArrayList<String> getTopRated() {

        ArrayList<String> topRated = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(UserRating), ArticleName FROM Comment GROUP BY ArticleName ORDER BY AVG(UserRating) DESC LIMIT 0,5", null);

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String articleName = cursor.getString(cursor.getColumnIndex("ArticleName"));
                    topRated.add(articleName);

                } while (cursor.moveToNext());
            } else {
                return topRated;
            }

        } else {
            return topRated;
        }
        return topRated;

    }

    public boolean alreadyCommented(String username, String articlename) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Username, ArticleName FROM " + TABLE_COMMENT + " WHERE " + COMMENT_USERNAME + " = '" + username + "' AND " + COMMENT_ARTICLENAME + " = '" + articlename + "'", null);

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // RETRIEVE LOGIN FROM THE DATABASE BASED ON THE USERNAME
    public Login getLogin(String username) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_LOGIN + " WHERE " + LOGIN_USERNAME + " = " + username, null);

        if (cursor != null) {

            cursor.moveToFirst();
            Login login = new Login(cursor.getString(0), cursor.getString(1));

            return login;

        } else {

            return null;

        }

    }

    public double getAverage(String articleName) {

        SQLiteDatabase db = this.getReadableDatabase();
        Double avg = 0.0;
        Cursor cursor = db.rawQuery("SELECT AVG(UserRating) as articleAverage FROM " + TABLE_COMMENT + " WHERE " + COMMENT_ARTICLENAME + " = '" + articleName + "'", null);

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            avg = cursor.getDouble(cursor.getColumnIndex("articleAverage"));
        }

        cursor.close();
        db.close();

        return avg;

    }

    public Article getArticle(String articleName) {

        SQLiteDatabase db = this.getReadableDatabase();

        //String articleName;
        String articleContent;
        Article article = new Article();

        Cursor cursor = db.rawQuery("SELECT ArticleContent FROM " + TABLE_ARTICLE + " WHERE " + ARTICLE_ARTICLENAME + " = '" + articleName + "'", null);

        if (cursor.getCount() == 1) {
            cursor.moveToFirst();
            articleContent = cursor.getString(cursor.getColumnIndex("ArticleContent"));
            article = new Article(articleContent, articleName);
        }

        return article;
    }

    // GET ARRAY LIST OF COMMENTS FROM THE DATABASE BASED ON ARTICLE NAME
    public ArrayList<Comment> getComments(String articleName) {
        ArrayList<Comment> comments = new ArrayList<Comment>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Comment WHERE ArticleName = '" + articleName + "'", null);

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String usersComment = cursor.getString(cursor.getColumnIndex("UserComment"));
                    Double usersRating = cursor.getDouble(cursor.getColumnIndex("UserRating"));
                    String username = cursor.getString(cursor.getColumnIndex("Username"));
                    String article = cursor.getString(cursor.getColumnIndex("ArticleName"));

                    Comment foundComment = new Comment(usersComment, usersRating, username, article);
                    comments.add(foundComment);

                } while (cursor.moveToNext());
            } else {
                return comments;
            }
        } else {
            return comments;
        }
        return comments;

    }

}
