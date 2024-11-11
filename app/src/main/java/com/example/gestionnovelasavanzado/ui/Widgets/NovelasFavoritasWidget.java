package com.example.gestionnovelasavanzado.ui.Widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.gestionnovelasavanzado.R;
import com.example.gestionnovelasavanzado.ui.Activities.MainActivity;

public class NovelasFavoritasWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.widget_title, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
        WidgetUpdateService.startActionUpdateWidget(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(intent.getAction())) {
            WidgetUpdateService.startActionUpdateWidget(context);
        }
    }
}