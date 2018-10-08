package br.com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nunes on 27/10/2016.
 */

public class DBHelper extends SQLiteOpenHelper {
    static final int VERSAO = 1;
    static final String DATABASE = "Produtividade.db";

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE, factory, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Horario(id integer primary key autoincrement, " +
                "horaInicioManha text," +
                "horaFimManha text," +
                "minutoInicioManha text," +
                "minutoFimManha text," +
                "horaInicioTarde text," +
                "horaFimTarde text," +
                "minutoInicioTarde text," +
                "minutoFimTarde text)");

        db.execSQL("CREATE TABLE Senha(senha int)");

        db.execSQL("CREATE TABLE Dias(data text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
