package br.com.Controllers;

import android.content.Context;
import android.database.Cursor;

import br.com.Model.Horario;
import br.com.db.DBAdapter;

/**
 * Created by gabrielnovakovski on 19/09/2017.
 */

public class GerenciadorDias {

    public static void SalvarDias(Context ctx, String[] dias) {
        String[] checa = retornaDias(ctx);
        String sql = "";

        if (checa.length > 0) {
           sql = "delete from Dias";
        }
        DBAdapter.executarComandoSQL(ctx, sql);

        for (int i = 0; i < dias.length; i++) {
            if (dias[i] != null) {
                sql = "INSERT INTO Dias(data) " +
                        " VALUES ('" + dias[i] + "');";

                DBAdapter.executarComandoSQL(ctx, sql);
            }

        }

        }


    public static String[] retornaDias(Context ctx){
        String sql = "SELECT data FROM Dias";
        Cursor cursor = DBAdapter.executaConsultaSQL(ctx,sql);
        String[] dias = new String[7];
        int i = 0;
        if(cursor.moveToFirst()){
            do{
                dias[i] = cursor.getString(cursor.getColumnIndex("data"));
                i ++;
            }
            while (cursor.moveToNext());
            cursor.close();
        }

        return dias;


    }
}
