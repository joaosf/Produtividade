package br.com.produtividade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.Controllers.GerenciadorDespertador;
import br.com.Controllers.GerenciadorDias;
import br.com.Controllers.GerenciadorSenha;
import br.com.Model.Horario;

public class BemVindoActivity extends AppCompatActivity {
    int temSenha;
    GerenciadorDias gDias = new GerenciadorDias();
    GerenciadorDespertador gDes = new GerenciadorDespertador();
    GerenciadorSenha gSenha = new GerenciadorSenha();
    Horario horario;
    TextView txtEditaHorario, txtCadastraHorario, txtCadastraSenha, txtEditaSenha;
    Button btnCadastraHorario, btnEditaHorario, btnEditaSenha, btnCadastraSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);
        gDias = new GerenciadorDias();
        gDes = new GerenciadorDespertador();
        String[] dias = gDias.retornaDias(getApplicationContext());
        horario = gDes.retornaHorario(getApplicationContext());
        gSenha = new GerenciadorSenha();

        txtEditaHorario = (TextView) findViewById(R.id.txtEditaHorarios);
        txtCadastraHorario = (TextView) findViewById(R.id.txtCadastraHorarios);
        txtEditaSenha = (TextView) findViewById(R.id.txtEditaSenha);
        txtCadastraSenha = (TextView) findViewById(R.id.txtCadastraSenha);
        btnCadastraSenha = (Button) findViewById(R.id.btnCadastraSenha);
        btnEditaSenha = (Button) findViewById(R.id.btnEditaSenha);
        btnCadastraHorario = (Button) findViewById(R.id.btnCadastraHorarios);
        btnEditaHorario = (Button) findViewById(R.id.btnEditaHorarios);
        btnCadastraSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarSenha();
            }
        });
        btnEditaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarSenha();
            }
        });
        btnCadastraHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarHorario();
            }
        });
        btnEditaHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarHorario();
            }
        });
        temSenha = gSenha.retornaSenha(getApplicationContext());
        txtCadastraHorario.setVisibility(View.GONE);
        btnCadastraHorario.setVisibility(View.GONE);
        txtEditaHorario.setVisibility(View.GONE);
        btnEditaHorario.setVisibility(View.GONE);

        if(temSenha > 0){
            txtCadastraSenha.setVisibility(View.GONE);
            btnCadastraSenha.setVisibility(View.GONE);
        }
        else{
            txtEditaSenha.setVisibility(View.GONE);
            btnEditaSenha.setVisibility(View.GONE);
        }
        if(temSenha > 0) {
            if (horario != null) {
                txtCadastraHorario.setVisibility(View.GONE);
                btnCadastraHorario.setVisibility(View.GONE);
                txtEditaHorario.setVisibility(View.VISIBLE);
                btnEditaHorario.setVisibility(View.VISIBLE);
            } else {
                txtEditaHorario.setVisibility(View.GONE);
                btnEditaHorario.setVisibility(View.GONE);
                txtCadastraHorario.setVisibility(View.VISIBLE);
                btnCadastraHorario.setVisibility(View.VISIBLE);
            }
        }
    }

    void cadastrarSenha(){
        Intent intent = new Intent(this, ResetPasscodeActivity.class);
        startActivity(intent);
    }
    void cadastrarHorario(){
        Intent intent = new Intent(this, DiasActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume(){
        super.onResume();
        horario = gDes.retornaHorario(getApplicationContext());
        temSenha = gSenha.retornaSenha(getApplicationContext());
        if(temSenha > 0){
            txtCadastraSenha.setVisibility(View.GONE);
            btnCadastraSenha.setVisibility(View.GONE);
            txtEditaSenha.setVisibility(View.VISIBLE);
            btnEditaSenha.setVisibility(View.VISIBLE);
        }
        else{
            txtEditaSenha.setVisibility(View.GONE);
            btnEditaSenha.setVisibility(View.GONE);
            txtCadastraSenha.setVisibility(View.VISIBLE);
            btnCadastraSenha.setVisibility(View.VISIBLE);
        }
        if(temSenha > 0) {
            if (horario != null) {
                txtCadastraHorario.setVisibility(View.GONE);
                btnCadastraHorario.setVisibility(View.GONE);
                txtEditaHorario.setVisibility(View.VISIBLE);
                btnEditaHorario.setVisibility(View.VISIBLE);
            } else {
                txtEditaHorario.setVisibility(View.GONE);
                btnEditaHorario.setVisibility(View.GONE);
                txtCadastraHorario.setVisibility(View.VISIBLE);
                btnCadastraHorario.setVisibility(View.VISIBLE);
            }
        }
    }


}
