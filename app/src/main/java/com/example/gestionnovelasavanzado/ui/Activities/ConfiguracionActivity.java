package com.example.gestionnovelasavanzado.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.Almacenamiento.BackupTask;
import com.example.gestionnovelasavanzado.ui.Almacenamiento.RestoreTask;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;
import java.util.List;

//Clase que representa la pantalla de configuración
public class ConfiguracionActivity extends AppCompatActivity {

    //Variables
    private PreferencesManager preferencesManager;
    private Button btnVolver, backupButton, restoreButton;
    private List<Novela> novelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencesManager = new PreferencesManager(this);

        //Aplicar el tema de la aplicación antes de setContentView
        if (preferencesManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion_activity);

        //Inicializar la lista de novelas
        novelas = preferencesManager.loadNovelas();

        //Switch para cambiar el tema de la aplicación
        Switch themeSwitch = findViewById(R.id.switch_theme);
        themeSwitch.setChecked(preferencesManager.isDarkMode());
        //Función flecha para cambiar el tema
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferencesManager.setTheme(isChecked);
            //Reiniciar la actividad para aplicar el tema
            AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            recreate();
        });

        //Botón para hacer backup de los datos
        backupButton = findViewById(R.id.btn_backup);
        backupButton.setOnClickListener(v -> backupData());

        //Botón para restaurar los datos
        restoreButton = findViewById(R.id.btn_restore);
        restoreButton.setOnClickListener(v -> restoreData());

        //Botón para volver al MainActivity
        btnVolver = findViewById(R.id.btn_volver);
        btnVolver.setOnClickListener(v -> finish());
    }

    //Metodo para hacer una copia de seguridad de los datos
    protected void backupData() {
        new BackupTask(this, novelas, preferencesManager.isDarkMode()).execute();
    }

    //Metodo para restaurar los datos
    protected void restoreData() {
        new RestoreTask(this, novelas, preferencesManager).execute();
        //Reiniciar la actividad principal después de restaurar los datos
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}