package com.peleadegallos;

/**
 * Interfaz para utilizar el codigo android.
 */
public interface AdaptadorCodigoAndroid {

    /**
     * Crea y envia una nueva notificacion
     *
     * @param titulo el titulo de la notificacion
     * @param texto  el texto que tendrá la notificacion
     */
    void nuevaNotificacion(String titulo, String texto);

    /**
     * La luz que detecta el sensor
     *
     * @return la luz actual
     */
    float getCurrentLux();
}
