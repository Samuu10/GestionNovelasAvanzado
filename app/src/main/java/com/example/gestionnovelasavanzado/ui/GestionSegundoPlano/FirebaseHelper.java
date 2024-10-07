package com.example.gestionnovelasavanzado.ui.GestionSegundoPlano;

import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseHelper {

    //Instancia de FirebaseDatabase
    private final FirebaseDatabase database;
    //Lista de novelas
    private final List<Novela> novelas;

    //Constructor
    public FirebaseHelper() {
        database = FirebaseDatabase.getInstance();
        this.novelas = new ArrayList<>();
    }

    //Método para cargar novelas desde Firebase
    public void cargarNovelas(String titulo, ValueEventListener listener) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("novelas");
        // Filtrar por el título especificado
        Query query = databaseReference.orderByChild("titulo").equalTo(titulo);
        query.addListenerForSingleValueEvent(listener);
    }

    //Método para obtener las novelas
    public List<Novela> obtenerNovelas() {
        return novelas;
    }
}