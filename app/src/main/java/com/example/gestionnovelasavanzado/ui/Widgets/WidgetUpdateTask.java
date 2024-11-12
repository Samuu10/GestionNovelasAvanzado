package com.example.gestionnovelasavanzado.ui.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.Activities.MainActivity;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import java.util.List;

public class WidgetUpdateTask extends AsyncTask<Void, Void, List<Novela>> {

    private Context context;

    public WidgetUpdateTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Novela> doInBackground(Void... voids) {
        PreferencesManager preferencesManager = new PreferencesManager(context);
        List<Novela> favoritas = preferencesManager.loadNovelas();
        favoritas.removeIf(novela -> !novela.getFavorito());
        return favoritas;
    }

    @Override
    protected void onPostExecute(List<Novela> favoritas) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        views.setTextViewText(R.id.widget_title, "Favoritas (" + favoritas.size() + ")");
        Intent intent = new Intent(context, WidgetService.class);
        views.setRemoteAdapter(R.id.widget_listview, intent);

        Intent mainIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NovelasFavoritasWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }
}