package br.com.produtividade;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import br.com.Controllers.GerenciadorDias;

public class DiasActivity extends AppCompatActivity {

    CheckBox chkDom, chkSeg, chkTer, chkQua, chkQui, chkSex, chkSab;
    String[] dias = new String[7];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dias);
        chkDom = (CheckBox) findViewById(R.id.checkDomingo);
        chkSeg = (CheckBox) findViewById(R.id.checkSegunda);
        chkTer = (CheckBox) findViewById(R.id.checkTerca);
        chkQua = (CheckBox) findViewById(R.id.checkQuarta);
        chkQui = (CheckBox) findViewById(R.id.checkQuinta);
        chkSex = (CheckBox) findViewById(R.id.checkSexta);
        chkSab = (CheckBox) findViewById(R.id.checkSabado);
        getSupportActionBar().setTitle("Dias de Bloqueio");
        dias = GerenciadorDias.retornaDias(getApplicationContext());
        String dia;
        for (int i =0; i <dias.length;i++) {
            if (dias[i] != null) {
                dia = dias[i];
                if (dia.equals("dom")) {
                    chkDom.setChecked(true);
                } else
                if (dia.equals("seg")) {
                    chkSeg.setChecked(true);
                } else
                if (dia.equals("ter")) {
                    chkTer.setChecked(true);
                } else
                if (dia.equals("qua")) {
                    chkQua.setChecked(true);
                } else
                if (dia.equals("qui")) {
                    chkQui.setChecked(true);
                } else
                if (dia.equals("sex")) {
                    chkSex.setChecked(true);
                } else
                if (dia.equals("sab")) {
                    chkSab.setChecked(true);
                }
            }
        }

    }

    public void btnOk(View v){
        Intent intent = new Intent(getApplicationContext(), HorasBloqueioActivity.class);

        if(chkDom.isChecked()){
            dias[0] = "Sunday";
        }
        if(chkSeg.isChecked()){
            dias[1] = "seg";
        }
        if(chkTer.isChecked()){
            dias[2] = "ter";
        }
        if(chkQua.isChecked()){
            dias[3] = "qua";
        }
        if(chkQui.isChecked()){
            dias[4] = "qui";
        }
        if(chkSex.isChecked()){
            dias[5] = "sex";
        }
        if(chkSab.isChecked()){
            dias[6] = "sab";
        }
        if(!chkDom.isChecked() &&
                !chkSeg.isChecked() &&
                !chkTer.isChecked() &&
                !chkQua.isChecked() &&
                !chkQui.isChecked() &&
                !chkSex.isChecked() &&
                !chkSab.isChecked()){
            Toast.makeText(this,"Você precisa escolher ao menos um dia", Toast.LENGTH_SHORT).show();
            return;
        }

        GerenciadorDias.SalvarDias(getApplicationContext(),dias);

        startActivity(intent);
    }

}
