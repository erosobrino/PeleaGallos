package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.awt.Image;

/**
 * The type Mapa.
 */
public class Mapa {
    /**
     * The Mapa.
     */
    Texture mapa, /**
     * The Cuadrado mapa.
     */
    cuadradoMapa;
    /**
     * The Puntos.
     */
    Vector2[] puntos=new Vector2[8];
    /**
     * The Rozamiento.
     */
    float rozamiento;
    /**
     * The Nombre.
     */
    String nombre;
}
