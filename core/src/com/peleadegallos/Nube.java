package com.peleadegallos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

/**
 * El actor nube, lo mueve de forma automatica y vuelve a aparecer por pantalla
 */
public class Nube extends Actor {

    /**
     * La Posicion x.
     */
    float posX,
    /**
     * La Posicion y.
     */
    posY,
    /**
     * La Velocidad.
     */
    velocidad;
    /**
     * El ancho de la pantalla
     */
    int anchoPantalla;
    /**
     * El alto de la pantalla
     */
    int altoPantalla;
    /**
     * La textura de la Nube.
     */
    Texture nube;

    /**
     * Inicializa la nube con los valores necesarios para que siempre vualva a aparecer
     *
     * @param altoPantalla  el alto pantalla
     * @param anchoPantalla el ancho pantalla
     * @param textura       la textura
     * @param rand          el random para la posicion y la velocidad
     */
    public Nube(int altoPantalla, int anchoPantalla, Texture textura, Random rand) {
        int alto = altoPantalla / 3;
        posX = rand.nextInt(anchoPantalla);
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla=altoPantalla;
        posY = rand.nextInt(alto*2)+alto*1.1f;
        velocidad = rand.nextInt(100) + 20;
        this.nube = textura;
    }

    /**
     * Devuelve pos x.
     *
     * @return la posicion x
     */
    public float getPosX() {
        return posX;
    }

    /**
     * Establece pos x.
     *
     * @param posX la posicion x
     */
    public void setPosX(float posX) {
        this.posX = posX;
    }

    /**
     * Devuelve pos y.
     *
     * @return la posicion y
     */
    public float getPosY() {
        return posY;
    }

    /**
     * Establece pos y.
     *
     * @param posY la posicion y
     */
    public void setPosY(float posY) {
        this.posY = posY;
    }

    /**
     * Devuelve velocidad.
     *
     * @return la velocidad
     */
    public float getVelocidad() {
        return velocidad;
    }

    /**
     * Establece velocidad.
     *
     * @param velocidad la velocidad
     */
    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    /**
     * Dibuja la nube por pantalla
     * @param batch dibuja la nube por pantalla
     * @param parentAlpha el alpha del padre
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();
        batch.begin();
        batch.draw(nube, posX, posY,anchoPantalla/1920*nube.getWidth(),altoPantalla/1080*nube.getHeight());
    }

    /**
     * Mueve la nube en funcion de la velocidad y del tiempo desde la ultima ejecucion
     * @param delta el tiempo desde la ultima ejecucion
     */
    @Override
    public void act(float delta) {
        this.posX -= velocidad * delta;
        if (this.posX < -nube.getWidth()) {
            this.posX = anchoPantalla;
            this. velocidad = (int) (Math.random() * (100) +20);
        }
    }
}
