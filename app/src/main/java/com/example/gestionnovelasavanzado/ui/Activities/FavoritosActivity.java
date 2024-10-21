package com.example.gestionnovelasavanzado.ui.Activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.NovelaAdapter;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;

import java.util.ArrayList;
import java.util.List;

//Clase que representa la pantalla de favoritos
public class FavoritosActivity extends AppCompatActivity {

    //Variables
    private List<Novela> novelasFavoritas;
    private NovelaAdapter adapter;
    private Button btnVolver;
    private PreferencesManager preferencesManager;

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
        setContentView(R.layout.favoritos_activity);

        //Creación del ListView
        ListView listViewFavoritos = findViewById(R.id.list_view_favoritos);

        //Obtener las novelas favoritas de MainActivity a partir del intent
        novelasFavoritas = getIntent().getParcelableArrayListExtra("novelas_favoritas");
        if (novelasFavoritas == null) {
            novelasFavoritas = new ArrayList<>();
        }

        //Crear el adaptador con las novelas favoritas
        adapter = new NovelaAdapter(this, novelasFavoritas);
        listViewFavoritos.setAdapter(adapter);

        //Botón para volver al MainActivity
        btnVolver = findViewById(R.id.btn_volver);
        btnVolver.setOnClickListener(v -> finish());
    }
}