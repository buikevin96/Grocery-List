package com.example.kevinbui.mygrocerylist.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.kevinbui.mygrocerylist.Model.Grocery;
import com.example.kevinbui.mygrocerylist.Util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context ctx;

    public DatabaseHandler(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.ctx = context;
    }

    // Create table with fields and columns we need
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_GROCERY_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY," + Constants.KEY_GROCERY_ITEM +
                " TEXT," + Constants.KEY_QTY_NUMBER + " TEXT," + Constants.KEY_DATE_NAME +
                " LONG);";

        db.execSQL(CREATE_GROCERY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Constants.TABLE_NAME);

        onCreate(db);
    }

    /*
     *  CRUD OPERATIONS: Create, Read, Update, Delete Methods
     */

    // Add Grocery
    public void AddGrocery(Grocery grocery){

    }

    // Get grocery Item
    private Grocery getGrocery(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {Constants.KEY_ID,
                        Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER, Constants.KEY_DATE_NAME},
                Constants.KEY_ID + "=?",
                new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();


        Grocery grocery = new Grocery();
        grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
        grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

        //convert timestamp to something readable
        java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                .getTime());

        grocery.setDateItemAdded(formatedDate);



        return grocery;
    }

    // Get all groceries
    public List<Grocery> getAllGroceries() {
        SQLiteDatabase db = this.getReadableDatabase();//Database instance

        List<Grocery> groceryList = new ArrayList<>();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[] {
                Constants.KEY_ID, Constants.KEY_GROCERY_ITEM, Constants.KEY_QTY_NUMBER,
                Constants.KEY_DATE_NAME}, null, null, null, null Constants.KEY_DATE_NAME + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Grocery grocery = new Grocery();
                grocery.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                grocery.setName(cursor.getString(cursor.getColumnIndex(Constants.KEY_GROCERY_ITEM)));
                grocery.setQuantity(cursor.getString(cursor.getColumnIndex(Constants.KEY_QTY_NUMBER)));

                //convert timestamp to something readable
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String formatedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.KEY_DATE_NAME)))
                        .getTime());

                grocery.setDateItemAdded(formatedDate);

                // Add to the groceryList
                groceryList.add(grocery);

            }while (cursor.moveToNext());
        }

        return null;
    }

    // Update grocery
    public int updateGrocery(Grocery grocery){
        return 0;
    }

    // Delete grocery
    public void deleteGrocery(int id){

    }

    // Get count
    public int getGroceriesCount(){

        return 0;
    }
}
