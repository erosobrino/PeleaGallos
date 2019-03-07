package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;

/**
 * The type Personaje.
 */
public class Personaje {

    private Texture textura;
    private int vida, velocidad;
    private String nombre;

    /**
     * Gets textura.
     *
     * @return the textura
     */
    public Texture getTextura() {
        return textura;
    }

    /**
     * Sets textura.
     *
     * @param textura the textura
     */
    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    /**
     * Gets vida.
     *
     * @return the vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * Sets vida.
     *
     * @param vida the vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Gets velocidad.
     *
     * @return the velocidad
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Sets velocidad.
     *
     * @param velocidad the velocidad
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Instantiates a new Personaje.
     *
     * @param textura   the textura
     * @param vida      the vida
     * @param velocidad the velocidad
     * @param nombre    the nombre
     */
    public Personaje(Texture textura, int vida, int velocidad, String nombre) {
        this.textura = textura;
        this.velocidad = velocidad;
        this.vida = vida;
        this.nombre = nombre;
    }

    /**
     * Gets nombre.
     *
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Sets nombre.
     *
     * @param nombre the nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
