package com.example.gestionnovelasavanzado.ui.GestionSegundoPlano;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;

//Clase SyncTask para sincronizar la lista de novelas con Firebase
public class SyncTask extends AsyncTask<Void, Integer, String> {

    //Variables
    private Context context;
    private List<Novela> novelas;

    //Constructor
    public SyncTask(Context context, List<Novela> novelas) {
        this.context = context;
        this.novelas = novelas;
    }

    //Método doInBackground para sincronizar la lista de novelas con Firebase
    @Override
    protected String doInBackground(Void... voids) {
        if (novelas == null || novelas.isEmpty()) {
            return "No hay novelas para sincronizar.";
        }

        try {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("novelas");

            int totalNovelas = novelas.size();
            for (int i = 0; i < totalNovelas; i++) {
                Novela novela = novelas.get(i);
                databaseReference.child(novela.getId()).setValue(novela);

                //Simular el progreso de la sincronización
                int progress = (int) ((i + 1) / (float) totalNovelas * 100);
                publishProgress(progress);

                //Simular un pequeño retraso por cada novela
                Thread.sleep(500); // Puedes ajustar este tiempo si es necesario
            }

            return "Sincronización completada con éxito.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error en la sincronización.";
        }
    }

    //Método onPreExecute para mostrar mensaje antes de iniciar la sincronización
    @Override
    protected void onPreExecute() {
        //Mostrar notificación de inicio de sincronización
        Toast.makeText(context, "Iniciando sincronización...", Toast.LENGTH_SHORT).show();
    }

    //Método onPostExecute para notificar al usuario de la sincronización
    @Override
    protected void onPostExecute(String resultado) {
        //Mostrar notificación según el resultado de la sincronización
        Toast.makeText(context, resultado, Toast.LENGTH_SHORT).show();
        mostrarNotificacion(context, "Sincronización", resultado);
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