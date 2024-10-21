package com.example.gestionnovelasavanzado.ui.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//Clase que se encarga de gestionar las preferencias de la aplicación del usuario
public class PreferencesManager {

    //Variables
    private static final String PREF_NAME = "user_preferences";
    private static final String KEY_THEME = "theme";
    private static final String KEY_NOVELAS = "novelas";
    private SharedPreferences sharedPreferences;
    private Gson gson;


    //Constructor
    public PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
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

    //Método para guardar la lista de novelas
    public void saveNovelas(List<Novela> novelas) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = gson.toJson(novelas);
        editor.putString(KEY_NOVELAS, json);
        editor.apply();
    }

    //Método para cargar la lista de novelas
    public List<Novela> loadNovelas() {
        String json = sharedPreferences.getString(KEY_NOVELAS, null);
        Type type = new TypeToken<ArrayList<Novela>>() {}.getType();
        return json != null ? gson.fromJson(json, type) : new ArrayList<>();
    }
}