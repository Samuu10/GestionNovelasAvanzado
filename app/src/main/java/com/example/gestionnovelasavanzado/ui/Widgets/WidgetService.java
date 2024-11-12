package com.example.gestionnovelasavanzado.ui.Widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

//Clase WidgetService que proporciona la vista remota para el widget
public class WidgetService extends RemoteViewsService {

    //Metodo que devuelve la vista remota
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViews(this.getApplicationContext());
    }
}