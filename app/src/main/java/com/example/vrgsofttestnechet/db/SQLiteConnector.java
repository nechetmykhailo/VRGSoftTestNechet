package com.example.vrgsofttestnechet.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteConnector extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public SQLiteConnector(Context context) {
        super(context, "News", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("create table NewsFavorites (_id integer primary key autoincrement,"
                    + "tittle TEXT,"
                    + "image TEXT,"
                    + "url TEXT )");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            db.execSQL("DROP TABLE IF EXISTS NewsFavorites");
        }
    }

    public void toFavorites(String tittle, String image, String url) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor cursor = db.query("NewsFavorites", new String[]{"tittle"},
                "tittle = ?", new String[]{tittle}, null, null, null);

        if (cursor.moveToFirst()) {
            db.close();
        } else {
            values.put("tittle", tittle);
            values.put("image", image);
            values.put("url", url);

            db.insert("NewsFavorites", null, values);
            db.close();
        }
    }

    public void deletinItemTorgPred(String tittle) {
        db = this.getWritableDatabase();
        db.delete("NewsFavorites", "tittle = ?", new String[]{tittle});
        db.close();
    }
}
