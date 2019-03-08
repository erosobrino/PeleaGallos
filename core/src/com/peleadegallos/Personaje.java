package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;

/**
 * La clase que guarda los datos del personaje para mostrar en el selector
 */
public class Personaje {

    private Texture textura;
    private int vida, velocidad;
    private String nombre;

    /**
     * Devuelve textura.
     *
     * @return la textura
     */
    public Texture getTextura() {
        return textura;
    }

    /**
     * Establece textura.
     *
     * @param textura la textura
     */
    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    /**
     * Devuelve vida.
     *
     * @return la vida
     */
    public int getVida() {
        return vida;
    }

    /**
     * Establece vida.
     *
     * @param vida la vida
     */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Devuelve velocidad.
     *
     * @return la velocidad
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Establece velocidad.
     *
     * @param velocidad la velocidad
     */
    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Devuelve nombre.
     *
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece nombre.
     *
     * @param nombre el nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Inicializa un nuevo personaje
     *
     * @param textura   la textura
     * @param vida      la vida
     * @param velocidad la velocidad
     * @param nombre    el nombre para la carga de las texturas en la pantallajuego
     */
    public Personaje(Texture textura, int vida, int velocidad, String nombre) {
        this.textura = textura;
        this.velocidad = velocidad;
        this.vida = vida;
        this.nombre = nombre;
    }
}
