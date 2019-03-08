package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.awt.Image;

/**
 * Clase para guardar los datos de los mapas
 */
public class Mapa {
    /**
     * La imagen del mapa completo
     */
    Texture mapa,
    /**
     * La textura de cada cuadrado del mapa
     */
    cuadradoMapa;
    /**
     * Los puntos en los que se coloca cada duadrado del mapa
     */
    Vector2[] puntos = new Vector2[8];
    /**
     * El Rozamiento.
     */
    float rozamiento;
    /**
     * El Nombre.
     */
    String nombre;
}
