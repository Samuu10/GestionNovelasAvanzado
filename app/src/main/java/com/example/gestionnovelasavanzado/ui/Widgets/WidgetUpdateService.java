package com.example.gestionnovelasavanzado.ui.Widgets;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.Activities.MainActivity;
import com.example.gestionnovelasavanzado.ui.SharedPreferences.PreferencesManager;
import com.example.gestionnovelasavanzado.ui.GestionNovelas.Novela;
import java.util.List;

public class WidgetUpdateService extends IntentService {

    public static final String ACTION_UPDATE_WIDGET = "com.example.gestionnovelasavanzado.ui.Widgets.action.UPDATE_WIDGET";

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                handleActionUpdateWidget();
            }
        }
    }

    private void handleActionUpdateWidget() {
        PreferencesManager preferencesManager = new PreferencesManager(this);
        List<Novela> favoritas = preferencesManager.loadNovelas();
        favoritas.removeIf(novela -> !novela.getFavorito());

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget);

        views.setTextViewText(R.id.widget_title, "Favoritas (" + favoritas.size() + ")");
        Intent intent = new Intent(this, WidgetService.class);
        views.setRemoteAdapter(R.id.widget_listview, intent);

        // Añadir PendingIntent para abrir MainActivity
        Intent mainIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mainIntent, PendingIntent.FLAG_IMMUTABLE);
        views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, NovelasFavoritasWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listview);
        appWidgetManager.updateAppWidget(appWidgetIds, views);
    }
}