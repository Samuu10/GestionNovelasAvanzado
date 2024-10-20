package com.example.gestionnovelasavanzado.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;

public class ConfiguracionActivity extends AppCompatActivity {

    //Variables
    private PreferencesManager preferencesManager;
    private Button btnVolver;

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

        //Botón para hacer backup de la los datos
        findViewById(R.id.btn_backup).setOnClickListener(v -> {
            ((MainActivity) getParent()).backupData();
        });

        //Botón para restaurar los datos
        findViewById(R.id.btn_restore).setOnClickListener(v -> {
            ((MainActivity) getParent()).restoreData();
        });

        //Botón para volver al MainActivity
        btnVolver = findViewById(R.id.btn_volver);
        btnVolver.setOnClickListener(v -> finish());
    }
}