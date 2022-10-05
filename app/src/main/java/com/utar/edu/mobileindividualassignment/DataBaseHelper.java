package com.utar.edu.mobileindividualassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SPENDING_DB";
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_TABLE = "SPENDING";
    public static final String COLUMN_SPEND_ID = "ID";
    public static final String COLUMN_SPEND_CATEGORY = "CATEGORY";
    public static final String COLUMN_SPEND_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_SPEND_AMOUNT = "SPEND_TOTAL";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DB_QUERY = "CREATE TABLE " + DATABASE_TABLE + " ( "
                + COLUMN_SPEND_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + COLUMN_SPEND_CATEGORY + " TEXT NOT NULL , "
                + COLUMN_SPEND_DESCRIPTION + " TEXT NOT NULL, "
                + COLUMN_SPEND_AMOUNT + " REAL NOT NULL);";

        db.execSQL(CREATE_DB_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    //addData
    public boolean addEntry(EntryObject entryObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SPEND_CATEGORY, entryObject.getSpendCategory());
        contentValues.put(COLUMN_SPEND_DESCRIPTION, entryObject.getSpendDescription());
        contentValues.put(COLUMN_SPEND_AMOUNT, entryObject.getSpendAmount());

        long insert = db.insert(DATABASE_TABLE, null, contentValues);
        if (insert == 1) {
            return false;
        } else {
            db.close();
            return true;
        }
    }

    //deleteData
    public boolean deleteEntry(EntryObject entryObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + DATABASE_TABLE + " WHERE " + COLUMN_SPEND_ID + " = " + entryObject.getSpendID();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        } else
            return false;
    }

    //update data
    public long updateEntry(int iD, String category, String description, double spendAmount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_SPEND_CATEGORY, category);
        contentValues.put(COLUMN_SPEND_DESCRIPTION, description);
        contentValues.put(COLUMN_SPEND_AMOUNT, spendAmount);

        return db.update(DATABASE_TABLE, contentValues, "ID=?", new String[]{String.valueOf(iD)});
    }

    //get all data
    public List<EntryObject> getAll() {
        List<EntryObject> returnList = new ArrayList<>();

        //get data from database
        String query = "SELECT * FROM " + DATABASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int spendID = cursor.getInt(0);
                String spendCategory = cursor.getString(1);
                String spendDescription = cursor.getString(2);
                double spendAmount = cursor.getDouble(3);
                EntryObject newEntryObject = new EntryObject(spendID, spendCategory, spendDescription, spendAmount);
                returnList.add(newEntryObject);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }
}//END CLASS
