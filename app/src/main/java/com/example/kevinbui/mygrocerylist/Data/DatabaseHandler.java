package com.example.kevinbui.mygrocerylist.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.kevinbui.mygrocerylist.Model.Grocery;
import com.example.kevinbui.mygrocerylist.Util.Constants;

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
        return null;
    }

    // Get all groceries
    public List<Grocery> getAllGroceries() {
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
