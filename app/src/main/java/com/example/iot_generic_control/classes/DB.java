package com.example.iot_generic_control.classes;
import android.content.ContentValues;
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
        String sql = "PRAGMA foreign_keys = ON ";
        db.execSQL(sql);

        sql = "CREATE TABLE device (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(40),\n" +
                "    desc TEXT,\n" +
                "    ip_address VARCHAR(25),\n" +
                "    port VARCHAR(5)\n" +
                ") ";
        db.execSQL(sql);

        sql=    "CREATE TABLE feature (\n" +
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
        String sql = "DROP TABLE IF EXISTS device; DROP TABLE IF EXISTS feature;";
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
        db.close();
        return devices;
    }

    public ArrayList<BaseFeature> selectAllFeatures(){
        SQLiteDatabase db;
        Cursor cursor;
        ArrayList<BaseFeature> features = new ArrayList<BaseFeature>();
        String[] columns = {"id", "name", "topic", "type", "value", "device_id"};
        String type;
        db = this.getReadableDatabase();
        cursor = db.query("feature", columns, null, null, null, null, "id");
        while (cursor.moveToNext()){
            type = cursor.getString(cursor.getColumnIndex("tyoe"));
            switch (type){
                case "button":
                    features.add(new ButtonFeature(cursor.getString(cursor.getColumnIndex("name")),
                                                   cursor.getString(cursor.getColumnIndex("topic")),
                                                   cursor.getInt(cursor.getColumnIndex("id")),
                                                   cursor.getInt(cursor.getColumnIndex("device_id")),
                                                   cursor.getString(cursor.getColumnIndex("value")), type));
                    break;
                case "sendText":
                    features.add(new SendTextFeature(cursor.getString(cursor.getColumnIndex("name")),
                                                     cursor.getString(cursor.getColumnIndex("topic")),
                                                     cursor.getInt(cursor.getColumnIndex("id")),
                                                     cursor.getInt(cursor.getColumnIndex("device_id")),
                                                     cursor.getString(cursor.getColumnIndex("value")), type));
                    break;
                case "slider":
                    int startRange, endRange;
                    String values;
                    String[] ranges;
                    values = cursor.getString(cursor.getColumnIndex("value"));
                    ranges = values.split(";");
                    startRange = Integer.parseInt(ranges[0]);
                    endRange = Integer.parseInt(ranges[1]);
                    features.add(new SliderFeature(cursor.getString(cursor.getColumnIndex("name")),
                                                   cursor.getString(cursor.getColumnIndex("topic")),
                                                   cursor.getInt(cursor.getColumnIndex("id")),
                                                   cursor.getInt(cursor.getColumnIndex("device_id")),
                                                   startRange, endRange, type));
                    break;
                case "toggleButton":
                    String toggleValues;
                    String[] onoff;
                    toggleValues = cursor.getString(cursor.getColumnIndex("value"));
                    onoff = toggleValues.split(";");
                    features.add(new ToggleButtonFeature(cursor.getString(cursor.getColumnIndex("name")),
                                                         cursor.getString(cursor.getColumnIndex("topic")),
                                                         cursor.getInt(cursor.getColumnIndex("id")),
                                                         cursor.getInt(cursor.getColumnIndex("device_id")),
                                                         onoff[0], onoff[1], type));
                    break;
            }
        }
        cursor.close();
        db.close();
        return features;
    }

    public long insertDevice(String name, String desc, String ip_address, String port){
        SQLiteDatabase db;
        ContentValues values;
        long res;
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("name", name);
        values.put("desc", desc);
        values.put("ip_address", ip_address);
        values.put("port", port);
        res = db.insert("device", null, values);
        db.close();
        return res;
    }

    public long insertFeature(String name, String topic, String type, String value, int device_id){
        SQLiteDatabase db;
        ContentValues values;
        long res;
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("name", name);
        values.put("topic", topic);
        values.put("type", type);
        values.put("value", value);
        values.put("device_id", device_id);
        res = db.insert("feature", null, values);
        db.close();
        return res;
    }

    public void updateDevice(int id, String name, String desc, String ip_address, String port){
        SQLiteDatabase db;
        ContentValues values;
        String where;
        db = this.getWritableDatabase();
        values = new ContentValues();
        where = "id = " + id;
        values.put("name", name);
        values.put("desc", desc);
        values.put("ip_address", ip_address);
        values.put("port", port);
        db.update("device", values, where, null);
        db.close();
    }

    public void updateFeature(int id, String name, String topic, String type, String value, int device_id){
        SQLiteDatabase db;
        ContentValues values;
        String where;
        db = this.getWritableDatabase();
        values = new ContentValues();
        where = "id = " + id;
        values.put("name", name);
        values.put("topic", topic);
        values.put("type", type);
        values.put("value", value);
        values.put("device_id", device_id);
        db.update("feature", values, where, null);
        db.close();
    }

    public void deleteFrom(String table, int id){
        SQLiteDatabase db;
        String where;
        where = "id = " + id;
        db = this.getReadableDatabase();
        db.delete(table, where, null);
        db.close();
    }
}
