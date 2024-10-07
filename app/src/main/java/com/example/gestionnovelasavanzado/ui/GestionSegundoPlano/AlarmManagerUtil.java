package com.example.gestionnovelasavanzado.ui.GestionSegundoPlano;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import java.util.Calendar;

//Clase AlarmManagerUtil para programar tareas periódicas de sincronización
public class AlarmManagerUtil {

    //Método para programar la tarea de sincronización con AlarmManager
    public static void manageSync(Context context) {
        Intent intent = new Intent(context, ConexionReceiver.class);

        //Establecer el flag de PendingIntent de acuerdo a la versión de Android
        int pendingIntentFlag = PendingIntent.FLAG_UPDATE_CURRENT;
        //Si la versión de Android es mayor o igual a 3
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            //Establecer el flag de PendingIntent como mutable (mutable significa que el PendingIntent puede ser modificado)
            pendingIntentFlag |= PendingIntent.FLAG_MUTABLE;
        }

        //Crear un PendingIntent para la tarea de sincronización y configurarlo para que se repita cada 2 minutos
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, pendingIntentFlag);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            //Configurar la tarea de sincronización para que se repita cada 2 minutos
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, 2);

            //Programar la tarea de sincronización para que se repita cada 2 minutos
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 120000, pendingIntent);
        }
    }
}