package com.wangh.e_university;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.AEADBadTagException;

/**
 * Created by wangh on 2016/8/5.
 */
public class DatabaseManager {
    private final String CLASS_TABLE = "classTable";
    private final String EXAM_TABLE = "exams";
    private final String SCORE_TABLE = "scores";
    private final String AVERAGE_CREDIT_TABLE ="averageCredit";
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase db;

    public DatabaseManager(Context context) {
        databaseHelper = new DatabaseHelper(context);
        db = databaseHelper.getWritableDatabase();
    }

    public void addClass(ClassItem classItem) {
        db.beginTransaction();
        try {
            ContentValues values =new ContentValues();
            values.put("classTitle",classItem.getClassTitle());
            values.put("classLocation",classItem.getClassLocation());
            values.put("classTime",classItem.getClassTime());
            values.put("teacher",classItem.getTeacher());
            values.put("weekStart",classItem.getWeekStart());
            values.put("weekEnd",classItem.getWeekEnd());
            values.put("singleWeek",classItem.getSingleWeek());
            values.put("doubleWeek",classItem.getDoubleWeek());
            values.put("timeStart",classItem.getTimeStart());
            values.put("timeEnd",classItem.getTimeEnd());
            values.put("classNumber",classItem.getClassNumber());
            values.put("date",classItem.getDate());
            values.put("colorId",classItem.getColorID());
            db.insert(CLASS_TABLE,null,values);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void addExam(ExamItem examItem){
        db.beginTransaction();
        try {
            ContentValues values=new ContentValues();
            values.put("title",examItem.getTitle());
            values.put("teacher",examItem.getTeacher());
            values.put("time",examItem.getTime());
            values.put("location",examItem.getLocation());
            values.put("credit",examItem.getCredit());
            values.put("modus",examItem.getModus());
            db.insert(EXAM_TABLE,null,values);

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void addScore(ScoreItem scoreItem){
        db.beginTransaction();
        try{
            ContentValues values=new ContentValues();
            values.put("title",scoreItem.getTitle());
            values.put("score",scoreItem.getScore());
            values.put("credit",scoreItem.getCredit());
            db.insert(SCORE_TABLE,null,values);

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void updateAverageCredit(String averageCredit){
        deleteAverageCredit();
        db.beginTransaction();
        try {
            ContentValues values=new ContentValues();
            values.put("value",averageCredit);
            db.insert(AVERAGE_CREDIT_TABLE,null,values);

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
    }

    public void deleteAllClass() {
        db.execSQL("DELETE FROM "+CLASS_TABLE);
    }

    public void deleteAllExam()  {
        db.execSQL("DELETE FROM "+EXAM_TABLE);
    }

    public void deleteAllScore()  {
        db.execSQL("DELETE FROM "+SCORE_TABLE);
    }

    public void deleteAverageCredit(){
        db.execSQL("DELETE FROM "+AVERAGE_CREDIT_TABLE);
    }

    public List<ClassItem> queryClass() {
        ArrayList<ClassItem> classItems = new ArrayList<ClassItem>();
        Cursor c = db.rawQuery("SELECT * FROM "+CLASS_TABLE, null);
        while (c.moveToNext()) {
            ClassItem classItem = new ClassItem(
                    c.getString(c.getColumnIndex("classTitle"))
                    , c.getString(c.getColumnIndex("classLocation"))
                    , c.getString(c.getColumnIndex("classTime"))
                    , c.getString(c.getColumnIndex("teacher"))
                    , c.getInt(c.getColumnIndex("weekStart"))
                    , c.getInt(c.getColumnIndex("weekEnd"))
                    , c.getInt(c.getColumnIndex("singleWeek"))
                    , c.getInt(c.getColumnIndex("doubleWeek"))
                    , c.getInt(c.getColumnIndex("timeStart"))
                    , c.getInt(c.getColumnIndex("timeEnd"))
                    , c.getInt(c.getColumnIndex("classNumber"))
                    , c.getInt(c.getColumnIndex("date"))
                    , c.getInt(c.getColumnIndex("colorId"))
            );
            classItems.add(classItem);
        }
        c.close();
        return classItems;
    }

    public List<ExamItem> queryExam(){
        ArrayList<ExamItem> examItems= new ArrayList<ExamItem>();
        Cursor c=db.rawQuery("SELECT * FROM "+EXAM_TABLE,null);
        while(c.moveToNext()){
            ExamItem examItem= new ExamItem(
                    c.getString(c.getColumnIndex("title"))
                    ,c.getString(c.getColumnIndex("teacher"))
                    ,c.getString(c.getColumnIndex("time"))
                    ,c.getString(c.getColumnIndex("location"))
                    ,c.getString(c.getColumnIndex("modus"))
                    ,c.getInt(c.getColumnIndex("credit"))
            );
            examItems.add(examItem);
        }
        c.close();
        return examItems;
    }

    public List<ScoreItem> queryScore(){
        ArrayList<ScoreItem> scoreItems = new ArrayList<ScoreItem>();
        Cursor c=db.rawQuery("SELECT * FROM "+SCORE_TABLE,null);
        while(c.moveToNext()){
            ScoreItem scoreItem = new ScoreItem(
                    c.getString(c.getColumnIndex("title"))
                    ,c.getDouble(c.getColumnIndex("score"))
                    ,c.getDouble(c.getColumnIndex("credit"))
            );
            scoreItems.add(scoreItem);
        }
        c.close();
        return scoreItems;
    }

    public String queryAverageCredit(){
        String averageCredit="";
        Cursor c=db.rawQuery("SELECT * FROM "+AVERAGE_CREDIT_TABLE,null);
        if(c.moveToNext()){
            averageCredit=c.getString(c.getColumnIndex("value"));
        }
        c.close();
        return averageCredit;
    }

    public void closeDB() {
        db.close();
    }

}
