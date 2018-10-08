package br.com.Controllers;

/**
 * Created by gabrielnovakovski on 15/07/2017.
 */
import android.content.Context;
import android.database.Cursor;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.Model.Horario;
import br.com.db.DBAdapter;

public class GerenciadorDespertador {

    public void SalvarHorario(Context ctx, Horario horario) {
            String sql = "INSERT INTO Horario " +
                    " VALUES ('1','@horaInicioManha','@horaFimManha','@minutoInicioManha','@minutoFimManha','@horaInicioTarde','@horaFimTarde', " +
                    " '@minutoInicioTarde', '@minutoFimTarde')";
        if(null != horario.getHoraInicioManha()) {
            sql = sql.replace("@horaInicioManha", horario.getHoraInicioManha());
        }
        else if(null == horario.getHoraInicioManha()){
            sql = sql.replace("@horaInicioManha", null);
        }
            sql = sql.replace("@horaFimManha",horario.getHoraFimManha());
            sql = sql.replace("@minutoInicioManha",horario.getMinutoInicioManha());
            sql = sql.replace("@minutoFimManha",horario.getMinutoFimManha());
            sql = sql.replace("@horaInicioTarde",horario.getHoraInicioTarde());
            sql = sql.replace("@horaFimTarde",horario.getHoraFimTarde());
            sql = sql.replace("@minutoInicioTarde",horario.getMinutoInicioTarde());
            sql = sql.replace("@minutoFimTarde",horario.getMinutoFimTarde());
            DBAdapter.executarComandoSQL(ctx,sql);
        }

        public void excluirHorario(Context ctx){
          Horario horario = retornaHorario(ctx);
            if(horario != null) {
                String sql = "DELETE FROM Horario";
                DBAdapter.executarComandoSQL(ctx, sql);
            }
    }
    public void AtualizarHorario(Context ctx,Horario horario) {
            String sql =  "UPDATE Horario"+
                    " SET  id = '1', horaInicioManha = '@horaInicioManha', horaInicioTarde = '@horaInicioTarde', " +
                    " minutoInicioManha = '@minutoInicioManha',minutoInicioTarde = '@minutoInicioTarde'," +
                    " horaFimManha = '@horaFimManha',horaFimTarde = '@horaFimTarde', " +
                    " minutoFimManha = '@minutoFimManha', minutoFimTarde = '@minutoFimTarde'";
        sql = sql.replace("@horaInicioManha", horario.getHoraInicioManha());
        sql = sql.replace("@horaInicioTarde",horario.getHoraInicioTarde());
        sql = sql.replace("@minutoInicioManha",horario.getMinutoInicioManha());
        sql = sql.replace("@minutoInicioTarde",horario.getMinutoInicioTarde());
        sql = sql.replace("@horaFimManha",horario.getHoraFimManha());
        sql = sql.replace("@horaFimTarde",horario.getHoraFimTarde());
        sql = sql.replace("@minutoFimManha",horario.getMinutoFimManha());
        sql = sql.replace("@minutoFimTarde",horario.getHoraFimTarde());
            DBAdapter.executarComandoSQL(ctx,sql);
        }

    public Horario retornaHorario(Context ctx){
            String sql = "SELECT * FROM Horario ";
            Cursor cursor = DBAdapter.executaConsultaSQL(ctx,sql);
            Horario horario = new Horario();

            if(cursor.moveToFirst()){
                int colunaHoraInicioManha = cursor.getColumnIndex("horaInicioManha");
                int colunaHoraInicioTarde = cursor.getColumnIndex("horaInicioTarde");
                int colunaMinutoInicioManha = cursor.getColumnIndex("minutoInicioManha");
                int colunaMinutoInicioTarde = cursor.getColumnIndex("minutoInicioTarde");
                int colunaHoraFimManha = cursor.getColumnIndex("horaFimManha");
                int colunaHoraFimTarde = cursor.getColumnIndex("horaFimTarde");
                int colunaMinutoFimManha = cursor.getColumnIndex("minutoFimManha");
                int colunaMinutoFimTarde = cursor.getColumnIndex("minutoFimTarde");
                do{
                    String horaInicioManha = cursor.getString(colunaHoraInicioManha);
                    String horaInicioTarde = cursor.getString(colunaHoraInicioTarde);
                    String minutoInicioManha = cursor.getString(colunaMinutoInicioManha);
                    String minutoInicioTarde = cursor.getString(colunaMinutoInicioTarde);
                    String horaFimManha = cursor.getString(colunaHoraFimManha);
                    String horaFimTarde = cursor.getString(colunaHoraFimTarde);
                    String minutoFimManha = cursor.getString(colunaMinutoFimManha);
                    String minutoFimTarde = cursor.getString(colunaMinutoFimTarde);
                    horario.setHoraInicioManha(horaInicioManha);
                    horario.setHoraInicioTarde(horaInicioTarde);
                    horario.setMinutoInicioManha(minutoInicioManha);
                    horario.setMinutoInicioTarde(minutoInicioTarde);
                    horario.setHoraFimManha(horaFimManha);
                    horario.setHoraFimTarde(horaFimTarde);
                    horario.setMinutoFimManha(minutoFimManha);
                    horario.setMinutoFimTarde(minutoFimTarde);

                }
                while (cursor.moveToNext());
                cursor.close();
            }
            return horario;

    }

    }
