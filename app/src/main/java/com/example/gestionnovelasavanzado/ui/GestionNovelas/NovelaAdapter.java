package com.example.gestionnovelasavanzado.ui.GestionNovelas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.Activities.MainActivity;

import java.util.List;

public class NovelaAdapter extends BaseAdapter {

    private Context context;
    private List<Novela> novelas;

    // Constructor para inicializar el adaptador con el contexto y la lista de novelas
    public NovelaAdapter(Context context, List<Novela> novelas) {
        this.context = context;
        this.novelas = novelas;
    }

    @Override
    public int getCount() {
        return novelas.size();
    }

    @Override
    public Object getItem(int position) {
        return novelas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Método para inflar la vista de cada elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_novela, parent, false);
        }

        Novela novela = novelas.get(position);
        TextView textViewTitulo = view.findViewById(R.id.textViewTitulo);
        TextView textViewAutor = view.findViewById(R.id.textViewAutor);

        textViewTitulo.setText(novela.getTitulo());
        textViewAutor.setText(novela.getAutor());

        // Añadir un evento al clic de la novela
        view.setOnClickListener(v -> {
            if (context instanceof MainActivity) {
                ((MainActivity) context).mostrarDetallesNovela(novela); // Llama a mostrarDetallesNovela
            }
        });

        return view;
    }
}
