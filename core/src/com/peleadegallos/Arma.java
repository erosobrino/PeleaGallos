package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;

/**
 * Clase utilizada para guardar los datos de todas las armas
 */
public class Arma {

    private Texture textura;
    private int alcance, balas, daño;
    private String nombre;

    /**
     * Devuelve el daño de las balas.
     *
     * @return el daño
     */
    public int getDaño() {
        return daño;
    }

    /**
     * Modifica el daño de la cada bala
     *
     * @param daño el daño
     */
    public void setDaño(int daño) {
        this.daño = daño;
    }

    /**
     * Devuelve la cantidad de balas.
     *
     * @return las balas
     */
    public int getBalas() {
        return balas;
    }

    /**
     * Modifica la cantidad de balas del cargador
     *
     * @param balas la cantidad de balas
     */
    public void setBalas(int balas) {
        this.balas = balas;
    }

    /**
     * Devuelve el alcance.
     *
     * @return el alcance
     */
    public int getAlcance() {
        return alcance;
    }

    /**
     * Modifica el alcance
     *
     * @param alcance el nuevo alcance, su valor minimo es 1
     */
    public void setAlcance(int alcance) {
        if (alcance < 0)
            alcance = 1;
        this.alcance = alcance;
    }

    /**
     * Devuelve la textura actual
     *
     * @return la textura
     */
    public Texture getTextura() {
        return textura;
    }

    /**
     * Cambia la textura
     *
     * @param textura la nueva textura
     */
    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    /**
     * Devuelve el nombrede la arma
     *
     * @return el nombre del arma
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre al arma
     *
     * @param nombre el nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Instantiates a new Arma.
     *
     * @param textura la textura de la arma
     * @param alcance el alcance o potencia con la que se dispara
     * @param balas   la cantidad de balas del cargador
     * @param daño    el daño
     * @param nombre  el nombre
     */
    public Arma(Texture textura, int alcance, int balas, int daño, String nombre) {
        this.textura = textura;
        this.alcance = alcance;
        this.balas = balas;
        this.daño = daño;
        this.nombre = nombre;
    }
}
