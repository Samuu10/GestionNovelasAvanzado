package com.example.gestionnovelasavanzado.ui.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

//Clase que se encarga de gestionar las preferencias de la aplicación del usuario
public class PreferencesManager {

    //Variables
    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_THEME = "theme";
    private SharedPreferences sharedPreferences;

    //Constructor
    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    //Método para guardar el tema de la aplicación
    public void setTheme(boolean isDarkMode) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_THEME, isDarkMode);
        editor.apply();
    }

    //Método para saber si el tema de la aplicación es oscuro o no
    public boolean isDarkMode() {
        return sharedPreferences.getBoolean(KEY_THEME, false);
    }
}
