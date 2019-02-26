package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;

public class Arma {

    private Texture textura;
    private int alcance, balas, daño;
    private String nombre;

    public int getDaño() {
        return daño;
    }

    public void setDaño(int daño) {
        this.daño = daño;
    }

    public int getBalas() {
        return balas;
    }

    public void setBalas(int balas) {
        this.balas = balas;
    }

    public int getAlcance() {
        return alcance;
    }

    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

    public Texture getTextura() {
        return textura;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    public Arma(Texture textura, int alcance, int balas, int daño, String nombre) {
        this.textura = textura;
        this.alcance = alcance;
        this.balas = balas;
        this.daño = daño;
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
