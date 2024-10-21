package com.example.gestionnovelasavanzado.ui.GestionNovelas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gestionnovelasavanzado.R;
import java.util.List;

//Clase NovelaAdapter que extiende BaseAdapter y se utiliza para mostrar la lista de novelas
public class NovelaAdapter extends RecyclerView.Adapter<NovelaAdapter.NovelaViewHolder> {

    private Context context;
    private List<Novela> novelas;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Novela novela);
    }

    public NovelaAdapter(Context context, List<Novela> novelas, OnItemClickListener listener) {
        this.context = context;
        this.novelas = novelas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NovelaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_novela, parent, false);
        return new NovelaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NovelaViewHolder holder, int position) {
        Novela novela = novelas.get(position);
        holder.textViewTitulo.setText(novela.getTitulo());
        holder.textViewAutor.setText(novela.getAutor());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(novela));
    }

    @Override
    public int getItemCount() {
        return novelas.size();
    }

    public static class NovelaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitulo;
        TextView textViewAutor;

        public NovelaViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitulo = itemView.findViewById(R.id.textViewTitulo);
            textViewAutor = itemView.findViewById(R.id.textViewAutor);
        }
    }
}