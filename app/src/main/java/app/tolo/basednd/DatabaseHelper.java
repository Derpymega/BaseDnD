package app.tolo.basednd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RolPrices.db";
    public static final String TABLE_NAME = "prices_table";
    public static final String COL_ID = "ID";
    public static final String COL_1 = "NAME";
    public static final String COL_2 = "QUANTITY";
    public static final String COL_3 = "COST";

    private static DatabaseHelper sInstance;
    public static synchronized DatabaseHelper getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //SQLiteDatabase db = this.getReadableDatabase();
        sInstance = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table " + TABLE_NAME + " ( " +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_1 + " TEXT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean addData(String name, String quant, String cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_1, name);
        values.put(COL_2, quant);
        values.put(COL_3, cost);

        long newRowId = db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        if(newRowId == -1) {return false; }
        else { return true; }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ /*"'" +*/ TABLE_NAME /*+ "'"*/);
        db.execSQL("UPDATE SQLITE_SEQUENCE SET seq = 0 WHERE name = " + "'" + TABLE_NAME + "'");
        db.close();
    }
}
