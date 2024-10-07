package com.example.gestionnovelasavanzado.ui.GestionSegundoPlano;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

//Clase ConexionReceiver que hereda de BroadcastReceiver y que detecta la conexión
public class ConexionReceiver extends BroadcastReceiver {

    //Método onReceive para detectar la conexión y avisar mediante un mensaje
    @Override
    public void onReceive(Context context, Intent intent) {
        //Verificar si hay conexión
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        //Si hay conexión se inicia la sincronización
        if (activeNetwork != null && activeNetwork.isConnected()) {
            //Verificar si la conexión es de tipo Wi-Fi antes de iniciar la sincronización
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(context, "Conexión Wi-Fi detectada, iniciando sincronización...", Toast.LENGTH_SHORT).show();
                //Iniciar la sincronización
                new SyncTask(context, null).execute();
            //Si la conexión no es de tipo Wi-Fi se muestra un mensaje de error
            } else {
                Toast.makeText(context, "Sincronización sólo habilitada con Wi-Fi.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
