package com.peleadegallos;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Nube extends Actor {

    int posX,posY, velocidad;

    ShapeRenderer formas;

    public Nube(int altoPantalla, int anchoPantalla){
        int mitadAlto=altoPantalla/2;
        posX=anchoPantalla;
        posY= (int) (Math.random()*(mitadAlto)+mitadAlto);
        velocidad=(int) (Math.random()*(20)+1);

        formas=new ShapeRenderer();
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        this.posX-=velocidad*delta;
    }
}
