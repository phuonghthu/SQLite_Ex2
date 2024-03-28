package com.K214111950.sqlite_ex2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    // Khai báo
    public static final String DB_NAME = "products.sqlite";
    public static final int DB_VERSION = 1;
    public static final String TBL_NAME = "product";
    public static final String COL_CODE = "ProductCode";
    public static final String COL_NAME = "ProductName";
    public static final String COL_PRICE = "ProductPrice";


    // Constructor
    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TBL_NAME + " " +
                "(" + COL_CODE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " VARCHAR(50), "
                + COL_PRICE + " REAL)";
        sqLiteDatabase.execSQL(sql);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(sqLiteDatabase);

    }
    // SELECT ...
    public Cursor queryData( String sql){
        // Đọc dữ liệu
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    // INSERT, UPDATE, DELETE
    public boolean execSql(String sql){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.execSQL(sql);
            return  true;
        }catch (Exception e) {
            Log.e("Error: ", e.toString());
            return false;
        }

    }

    public int numbOfRows(){
        Cursor c = queryData("SELECT * FROM " + TBL_NAME);
        int numOfRows = c.getCount();
        c.close();
        return numOfRows;
    }

    // Tạo dữ liệu mẫu
    public void createSampleData(){
        if (numbOfRows() == 0){
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Heneiken', 19000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Tiger', 18000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Sapporo', 22000)");
            execSql("INSERT INTO " + TBL_NAME + " VALUES(null, 'Blanc1664', 21000)");
        }


    }
}
