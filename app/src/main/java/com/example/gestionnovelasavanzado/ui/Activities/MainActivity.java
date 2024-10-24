package com.example.gestionnovelasavanzado.ui.Activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.NovelaAdapter;
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.AlarmManagerUtil;
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.FirebaseHelper;
import com.example.gestionnovelasavanzado.ui.GestionSegundoPlano.SyncTask;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

//Clase que representa la pantalla principal
public class MainActivity extends AppCompatActivity{

    //Variables
    private List<Novela> novelas;
    private NovelaAdapter adapter;
    private FirebaseHelper firebaseHelper;
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
        setContentView(R.layout.activity_main);

        int iconColor = getResources().getColor(R.color.iconColor);

        //Inicializar listas
        novelas = preferencesManager.loadNovelas();

        //Configurar la lista de novelas y el adaptador

        RecyclerView recyclerView = findViewById(R.id.recycler_view_novelas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NovelaAdapter(this, novelas, novela -> mostrarDetallesNovela(novela));
        recyclerView.setAdapter(adapter);

        //Inicializar FirebaseHelper
        firebaseHelper = new FirebaseHelper();

        //Configurar los botones para gestionar las novelas
        findViewById(R.id.btn_agregar).setOnClickListener(v -> mostrarDialogoAñadirNovela());
        findViewById(R.id.btn_eliminar).setOnClickListener(v -> mostrarDialogoEliminarNovela());

        //Botón para ir a la actividad favoritos
        findViewById(R.id.btn_favoritos).setOnClickListener(v -> {
            //Crear un intent para ir a la actividad favoritos
            Intent intent = new Intent(MainActivity.this, FavoritosActivity.class);
            //Pasar la lista de novelas favoritas a la actividad favoritos a través del método putParcelableArrayListExtra
            intent.putParcelableArrayListExtra("novelas_favoritas", obtenerNovelasFavoritas());
            //Iniciar la actividad favoritos
            startActivity(intent);
        });

        //Botón para sincronizar la lista de novelas con Firebase
        ImageButton syncButton = findViewById(R.id.ic_sync);
        syncButton.setColorFilter(new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN));
        syncButton.setOnClickListener(v -> {
            new SyncTask(this, novelas).execute();
        });

