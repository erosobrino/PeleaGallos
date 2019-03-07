package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;

/**
 * The type Arma.
 */
public class Arma {

    private Texture textura;
    private int alcance, balas, daño;
    private String nombre;

    /**
     * Get daño int.
     *
     * @return the int
     */
    public int getDaño() {
        return daño;
    }

    /**
     * Set daño.
     *
     * @param daño the daño
     */
    public void setDaño(int daño) {
        this.daño = daño;
    }

    /**
     * Gets balas.
     *
     * @return the balas
     */
    public int getBalas() {
        return balas;
    }

    /**
     * Sets balas.
     *
     * @param balas the balas
     */
    public void setBalas(int balas) {
        this.balas = balas;
    }

    /**
     * Gets alcance.
     *
     * @return the alcance
     */
    public int getAlcance() {
        return alcance;
    }

    /**
     * Sets alcance.
     *
     * @param alcance the alcance
     */
    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

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
     * Instantiates a new Arma.
     *
     * @param textura the textura
     * @param alcance the alcance
     * @param balas   the balas
     * @param daño    the daño
     * @param nombre  the nombre
     */
    public Arma(Texture textura, int alcance, int balas, int daño, String nombre) {
        this.textura = textura;
        this.alcance = alcance;
        this.balas = balas;
        this.daño = daño;
        this.nombre=nombre;
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
