package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;

public class Personaje {

    private Texture textura;
    private int vida, velocidad;
    private String nombre;

    public Texture getTextura() {
        return textura;
    }

    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public Personaje(Texture textura, int vida, int velocidad, String nombre) {
        this.textura = textura;
        this.velocidad = velocidad;
        this.vida = vida;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
