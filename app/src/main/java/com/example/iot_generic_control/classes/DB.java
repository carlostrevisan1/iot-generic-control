package com.example.iot_generic_control.classes;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper{


    public DB(Context context){
        super(context, "iot_manager.db", null , 1);
    }
    //Classe de controle do banco de dados, possui funções de criação do banco assim como funçes de CRUD para as duas tabelas do banco
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Cria o BD e suas tabelas
        String pragma_sql = "PRAGMA foreign_keys = ON ";

        String device_sql = "CREATE TABLE device (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(40),\n" +
                "    desc TEXT,\n" +
                "    ip_address VARCHAR(25),\n" +
                "    port VARCHAR(5),\n" +
                "    colour VARCHAR(15)\n" +
                ") ";

        String feat_sql = "CREATE TABLE feature (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    name VARCHAR(50),\n" +
                "    topic VARCHAR(50),\n" +
                "    type VARCHAR(15),\n" +
                "    value VARCHAR(100),\n" +
                "    device_id INTEGER,\n" +
                "    FOREIGN KEY (device_id) REFERENCES device (id) ON DELETE CASCADE ON UPDATE CASCADE\n" +
                ");";

        db.execSQL(pragma_sql);
        db.execSQL(device_sql);
        db.execSQL(feat_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Recria o BD no caso de haver alguma mudança
        String sql = "DROP TABLE IF EXISTS device;";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS feature;";
        db.execSQL(sql);
        onCreate(db);
    }

    public ArrayList<IOTDevice> selectAllDevices(){
        //Lê todos as linhas da tabela "device" e as devolve em uma lista de IOTDevice
        SQLiteDatabase db;
        Cursor cursor;
        ArrayList<IOTDevice> devices = new ArrayList<IOTDevice>();
        String[] columns = {"id", "name", "desc", "ip_address", "port", "colour"};
        db = this.getReadableDatabase();
        cursor = db.query("device", columns, null, null, null, null, "id");
        while (cursor.moveToNext()){
            devices.add(new IOTDevice(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("desc")),
                                      cursor.getString(cursor.getColumnIndex("ip_address")), cursor.getString(cursor.getColumnIndex("port")),
                                      cursor.getString(cursor.getColumnIndex("colour")), cursor.getInt(cursor.getColumnIndex("id"))));

        }
        cursor.close();
        db.close();
        return devices;
    }

    public ArrayList<BaseFeature> selectAllFeatures(int device_id){
        //Lê todas as linhas da tabela "feature" que possuam device_id igual ao device_id passado á função
        SQLiteDatabase db;
        Cursor cursor;
        ArrayList<BaseFeature> features = new ArrayList<BaseFeature>();
        String type, query;
        db = this.getReadableDatabase();
        query = "SELECT * FROM feature WHERE device_id = ?";
        cursor = db.rawQuery(query, new String[] {Integer.toString(device_id)});
        while (cursor.moveToNext()){
            type = cursor.getString(cursor.getColumnIndex("type"));
            //Existem 4 tipos de features, por isso checamos o tipo antes para saber como continuar
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
                    String values, prefix, suffix;
                    String[] ranges;
                    values = cursor.getString(cursor.getColumnIndex("value"));
                    //A coluna "value" é um campo de texto que pode conter multiplas informações separadas por ';', no caso do slider podendo conter até 4 valores
                    ranges = values.split(";");
                    switch (ranges.length){
                        case 3:
                            startRange = Integer.parseInt(ranges[0]);
                            endRange = Integer.parseInt(ranges[1]);
                            prefix = ranges[2];
                            suffix = "";
                            break;
                        case 4:
                            startRange = Integer.parseInt(ranges[0]);
                            endRange = Integer.parseInt(ranges[1]);
                            prefix = ranges[2];
                            suffix = ranges[3];
                            break;
                        default:
                            startRange = Integer.parseInt(ranges[0]);
                            endRange = Integer.parseInt(ranges[1]);
                            prefix = "";
                            suffix = "";
                            break;
                    }
                    features.add(new SliderFeature(cursor.getString(cursor.getColumnIndex("name")),
                                                   cursor.getString(cursor.getColumnIndex("topic")),
                                                   cursor.getInt(cursor.getColumnIndex("id")),
                                                   cursor.getInt(cursor.getColumnIndex("device_id")),
                                                   startRange, endRange, type, prefix, suffix));
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
                case "colorPicker":
                    String value;
                    String[] parameters;
                    String color_system, sep, cp_prefix, cp_suffix;
                    value = cursor.getString(cursor.getColumnIndex("value"));
                    parameters = value.split(";");
                    switch (parameters.length){
                        case 2:
                            color_system = parameters[0];
                            sep = parameters[1];
                            cp_prefix = "";
                            cp_suffix = "";
                            break;
                        case 3:
                            color_system = parameters[0];
                            sep = parameters[1];
                            cp_prefix = parameters[2];
                            cp_suffix = "";
                            break;
                        case 4:
                            color_system = parameters[0];
                            sep = parameters[1];
                            cp_prefix = parameters[2];
                            cp_suffix = parameters[3];
                            break;
                        default:
                            color_system = parameters[0];
                            sep = "";
                            cp_prefix = "";
                            cp_suffix = "";
                            break;
                    }
                    features.add(new ColorPickerFeature(cursor.getString(cursor.getColumnIndex("name")),
                                                        cursor.getString(cursor.getColumnIndex("topic")),
                                                        cursor.getInt(cursor.getColumnIndex("id")),
                                                        cursor.getInt(cursor.getColumnIndex("device_id")), type, color_system, sep, cp_prefix,
                                                        cp_suffix));
                    break;
            }
        }
        cursor.close();
        db.close();
        return features;
    }

    public long insertDevice(String name, String desc, String ip_address, String port, String colour){
        //Insere uma nova linha na tabela "device"
        SQLiteDatabase db;
        ContentValues values;
        long res;
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put("name", name);
        values.put("desc", desc);
        values.put("ip_address", ip_address);
        values.put("port", port);
        values.put("colour", colour);
        res = db.insert("device", null, values);
        db.close();
        return res;
    }

    public long insertFeature(String name, String topic, String type, String value, int device_id){
        //Insere uma nova linha na tabela "feature"
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

    public void updateDevice(int id, String name, String desc, String ip_address, String port, String colour){
        //Faz update de uma linha da tabela "device" dado seu ID
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
        values.put("colour", colour);
        db.update("device", values, where, null);
        db.close();

    }

    public void updateFeature(int id, String name, String topic, String type, String value){
        //Faz update de uma linha da tabela "feature"
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
        db.update("feature", values, where, null);
        db.close();
    }

    public void deleteFrom(String table, int id){
        //Deleta uma linha com o id passado da tabela passada em table
        SQLiteDatabase db;
        String where;
        where = "id = " + id;
        db = this.getReadableDatabase();
        db.delete(table, where, null);
        db.close();
    }
}
