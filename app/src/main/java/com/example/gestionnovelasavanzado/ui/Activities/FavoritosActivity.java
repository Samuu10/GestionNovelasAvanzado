package com.example.gestionnovelasavanzado.ui.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.NovelaAdapter;
import java.util.ArrayList;
import java.util.List;

//Clase que representa la pantalla de favoritos
public class FavoritosActivity extends AppCompatActivity {

    //Variables
    private List<Novela> novelasFavoritas;
    private NovelaAdapter adapter;
    private Button btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        btnVolver.setOnClickListener(v -> {
            startActivity(new Intent(FavoritosActivity.this, MainActivity.class));
        });
    }
}