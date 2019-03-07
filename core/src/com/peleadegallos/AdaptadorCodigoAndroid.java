package com.peleadegallos;

/**
 * The interface Adaptador codigo android.
 */
public interface AdaptadorCodigoAndroid {

    /**
     * Nueva notificacion.
     *
     * @param titulo the titulo
     * @param texto  the texto
     */
    void nuevaNotificacion(String titulo, String texto);

    /**
     * Gets current lux.
     *
     * @return the current lux
     */
    float getCurrentLux();
}
