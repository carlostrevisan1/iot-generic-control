package com.example.iot_generic_control.classes;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
                "    type VARCHAR(15),\n" +
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

    public ArrayList<IOTDevice> selectAllDevices(){
        SQLiteDatabase db;
        Cursor cursor;
        ArrayList<IOTDevice> devices = new ArrayList<IOTDevice>();
        String[] columns = {"id", "name", "desc", "ip_address", "port"};
        db = this.getReadableDatabase();
        cursor = db.query("device", columns, null, null, null, null, "id");
        while (cursor.moveToNext()){
            devices.add(new IOTDevice(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("desc")),
                                      cursor.getString(cursor.getColumnIndex("ip_address")), cursor.getString(cursor.getColumnIndex("port")),
                                      cursor.getInt(cursor.getColumnIndex("id"))));

        }
        cursor.close();
        return devices;
    }
    //TERMINAR:
    public ArrayList<BaseFeature> selectAllFeatures(){
        SQLiteDatabase db;
        Cursor cursor;
        ArrayList<BaseFeature> features = new ArrayList<BaseFeature>();
        String[] columns = {"id", "name", "topic", "type", "value", "device_id"};
        String type;
        db = this.getReadableDatabase();
        cursor = db.query("device", columns, null, null, null, null, "id");
        while (cursor.moveToNext()){
            type = cursor.getString(cursor.getColumnIndex("tyoe"));
            /*devices.add(new IOTDevice(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("desc")),
                    cursor.getString(cursor.getColumnIndex("ip_address")), cursor.getString(cursor.getColumnIndex("port")),
                    cursor.getInt(cursor.getColumnIndex("id"))));*/

        }
        cursor.close();
        return features;
    }
}
