package com.peleadegallos;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Adaptador utilizado con codigo nativo de android y asi
 * poder utilizar funciones que solo estan disponibles en dispositivos mobiles
 */
public class AdaptadorAndroid implements AdaptadorCodigoAndroid, SensorEventListener {

    private Activity activityPrincipal;
    private float currentLux = 0;
    Sensor sensorLuz;
    SensorManager sensorManager;

    /**
     * Instantiates a new Adaptador android.
     *
     * @param activityPrincipal el activity que lanza la aplicacion, con ella cogemos los sensores
     */
    public AdaptadorAndroid(Activity activityPrincipal) {
        this.activityPrincipal = activityPrincipal;
        sensorManager = (SensorManager) activityPrincipal.getSystemService(Context.SENSOR_SERVICE);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensorLuz != null) { // si el sensor existe lo registro
            sensorManager.registerListener(this, sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    /**
     * Envia una notifiacion al dispositivo
     * Su icono será el mismo que el de la aplicacion
     *
     * @param titulo El titulo que tendra la notificacion
     * @param texto  El texto que tendra la notificacion, situado debajo del titulo
     */
    @Override
    public void nuevaNotificacion(String titulo, String texto) {
        Notification.Builder mBuilder = new Notification.Builder(activityPrincipal)
                .setSmallIcon(R.drawable.ic_launcher_2)
                .setContentTitle(titulo)
                .setContentText(texto);
        int notificationId = 1;  //Al tener todas el mismo id evito que haya muchas notificaciones a lavez, elimina las anteriores
        NotificationManager notificationManager = (NotificationManager) activityPrincipal.getSystemService(activityPrincipal.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationId, mBuilder.build());
    }

    /**
     * Devuelve la cantidad de luz que capta el sensor
     *
     * @return la luz actual
     */
    @Override
    public float getCurrentLux() {
        return currentLux;
    }

    /**
     * Funciona de forma automatica cuando cambia el valor de la luz
     *
     * @param event el evento que utliza para enviar los datos cuando cambia un sensor
     */
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
