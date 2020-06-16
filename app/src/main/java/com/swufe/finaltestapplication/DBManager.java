package com.swufe.finaltestapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    public void add(WordItem item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", item.getWord());
        values.put("trans", item.getTrans());
        db.insert(TBNAME, null, values);
        db.close();
    }

//    public void addAll(List<RateItem> list){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        for (RateItem item : list) {
//            ContentValues values = new ContentValues();
//            values.put("curname", item.getCurName());
//            values.put("currate", item.getCurRate());
//            db.insert(TBNAME, null, values);
//        }
//        db.close();
//    }

    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

//    public void update(WordItem item){
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("word", item.getWord());
//        values.put("trans", item.getTrans());
//        db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
//        db.close();
//    }

    public List<WordItem> listAll(){
        List<WordItem> rateList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

//        select null1 from tableName where null2=null3 group by null4 having null5 order by null6
//        对于上面的null1。。。null6,一定注意对应的是String还是String[]。

        Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
        if(cursor!=null){
            rateList = new ArrayList<WordItem>();
            while(cursor.moveToNext()){
                WordItem item = new WordItem();
                item.setWord(cursor.getString(cursor.getColumnIndex("WORD")));
                item.setTrans(cursor.getString(cursor.getColumnIndex("TRANS")));

                rateList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateList;

    }

//    public RateItem findById(int id){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
//        RateItem rateItem = null;
//        if(cursor!=null && cursor.moveToFirst()){
//            rateItem = new RateItem();
//            rateItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
//            rateItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
//            rateItem.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
//            cursor.close();
//        }
//        db.close();
//        return rateItem;
//    }
}
