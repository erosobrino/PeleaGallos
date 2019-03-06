package com.peleadegallos;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AdaptadorAndroid implements AdaptadorCodigoAndroid, SensorEventListener {

    private Activity activityPrincipal;
    private float currentLux = 0;
    Sensor sensorLuz;
    SensorManager sensorManager;

    public AdaptadorAndroid(Activity activityPrincipal) {
        this.activityPrincipal = activityPrincipal;
        sensorManager = (SensorManager) activityPrincipal.getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensorLuz != null) { // si el sensor existe lo registro
            sensorManager.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void nuevaNotificacion(String titulo, String texto) {
        Notification.Builder mBuilder = new Notification.Builder(activityPrincipal)
                .setSmallIcon(R.drawable.ic_launcher_2)
                .setContentTitle(titulo)
                .setContentText(texto);
        int notificationId = 1;
        NotificationManager notificationManager = (NotificationManager) activityPrincipal.getSystemService(activityPrincipal.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, mBuilder.build());
    }

    @Override
    public float getCurrentLux() {
        return currentLux;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == sensorLuz) {
            currentLux = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
