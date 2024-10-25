package com.example.gestionnovelasavanzado.ui.Almacenamiento;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.Toast;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class RestoreTask extends AsyncTask<Void, Void, Boolean> {
    //Variables
    private Context context;
    private List<Novela> novelas;
    private PreferencesManager preferencesManager;
    private Handler handler;

    //Constructor
    public RestoreTask(Context context, List<Novela> novelas, PreferencesManager preferencesManager) {
        this.context = context;
        this.novelas = novelas;
        this.preferencesManager = preferencesManager;
        this.handler = new Handler();
    }

    //Método para mostrar un mensaje antes de iniciar la restauración de datos
    @Override
    protected void onPreExecute() {
        handler.post(() -> Toast.makeText(context, "Iniciando restauración de datos...", Toast.LENGTH_SHORT).show());
    }

    //Método para realizar la restauración de datos en segundo plano y cargar los datos desde un archivo
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            File file = new File(context.getExternalFilesDir(null), "backup.txt");
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();
            String[] novelasData = new String(data).split("\n");
            novelas.clear();
            for (String novelaData : novelasData) {
                String[] fields = novelaData.split(",");
                if (fields[0].equals("theme")) {
                    preferencesManager.setTheme(Boolean.parseBoolean(fields[1]));
                } else {
                    Novela novela = new Novela(fields[0], fields[1], Integer.parseInt(fields[2]), fields[3]);
                    novela.setFavorito(Boolean.parseBoolean(fields[4]));
                    novelas.add(novela);
                }
            }
            preferencesManager.saveNovelas(novelas);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Método para mostrar un mensaje después de completar la restauración de datos
    @Override
    protected void onPostExecute(Boolean result) {
        handler.post(() -> {
            if (result) {
                Toast.makeText(context, "Restauración completada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Restauración fallida", Toast.LENGTH_SHORT).show();
            }
        });
    }
}