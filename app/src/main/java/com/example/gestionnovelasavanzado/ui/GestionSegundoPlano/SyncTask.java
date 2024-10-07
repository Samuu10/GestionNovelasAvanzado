package com.example.gestionnovelasavanzado.ui.GestionSegundoPlano;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

//Clase SyncTask para sincronizar la lista de novelas con Firebase
public class SyncTask extends AsyncTask<Void, Void, Boolean> {

    //Variables
    private Context context;
    private List<Novela> novelas;

    //Constructor
    public SyncTask(Context context, List<Novela> novelas) {
        this.context = context;
        this.novelas = novelas;
    }

    //Método doInBackground para sincronizar la lista de novelas con Firebase y notificar al usuario (realizado en 2º plano)
    @Override
    protected Boolean doInBackground(Void... voids) {
        if (novelas == null) return false;

        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("novelas");

            for (Novela novela : novelas) {
                databaseReference.child(novela.getId()).setValue(novela);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Método onPostExecute para notificar al usuario de la sincronización
    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            mostrarNotificacion(context, "Sincronización completada", "Las novelas se han sincronizado correctamente.");
        } else {
            mostrarNotificacion(context, "Error en la sincronización", "No se pudo sincronizar con el servidor.");
        }
    }


    //Método para mostrar una notificación al usuario
    private void mostrarNotificacion(Context context, String titulo, String mensaje) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String canalId = "sync_channel";

        //Crear el canal de notificación
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(canalId, "Sincronización", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        //Configurar y mostrar la notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, canalId)
                .setSmallIcon(R.drawable.ic_sync)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1, builder.build());
    }
}