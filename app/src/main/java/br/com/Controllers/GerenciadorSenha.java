package br.com.Controllers;

import android.content.Context;
import android.database.Cursor;

import br.com.Model.Horario;
import br.com.db.DBAdapter;

/**
 * Created by gabrielnovakovski on 19/09/2017.
 */

public class GerenciadorSenha {


    public static void SalvarSenha(Context ctx, int senha) {
        String sql = "";
        int retornaSenha = retornaSenha(ctx);
        if(retornaSenha > 0) {
            sql = "delete from Senha";
            DBAdapter.executarComandoSQL(ctx,sql);
        }

        sql = "INSERT INTO Senha(senha) " +
                " VALUES ('"+senha+"')";
        DBAdapter.executarComandoSQL(ctx,sql);
    }

    public static int retornaSenha(Context ctx){
        String sql = "SELECT senha FROM Senha";
        Cursor cursor = DBAdapter.executaConsultaSQL(ctx,sql);
        int senha = 0;
        if(cursor.moveToFirst()){

            senha = cursor.getInt(cursor.getColumnIndex("senha"));
            cursor.close();
        }
        return senha;

    }

}
