package com.obdsim.persistence;

import android.provider.BaseColumns;

/**
 * Created by leo on 04/08/2017.
 */

public final class ObdCommandContract {
    private ObdCommandContract() {}

    public static class CommandEntry implements BaseColumns {
        public static final String TABLE_NAME ="obd_command";

        public static final String CODE = "name";
        public static final String RESPONSE = "response";
    }

    public static final String SQL_CREATE_COMMAND_ENTRIES =
            "CREATE TABLE " + CommandEntry.TABLE_NAME + " (" +
                    CommandEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    CommandEntry.CODE + " TEXT," +
                    CommandEntry.RESPONSE + " TEXT" +
                    " )";

    public static final String SQL_DELETE_COMMAND_ENTRIES = "DROP TABLE IF EXISTS " + CommandEntry.TABLE_NAME;

}
