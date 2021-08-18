package com.udemydevandroid.listadecompras.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {


    public static int VERSION = 1;
    public static String DB_NAME = "app_compras";
    public static String TABLE_NAME = "compras";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "  (id INTEGER PRIMARY KEY AUTOINCREMENT, quantidade INTEGER DEFAULT 0, nome TEXT NOT NULL, valor DECIMAL DEFAULT 0);";
        try {
            db.execSQL(sql);
            Log.i("BANCO", "Banco criado");
        } catch (Exception e) {
            Log.i("BANCO", "Erro ao criar banco de dados. | " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
