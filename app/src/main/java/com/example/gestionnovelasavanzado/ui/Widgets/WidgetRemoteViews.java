package com.example.gestionnovelasavanzado.ui.Widgets;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import java.util.List;

public class WidgetRemoteViews implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private List<Novela> favoritas;

    public WidgetRemoteViews(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {}

    @Override
    public void onDataSetChanged() {
        PreferencesManager preferencesManager = new PreferencesManager(context);
        favoritas = preferencesManager.loadNovelas();
        favoritas.removeIf(novela -> !novela.getFavorito());
    }

    @Override
    public void onDestroy() {
        if (favoritas != null) {
            favoritas.clear();
        }
    }

    @Override
    public int getCount() {
        return favoritas != null ? favoritas.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (favoritas == null || favoritas.size() == 0) {
            return null;
        }

        Novela novela = favoritas.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_novela);
        views.setTextViewText(R.id.textViewTitulo, novela.getTitulo());
        views.setTextViewText(R.id.textViewAutor, novela.getAutor());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
}