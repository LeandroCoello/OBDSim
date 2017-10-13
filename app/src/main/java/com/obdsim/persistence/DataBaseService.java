package com.obdsim.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.obdsim.persistence.entities.RPMCommand;
import com.obdsim.utils.CommandsContainer;
import com.obdsim.persistence.entities.MockObdCommand;

import java.util.ArrayList;

/**
 * Created by lrocca on 26/07/2017.
 */
public class DataBaseService extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CarDiag.db";
//    public static final String DATABASE_NAME = "CarDiag";
    private SQLiteDatabase db;

    public DataBaseService(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(ObdCommandContract.SQL_CREATE_COMMAND_ENTRIES);
        this.db = db;

        if (getCommands(null,null).size() == 0) {
            for (MockObdCommand mCmd : CommandsContainer.getInstance().getCommandList()) {
                insertCommand(mCmd);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ObdCommandContract.SQL_DELETE_COMMAND_ENTRIES);
        onCreate(db);
    }


    //////////////////////Commands///////////////////////////

    public ArrayList<MockObdCommand> getCommands(String whereColumns, String[] whereColumnsValues) {

        ArrayList<MockObdCommand> cmds = new ArrayList<MockObdCommand>();

        String[] projection = {
                ObdCommandContract.CommandEntry._ID,
                ObdCommandContract.CommandEntry.CODE,
                ObdCommandContract.CommandEntry.RESPONSE,
                ObdCommandContract.CommandEntry.STATE_FLAG,
                ObdCommandContract.CommandEntry.DESCRIPTION
        };

        Cursor c = db.query(
                ObdCommandContract.CommandEntry.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                whereColumns,                                // The columns for the WHERE clause
                whereColumnsValues,//   selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null//     sortOrder                                 // The sort order
        );

        if (c.moveToFirst()) {
            do {
                MockObdCommand cmd = ObdCommandRowMapper.getCommand(c);
                cmds.add(cmd);
            } while(c.moveToNext());
        }
        c.close();
        return cmds;
    }

    public void insertCommand(MockObdCommand cmd) {
        ContentValues values = new ContentValues();

        values.put(ObdCommandContract.CommandEntry.CODE, cmd.getCmd());
        values.put(ObdCommandContract.CommandEntry.RESPONSE, cmd.getResponse());
        values.put(ObdCommandContract.CommandEntry.STATE_FLAG, cmd.getStateFlag());
        values.put(ObdCommandContract.CommandEntry.DESCRIPTION, cmd.getDescription());

        Long id = db.insert(ObdCommandContract.CommandEntry.TABLE_NAME, null, values);
        cmd.set_id(id.intValue());
//        db.delete();
    }

    public void deleteCommand(MockObdCommand cmd) {

        String[] whereValues = new String[] {cmd.get_id().toString()};
        db.delete(ObdCommandContract.CommandEntry.TABLE_NAME, ObdCommandContract.CommandEntry._ID+"=?", whereValues);

    }


    public void updateCommand(MockObdCommand cmd) {
        ContentValues cv = new ContentValues();

        cv.put(ObdCommandContract.CommandEntry.CODE, cmd.getCmd());
        cv.put(ObdCommandContract.CommandEntry.RESPONSE, cmd.getResponse());

        String[] whereValues = new String[] {cmd.get_id().toString()};

        db.update(ObdCommandContract.CommandEntry.TABLE_NAME, cv, ObdCommandContract.CommandEntry._ID+"=?",whereValues);
    }

}