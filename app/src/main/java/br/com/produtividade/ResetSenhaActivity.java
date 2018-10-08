package br.com.produtividade;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetSenhaActivity extends AppCompatActivity {
    Button btnVoltarLogin, btnResetaSenha;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private EditText txtEmailResetaSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);
        txtEmailResetaSenha = (EditText) findViewById(R.id.txtEmailReset);
        progressBar = (ProgressBar) findViewById(R.id.progressBarReset);
        btnResetaSenha = (Button) findViewById(R.id.btnResetarSenha);
        btnVoltarLogin = (Button) findViewById(R.id.btnVoltarLogin);
        auth = FirebaseAuth.getInstance();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Resetar Senha");


        btnVoltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnResetaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = txtEmailResetaSenha.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Digite seu e-mail", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetSenhaActivity.this, "Mandamos para seu e-mail as instruções para troca de senha", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetSenhaActivity.this, "Falha ao enviar o e-mail de troca de senha!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });

    }


}
