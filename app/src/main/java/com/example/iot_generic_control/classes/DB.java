package com.example.iot_generic_control.classes;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper{

    public DB(Context context){
        super(context, "iot_manager.db", null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE device (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(40),\n" +
                "    desc TEXT,\n" +
                "    ip_address VARCHAR(25),\n" +
                "    port VARCHAR(5)\n" +
                ");\n" +
                "\n" +
                "CREATE TABLE feature (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(50),\n" +
                "    topic VARCHAR(50),\n" +
                "    type INTEGER,\n" +
                "    value VARCHAR(100),\n" +
                "    device_id INTEGER,\n" +
                "    FOREIGN KEY (device_id) REFERENCES device (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS device; DROP TABLE IF EXISTS features;";
        db.execSQL(sql);
        onCreate(db);
    }
}
