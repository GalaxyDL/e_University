package com.wangh.e_university;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by wangh on 2016/8/5.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "DATA.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE classTable" +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " classTitle TEXT," +
                " classLocation TEXT," +
                " classTime TEXT," +
                " teacher TEXT," +
                " weekStart INTEGER," +
                " weekEnd INTEGER," +
                " singleWeek INTEGER," +
                " doubleWeek INTEGER," +
                " timeStart INTEGER," +
                " timeEnd INTEGER," +
                " classNumber INTEGER, " +
                " date INTEGER," +
                " colorId INTEGER);"
        );
        sqLiteDatabase.execSQL("CREATE TABLE scores" +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " title TEXT," +
                " score DOUBLE," +
                " credit DOUBLE);"
        );
        sqLiteDatabase.execSQL("CREATE TABLE averageCredit" +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " value TEXT);"
        );
        sqLiteDatabase.execSQL("CREATE TABLE exams" +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " title TEXT," +
                " teacher TEXT," +
                " time TEXT," +
                " location TEXT," +
                " credit INTEGER," +
                " modus TEXT);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if(i==1&&i1==2){
            sqLiteDatabase.execSQL("ALTER TABLE scores RENAME TO temp_scores ;");
            sqLiteDatabase.execSQL("CREATE TABLE scores" +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " title TEXT," +
                    " score DOUBLE," +
                    " credit DOUBLE);"
            );
            sqLiteDatabase.execSQL("INSERT INTO scores SELECT * FROM temp_scores ;");
            sqLiteDatabase.execSQL("DROP TABLE temp_scores ;");
        }
    }

}
