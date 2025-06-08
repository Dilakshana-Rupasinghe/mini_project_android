package com.example.googlemapdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SessionDB {
    private static final String DATABASE_NAME = "field_session_record_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FIELD_CANVASING_RECORD = "field_record_tbl";
    private static final String TABLE_FIELD_CANVASING_COL_REC_ID = "session_id";
    private static final String TABLE_FIELD_CANVASING_COL_LATITUDE = "latitude";
    private static final String TABLE_FIELD_CANVASING_COL_LONGITUDE = "longitude";
    private static final String TABLE_FIELD_CANVASING_COL_DATE = "date";
    private static final String TABLE_FIELD_CANVASING_COL_TIME = "time";
    private static final String TABLE_FIELD_CANVASING_COL_VOTE_DESIRED_PARTY = "vote_desired_party";
    private static final String TABLE_FIELD_SURVEY_COL_VOTE_TYPE = "Vote_type";
    private static final String TABLE_FIELD_SURVEY_COL_COMMENT = "Comment";
    private DBHelper ourHelper;
    private final Context outContext;
    private SQLiteDatabase ourDatabase;

    public SessionDB(Context context) {
        this.outContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode = "CREATE TABLE " + TABLE_FIELD_CANVASING_RECORD + "(" +
                    TABLE_FIELD_CANVASING_COL_REC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TABLE_FIELD_CANVASING_COL_LATITUDE + " REAL NOT NULL, " +
                    TABLE_FIELD_CANVASING_COL_LONGITUDE + " REAL NOT NULL, " +
                    TABLE_FIELD_CANVASING_COL_DATE + " TEXT NOT NULL, " +
                    TABLE_FIELD_CANVASING_COL_TIME + " TEXT NOT NULL, " +
                    TABLE_FIELD_CANVASING_COL_VOTE_DESIRED_PARTY + " INTEGER NOT NULL, " +
                    TABLE_FIELD_SURVEY_COL_VOTE_TYPE + " INTEGER NOT NULL, " +
                    TABLE_FIELD_SURVEY_COL_COMMENT + " TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FIELD_CANVASING_RECORD);
            onCreate(db);
        }

    }

    public SessionDB open() throws SQLException {
        ourHelper = new DBHelper(outContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
    }

    public long createEntry(int recID, double latitude, double longitude, String Date,
                            String Time, String expectedVotes, String votetype, String comment) {
        ContentValues cv = new ContentValues();
        cv.put(TABLE_FIELD_CANVASING_COL_REC_ID, recID);
        cv.put(TABLE_FIELD_CANVASING_COL_LATITUDE, latitude);
        cv.put(TABLE_FIELD_CANVASING_COL_LONGITUDE, longitude);
        cv.put(TABLE_FIELD_CANVASING_COL_DATE, Date);
        cv.put(TABLE_FIELD_CANVASING_COL_TIME, Time);
        cv.put(TABLE_FIELD_CANVASING_COL_VOTE_DESIRED_PARTY, expectedVotes);
        cv.put(TABLE_FIELD_SURVEY_COL_VOTE_TYPE, votetype);

        cv.put(TABLE_FIELD_SURVEY_COL_COMMENT, comment);
        return ourDatabase.insert(TABLE_FIELD_CANVASING_RECORD, null, cv);
    }

    public String getData() {
        String[] columns = new String[]{TABLE_FIELD_CANVASING_COL_REC_ID, TABLE_FIELD_CANVASING_COL_LATITUDE,
                TABLE_FIELD_CANVASING_COL_LONGITUDE, TABLE_FIELD_CANVASING_COL_DATE,
                TABLE_FIELD_CANVASING_COL_TIME, TABLE_FIELD_CANVASING_COL_VOTE_DESIRED_PARTY,
                TABLE_FIELD_SURVEY_COL_VOTE_TYPE, TABLE_FIELD_SURVEY_COL_COMMENT};
        Cursor c = ourDatabase.query(TABLE_FIELD_CANVASING_RECORD, columns,
                null,
                null,
                null,
                null,
                null);
        String result = "";

        int iRecID = c.getColumnIndex(TABLE_FIELD_CANVASING_COL_REC_ID);
        int iLatitude = c.getColumnIndex(TABLE_FIELD_CANVASING_COL_LATITUDE);
        int iLongitude = c.getColumnIndex(TABLE_FIELD_CANVASING_COL_LONGITUDE);
        int iDate = c.getColumnIndex(TABLE_FIELD_CANVASING_COL_DATE);
        int iTime = c.getColumnIndex(TABLE_FIELD_CANVASING_COL_TIME);
        int ivotedesiredparty = c.getColumnIndex(TABLE_FIELD_CANVASING_COL_VOTE_DESIRED_PARTY);
        int iVotetype = c.getColumnIndex(TABLE_FIELD_SURVEY_COL_VOTE_TYPE);
        int icomment = c.getColumnIndex(TABLE_FIELD_SURVEY_COL_COMMENT);


        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(iRecID) + " | " + c.getString(iLatitude)
                    + " | " + c.getString(iLongitude) + " | " + c.getString(iDate) +
                    " | " + c.getString(iTime) + " | " + c.getString(ivotedesiredparty) +
                    " | " + c.getString(iVotetype) +  " | " + c.getString(icomment) + " \n ";
        }
        c.close();
        return result;
    }
}
