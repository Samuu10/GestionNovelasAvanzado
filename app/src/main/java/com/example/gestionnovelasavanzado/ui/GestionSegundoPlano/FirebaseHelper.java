package com.example.gestionnovelasavanzado.ui.GestionSegundoPlano;

import android.os.AsyncTask;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

//Clase FirebaseHelper para realizar operaciones con Firebase
public class FirebaseHelper {

    //Variables
    private final FirebaseDatabase database;
    private final List<Novela> novelas;

    //Constructor
    public FirebaseHelper() {
        database = FirebaseDatabase.getInstance();
        this.novelas = new ArrayList<>();
    }

    //Metodo para cargar las novelas
    public void cargarNovelas(String titulo, ValueEventListener listener) {
        new CargarNovelasTask(titulo, listener).execute();
    }

    //Clase interna CargarNovelasTask que extiende AsyncTask y  carga las novelas de Firebase en segundo plano
    private class CargarNovelasTask extends AsyncTask<Void, Void, Void> {

        //Variables
        private String titulo;
        private ValueEventListener listener;

        //Constructor
        public CargarNovelasTask(String titulo, ValueEventListener listener) {
            this.titulo = titulo;
            this.listener = listener;
        }

        //Metodo doInBackground para cargar las novelas de Firebase
        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseReference databaseReference = database.getReference("novelas");
            Query query = databaseReference.orderByChild("titulo").equalTo(titulo);
            query.addListenerForSingleValueEvent(listener);
            return null;
        }
    }
}