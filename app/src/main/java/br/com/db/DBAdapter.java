package br.com.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nunes on 27/10/2016.
 */

public class DBAdapter {

    static SQLiteDatabase database;
    static DBHelper dbHelper;

    public static void abrirConexao(Context ctx){
        if(database == null || !database.isOpen()){
            dbHelper = new DBHelper(ctx,null);
            database = dbHelper.getWritableDatabase();
        }
    }

    public static void fecharConexao(){
        if(database != null && database.isOpen()){
            database.close();
        }
    }

    public static void executarComandoSQL(Context ctx, String sql){
        abrirConexao(ctx);
        database.execSQL(sql);
        fecharConexao();
    }

    public static Cursor executaConsultaSQL(Context ctx, String sql){
        abrirConexao(ctx);
        Cursor c = database.rawQuery(sql, null);
        return c;
    }

}
