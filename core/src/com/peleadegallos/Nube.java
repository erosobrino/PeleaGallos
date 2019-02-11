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

public class Nube extends Actor {

    float posX, posY, velocidad;
    int anchoPantalla;
    Texture nube;

    public Nube(int altoPantalla, int anchoPantalla, Texture textura, Random rand) {
        int alto = altoPantalla / 3;
        posX = rand.nextInt(anchoPantalla);
        this.anchoPantalla = anchoPantalla;
        posY = rand.nextInt(alto*2)+alto*1.1f;
        velocidad = rand.nextInt(100) + 20;
        this.nube = textura;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getVelocidad() {
        return velocidad;
    }

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
