package br.com.produtividade;



import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

import br.com.Controllers.GerenciadorDespertador;
import br.com.Controllers.GerenciadorDias;
import br.com.Model.Horario;

public class HorasBloqueioActivity extends AppCompatActivity {
    static int botaoEscolhido;
    static TextView txtHoraInicioManha, txtHoraFimManha, txtHoraInicioTarde, txtHoraFimTarde;
    private static final int MY_PERMISSIONS_REQUEST_WAKE_LOCK = 1;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    static int horaInicioManha;
    static int minutoInicioManha;
    static int horaFimManha;
    static int minutoFimManha;
    static int horaInicioTarde;
    static int minutoInicioTarde;
    static int horaFimTarde;
    static int minutoFimTarde;
    String emailUsuarioLogado;
    String dom, seg, ter, qua, qui, sex, sab;
    String[] dias;
    Horario horario = new Horario();
    Horario horarioCarregar = new Horario();
    private FirebaseAuth.AuthStateListener mAuth;
    Button btnSalvar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horas_bloqueio);
        txtHoraInicioManha = (TextView) findViewById(R.id.txtHoraInicioManha);
        txtHoraFimManha = (TextView) findViewById(R.id.txtHoraFimManha);
        txtHoraInicioTarde = (TextView) findViewById(R.id.txtHoraInicioTarde);
        txtHoraFimTarde = (TextView) findViewById(R.id.txtHoraFimTarde);
        getSupportActionBar().setTitle("Horário de Bloqueio");
        btnSalvar = (Button) findViewById(R.id.btnSalvarHorario);
        carregaHorarios();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            emailUsuarioLogado = user.getEmail();
        } else {
            // No user is signed in
        }
        btnSalvar.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
              salvar();
            }

        });
        if (ContextCompat.checkSelfPermission(HorasBloqueioActivity.this,
                android.Manifest.permission.WAKE_LOCK)
                != PackageManager.PERMISSION_GRANTED) {
            // Precisamos dar uma explicação?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HorasBloqueioActivity.this,
                    android.Manifest.permission.WAKE_LOCK)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(HorasBloqueioActivity.this,
                        new String[]{android.Manifest.permission.SET_ALARM, android.Manifest.permission.WAKE_LOCK, android.Manifest.permission.INTERNET},
                        MY_PERMISSIONS_REQUEST_WAKE_LOCK);
                // MY_PERMISSIONS_REQUEST_CAMERA is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }


        }

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
           return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            if(botaoEscolhido == 1){
                txtHoraInicioManha.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                horaInicioManha = hourOfDay;
                minutoInicioManha = minute;
            }
            else if(botaoEscolhido == 2){
                txtHoraFimManha.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                horaFimManha = hourOfDay;
                minutoFimManha = minute;
            }
            else if(botaoEscolhido == 3){
                txtHoraInicioTarde.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                horaInicioTarde = hourOfDay;
                minutoInicioTarde = minute;
            }
            else if(botaoEscolhido == 4){
                txtHoraFimTarde.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
                horaFimTarde = hourOfDay;
                minutoFimTarde = minute;
            }

        }
    }

    public void timePickerInicioManha(View v) {
        botaoEscolhido = 1;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Escolha a data de Início da Manhã");
    }

    public void timePickerFimManha(View v) {
        botaoEscolhido = 2;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Escolha a data de Fim da Manhã");
    }
    public void timePickerInicioTarde(View v) {
        botaoEscolhido = 3;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Escolha a data de Início da Manhã");
    }
    public void timePickerFimTarde(View v) {
        botaoEscolhido = 4;
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "Escolha a data de Fim da Manhã");
    }

    public void salvar(){

        GerenciadorDespertador gDespertador = new GerenciadorDespertador();

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        final int id = (int) System.currentTimeMillis();
        alarmIntent = PendingIntent.getBroadcast(this, id, intent, 0);

        salvarHorarioManha(horario);
        salvarHorarioTarde(horario);
        gDespertador.excluirHorario(getApplicationContext());
        gDespertador.SalvarHorario(getApplicationContext(), horario);
        Toast.makeText(this, "Salvo com Sucesso", Toast.LENGTH_SHORT).show();
        Intent intentInfo = new Intent();
        startActivity(intentInfo);


    }

    public void carregaHorarios(){
        GerenciadorDespertador gerenciadorDespertador = new GerenciadorDespertador();
        horarioCarregar = gerenciadorDespertador.retornaHorario(getApplicationContext());
        if(horarioCarregar.getHoraInicioManha() != null) {
            txtHoraInicioManha.setText(horarioCarregar.getHoraInicioManha() + ":" + horarioCarregar.getMinutoInicioManha());
        }
        if(horarioCarregar.getHoraFimManha() != null) {
            txtHoraFimManha.setText(horarioCarregar.getHoraFimManha() + ":" + horarioCarregar.getMinutoFimManha());
        }
        if(horarioCarregar.getHoraInicioTarde() != null) {
            txtHoraInicioTarde.setText(horarioCarregar.getHoraInicioTarde() + ":" + horarioCarregar.getMinutoInicioTarde());
        }
        if(horarioCarregar.getHoraFimTarde() != null) {
            txtHoraFimTarde.setText(horarioCarregar.getHoraFimTarde() + ":" + horarioCarregar.getMinutoFimTarde());
        }
    }

    public void salvarHorarioManha(Horario horario){
        if(horaInicioManha != 0 && minutoInicioManha != 0) {
            salvaDiasManha();
        }
        horario.setHoraInicioManha(Integer.toString(horaInicioManha));
        horario.setMinutoInicioManha(Integer.toString(minutoInicioManha));
        horario.setMinutoFimManha(Integer.toString(minutoFimManha));
        horario.setHoraFimManha(Integer.toString(horaFimManha));
    }

    public void salvarHorarioTarde(Horario horario){
        if(horaInicioTarde != 0 && minutoInicioTarde != 0) {
           salvaDiasTarde();
        }
        horario.setHoraInicioTarde(Integer.toString(horaInicioTarde));
        horario.setMinutoInicioTarde(Integer.toString(minutoInicioTarde));
        horario.setMinutoFimTarde(Integer.toString(minutoFimTarde));
        horario.setHoraFimTarde(Integer.toString(horaFimTarde));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WAKE_LOCK: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    void salvaDiasManha(){
         dias = GerenciadorDias.retornaDias(getApplicationContext());
        String dia;
        for (int i =0; i <dias.length;i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, horaInicioManha);
            calendar.set(Calendar.MINUTE, minutoInicioManha);
            calendar.set(Calendar.DAY_OF_YEAR, getDayOfWeek(dias[i]));
            Log.i("ALARME", "setando o alarme para" + calendar.getTime());

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    1000 * 60 * 2, alarmIntent);

           // alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }

    void salvaDiasTarde(){
        dias = GerenciadorDias.retornaDias(getApplicationContext());
        String dia;
        for (int i =0; i <dias.length;i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, horaInicioTarde);
            calendar.set(Calendar.MINUTE, minutoInicioTarde);
            calendar.set(Calendar.DAY_OF_YEAR, getDayOfWeek(dias[i]));
            Log.i("ALARME", "setando o alarme para" + calendar.getTime());

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    1000 * 60 * 2, alarmIntent);
          //  alarmMgr.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
        }
    }

    private int getDayOfWeek(String var) {
        Calendar calendar = Calendar.getInstance();
        if (var != null && var.equals("dom")) {
            return calendar.SUNDAY;
        } else
        if (var != null && var.equals("seg")) {
            return calendar.MONDAY;
        } else
        if (var != null && var.equals("ter")) {
            return calendar.TUESDAY;
        } else
        if (var != null && var.equals("qua")) {
            return calendar.WEDNESDAY;
        } else
        if (var != null && var.equals("qui")) {
            return calendar.THURSDAY;
        } else
        if (var != null && var.equals("sex")) {
            return calendar.FRIDAY;
        } else
        if (var != null && var.equals("sab")) {
            return calendar.SATURDAY;
        }
        return 90;
    }



}



