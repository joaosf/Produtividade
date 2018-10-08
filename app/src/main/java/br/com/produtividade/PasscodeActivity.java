package br.com.produtividade;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hanks.passcodeview.PasscodeView;

import br.com.Controllers.GerenciadorSenha;
import br.com.Model.Mail;

/**
 * Created by hanks on 2017/4/17.
 */

public class PasscodeActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    FirebaseUser user;
    String email;
    private PendingIntent alarmIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
             email = user.getEmail();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }



        setContentView(R.layout.activity_passcode);
        PasscodeView passcodeView = (PasscodeView) findViewById(R.id.passcodeView);
        passcodeView
                .setPasscodeLength(4)
                .setLocalPasscode(Integer.toString(GerenciadorSenha.retornaSenha(getApplicationContext())))
                .setCorrectInputTip("PIN correto")
                .setSecondInputTip("Coloque seu PIN novamente")
                .setWrongInputTip("Você errou seu PIN")
                .setWrongLengthTip("Você precisa digitar 4 digitos")

                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {

                    }

                    @Override
                    public void onSuccess(String number) {
                        Toast.makeText(getApplication(), "PIN correto", Toast.LENGTH_SHORT).show();
                        if(alarmMgr != null){
                            alarmMgr.cancel(alarmIntent);
                        }
                        enviarEmail();
                        finish();
                    }
                });
    }

    @Override
    public void onBackPressed(){
        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);

        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 2*60*1000,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);

        this.finish();
    }

    public boolean isOnline() {
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(), "Erro ao verificar se estava online! (" + ex.getMessage() + ")", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void enviarEmail(){



        if(isOnline()) {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    Mail m = new Mail("appprodutividade@gmail.com", "AppProdutividade12!");

                    String[] toArr = {email};
                    m.setTo(toArr);

                    m.setFrom("appprodutividade@gmail.com");
                    m.setSubject("App Produtividade");
                    m.setBody(" Você parou de ser produtivo! Vamos lá, sabemos que consegue ser mais produtivo da próxima vez! ");

                    try {
                        //m.addAttachment("pathDoAnexo");//anexo opcional
                        m.send();
                    }
                    catch(RuntimeException rex){ }//erro ignorado
                    catch(Exception e) {
                        //tratar algum outro erro aqui
                    }

                    //System.exit(0);
                }
            }).start();
        }
        else {
            Toast.makeText(getApplicationContext(), "Não estava online para enviar e-mail!", Toast.LENGTH_SHORT).show();
            System.exit(0);
        }
    }

}
