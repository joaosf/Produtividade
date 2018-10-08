package br.com.produtividade;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by gabrielnovakovski on 15/07/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ALARME", "O alarme executou as: "+new Date());
        //AlarmActivity.getTxt().setText("FUNCIONA");
        Intent in = new Intent(context, PasscodeActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);

    }

}