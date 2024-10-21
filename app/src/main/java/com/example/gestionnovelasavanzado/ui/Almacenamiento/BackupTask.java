package com.example.gestionnovelasavanzado.ui.Almacenamiento;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class BackupTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private List<Novela> novelas;
    private boolean isDarkMode;
    private Handler handler;

    public BackupTask(Context context, List<Novela> novelas, boolean isDarkMode) {
        this.context = context;
        this.novelas = novelas;
        this.isDarkMode = isDarkMode;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onPreExecute() {
        handler.post(() -> Toast.makeText(context, "Iniciando copia de seguridad...", Toast.LENGTH_SHORT).show());
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            File file = new File(context.getExternalFilesDir(null), "backup.txt");
            FileOutputStream fos = new FileOutputStream(file);
            for (Novela novela : novelas) {
                String data = novela.getTitulo() + "," + novela.getAutor() + "," + novela.getAñoPublicacion() + "," + novela.getSinopsis() + "," + novela.getFavorito() + "\n";
                fos.write(data.getBytes());
            }
            fos.write(("theme," + isDarkMode + "\n").getBytes());
            fos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        handler.post(() -> {
            if (result) {
                Toast.makeText(context, "Copia de seguridad completada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Copia de seguridad fallida", Toast.LENGTH_SHORT).show();
            }
        });
    }
}