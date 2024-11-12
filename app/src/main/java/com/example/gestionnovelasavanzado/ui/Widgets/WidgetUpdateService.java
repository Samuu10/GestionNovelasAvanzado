package com.example.gestionnovelasavanzado.ui.Widgets;

import android.content.Context;
import android.content.Intent;
import androidx.core.app.JobIntentService;

public class WidgetUpdateService extends JobIntentService {

    private static final int JOB_ID = 1000;

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        enqueueWork(context, WidgetUpdateService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        new WidgetUpdateTask(this).execute();
    }
}