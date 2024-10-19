package com.example.gestionnovelasavanzado.ui.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;

public class ConfiguracionActivity extends AppCompatActivity {

    //Variables
    private PreferencesManager preferencesManager;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferencesManager = new PreferencesManager(this);

        // Aplicar el tema antes de setContentView
        if (preferencesManager.isDarkMode()) {
            setTheme(R.style.Theme_GestionNovelasAvanzado_Night);
        } else {
            setTheme(R.style.Theme_GestionNovelasAvanzado);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion_activity);

        Switch themeSwitch = findViewById(R.id.switch_theme);
        themeSwitch.setChecked(preferencesManager.isDarkMode());
        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            preferencesManager.setTheme(isChecked);
            recreate(); // Recrear la actividad para aplicar el nuevo tema
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