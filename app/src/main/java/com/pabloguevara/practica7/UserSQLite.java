package com.pabloguevara.practica7;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class UserSQLite extends SQLiteOpenHelper{

    String sql_tabla = "CREATE TABLE Peluchitos(Id integer primary key, Nombre text, Cantidad integer, Precio integer)";

    public UserSQLite (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sql_tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Peluchitos");
        db.execSQL(sql_tabla);
    }
}