        //Botón para ir a la actividad de configuración
        ImageButton menuButton = findViewById(R.id.menu_hamburguesa);
        menuButton.setColorFilter(new PorterDuffColorFilter(iconColor, PorterDuff.Mode.SRC_IN));
        menuButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
            startActivity(intent);
        });

        //Sincronización automática con AlarmManager cada 2 minutos
        AlarmManagerUtil.manageSync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Actualizar la lista de novelas
        adapter.notifyDataSetChanged();
    }

    //Método para mostrar el diálogo para añadir novela a la lista
    private void mostrarDialogoAñadirNovela() {
        //Crear el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.agregar_novela, null);
        EditText editTextTitle = view.findViewById(R.id.editTextTituloAñadir);

        //Configurar el diálogo
        builder.setView(view)
                .setTitle("Añadir Novela a la Lista")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Añadir", (dialog, id) -> {
                    String title = editTextTitle.getText().toString();
                    añadirNovelaLista(title);
                });
        builder.create().show();
    }

    //Método para mostrar el diálogo para eliminar novela de la lista
    private void mostrarDialogoEliminarNovela() {
        //Crear el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.eliminar_novela, null);
        EditText editTextTitle = view.findViewById(R.id.editTextTituloEliminar);

        //Configurar el diálogo
        builder.setView(view)
                .setTitle("Eliminar Novela de la Lista")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Eliminar", (dialog, id) -> {
                    String title = editTextTitle.getText().toString();
                    eliminarNovelaLista(title);
                });

        builder.create().show();
    }

    //Método que busca la novela en Firebase y la añade solo a la lista
    private void añadirNovelaLista(String title) {
        //Verificar primero si la novela ya está en la lista antes de consultar Firebase
        for (Novela novela : novelas) {
            //Si está en la lista, la novela no se añade y se muestra un mensaje
            if (novela.getTitulo().equalsIgnoreCase(title)) {
                Toast.makeText(MainActivity.this, "La novela ya está en la lista", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        //Si no está en la lista, buscar en Firebase
        firebaseHelper.cargarNovelas(title, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Si la novela existe en Firebase la añadimos a la lista
                if (snapshot.exists()) {
                    //Bucle para encontrar la novela en Firebase
                    for (DataSnapshot novelaSnapshot : snapshot.getChildren()) {
                        Novela novela = novelaSnapshot.getValue(Novela.class);
                        if (novela != null) {
                            //Añadir a la lista local si no está duplicada (verificado previamente)
                            novelas.add(novela);
                            adapter.notifyDataSetChanged();
                            preferencesManager.saveNovelas(novelas);
                            Toast.makeText(MainActivity.this, "Novela añadida a la lista", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                //Si la novela no existe en Firebase se muestra un mensaje
                } else {
                    Toast.makeText(MainActivity.this, "Novela no encontrada", Toast.LENGTH_SHORT).show();
                }
            }

            //Si ocurre un error al buscar, se muestra un mensaje
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error al buscar novela: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Método para eliminar novela de la lista local
    private void eliminarNovelaLista(String titulo) {
        boolean encontrado = false;
        //Bucle para encontrar la novela en la lista
        for (int i = 0; i < novelas.size(); i++) {
            //Si la novela está en la lista se elimina
            if (novelas.get(i).getTitulo().equalsIgnoreCase(titulo)) {
                novelas.remove(i);
                adapter.notifyDataSetChanged();
                preferencesManager.saveNovelas(novelas);
                encontrado = true;
                break;
            }
        }
        //Si la novela se ha eliminado se muestra un mensaje de exito
        if (encontrado) {
            Toast.makeText(MainActivity.this, "Novela eliminada de la lista", Toast.LENGTH_SHORT).show();
        //Si la novela no se ha eliminado se muestra un mensaje de error
        } else {
            Toast.makeText(MainActivity.this, "Novela no encontrada", Toast.LENGTH_SHORT).show();
        }
        //Actualización de la interfaz después de eliminar la novela
        adapter.notifyDataSetChanged();
    }

    //Método para mostrar los detalles de la novela seleccionada
    public void mostrarDetallesNovela(Novela novela) {
        //Crear el diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.detalles_novela, null);

        //Enlazar los elementos de la vista con los atributos de la novela
        TextView textViewTitulo = view.findViewById(R.id.textViewTitulo);
        TextView textViewAutor = view.findViewById(R.id.textViewAutor);
        TextView textViewAnio = view.findViewById(R.id.textViewAño);
        TextView textViewSinopsis = view.findViewById(R.id.textViewSinopsis);

        //Mostrar los detalles de la novela
        textViewTitulo.setText(novela.getTitulo());
        textViewAutor.setText(novela.getAutor());
        textViewAnio.setText("Año: " + novela.getAñoPublicacion());
        textViewSinopsis.setText(novela.getSinopsis());

        //Configurar el diálogo
        builder.setView(view)
                .setTitle("Detalles de la Novela")
                .setPositiveButton("Cerrar", null)
                .setNeutralButton(novela.getFavorito() ? "Eliminar de Favoritos" : "Añadir a Favoritos", (dialog, id) -> {
                    novela.setFavorito(!novela.getFavorito());
                    preferencesManager.saveNovelas(novelas);
                    Toast.makeText(this, novela.getFavorito() ? "Añadida a favoritos" : "Eliminada de favoritos", Toast.LENGTH_SHORT).show();
                });
        builder.create().show();
    }

    //Método para obtener las novelas favoritas
    public ArrayList<Novela> obtenerNovelasFavoritas() {
        ArrayList<Novela> favoritas = new ArrayList<>();
        for (Novela novela : novelas) {
            if (novela.getFavorito()) {
                favoritas.add(novela);
            }
        }
        return favoritas;
    }
}