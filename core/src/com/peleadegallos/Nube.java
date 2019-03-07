package com.peleadegallos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * The type Nube.
 */
public class Nube extends Actor {

    /**
     * The Pos x.
     */
    float posX, /**
     * The Pos y.
     */
    posY, /**
     * The Velocidad.
     */
    velocidad;
    /**
     * The Ancho pantalla.
     */
    int anchoPantalla;
    /**
     * The Nube.
     */
    Texture nube;

    /**
     * Instantiates a new Nube.
     *
     * @param altoPantalla  the alto pantalla
     * @param anchoPantalla the ancho pantalla
     * @param textura       the textura
     * @param rand          the rand
     */
    public Nube(int altoPantalla, int anchoPantalla, Texture textura, Random rand) {
        int alto = altoPantalla / 3;
        posX = rand.nextInt(anchoPantalla);
        this.anchoPantalla = anchoPantalla;
        posY = rand.nextInt(alto*2)+alto*1.1f;
        velocidad = rand.nextInt(100) + 20;
        this.nube = textura;
    }

    /**
     * Gets pos x.
     *
     * @return the pos x
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Sets pos x.
     *
     * @param posX the pos x
     */
    public void setPosX(float posX) {
        this.posX = posX;
    }

    /**
     * Gets pos y.
     *
     * @return the pos y
     */
    public float getPosY() {
        return posY;
    }

    /**
     * Sets pos y.
     *
     * @param posY the pos y
     */
    public void setPosY(float posY) {
        this.posY = posY;
    }

    /**
     * Gets velocidad.
     *
     * @return the velocidad
     */
    public float getVelocidad() {
        return velocidad;
    }

    /**
     * Sets velocidad.
     *
     * @param velocidad the velocidad
     */
    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        batch.begin();
        batch.draw(nube, posX, posY);
    }

    @Override
    public void act(float delta) {
        this.posX -= velocidad * delta;
        if (this.posX < -nube.getWidth()) {
            this.posX = anchoPantalla;
            this. velocidad = (int) (Math.random() * (100) +20);
        }
    }
}
